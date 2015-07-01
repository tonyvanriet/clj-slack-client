(ns clj-slack-client.connectivity
  (:gen-class)
  (:require
    [clj-slack-client.web :as web]
    [cheshire.core :as json]
    [manifold.stream :as stream]
    [byte-streams]
    [aleph.http :as aleph]))


(def ^:dynamic *websocket-stream* nil)


(defn send-to-websocket
  [data-json]
  (stream/put! *websocket-stream* data-json))


(def heartbeating (atom false))


(def ping-json
  (json/encode {:id   1
                :type "ping"}))


(defn start-ping
  []
  (swap! heartbeating (constantly true))
  (future
    (loop []
      (Thread/sleep 5000)
      (send-to-websocket ping-json)
      (when @heartbeating (recur)))))


(defn stop-ping
  []
  (swap! heartbeating (constantly false)))



(defn connect-websocket-stream
  [ws-url]
  @(aleph/websocket-client ws-url))


(defn start-real-time
  ([api-token set-team-state handle-event-json]
    (let [response-body (web/rtm-start api-token)
          ws-url (:url response-body)
          ws-stream (connect-websocket-stream ws-url)]
      (alter-var-root (var *websocket-stream*) (constantly ws-stream))
      (set-team-state response-body))
    (start-ping)
    (stream/consume handle-event-json *websocket-stream*)))


(defn disconnect
  []
  (stop-ping)
  (when *websocket-stream*
    (stream/close! *websocket-stream*)))






