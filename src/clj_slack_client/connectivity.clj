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


(def ^:private message-id (atom 0))

(defn send-message
  "adds the incrementing message id to the message, converts it to json,
  and passes it to the websocket."
  [message]
  (-> message
      (assoc :id (swap! message-id inc))
      (json/encode)
      (send-to-websocket)))


(def ^:private heartbeating (atom false))

(def ping-message {:type "ping"})

(defn start-ping
  []
  (swap! heartbeating (constantly true))
  (future
    (loop []
      (Thread/sleep 5000)
      (send-message ping-message)
      (when @heartbeating (recur)))))

(defn stop-ping
  []
  (swap! heartbeating (constantly false)))


(defn connect-websocket-stream
  [ws-url]
  @(aleph/websocket-client ws-url))


(defn handle-event
  [event]
  (let [event-type (:type event)]
    (if (= event-type "pong")                               ;; todo host callback for this message-id?
      (println "pong")                                      ;; todo track pong time
      (println event))))


(defn event-json->event
  [event-json]
  (json/parse-string event-json true))


(defn start-real-time
  [api-token set-team-state pass-event-to-rx]
  (let [response-body (web/rtm-start api-token)
        ws-url (:url response-body)
        ws-stream (connect-websocket-stream ws-url)]
    (set-team-state response-body)
    (alter-var-root (var *websocket-stream*) (constantly ws-stream)))
  (start-ping)
  (let [slack-event-stream (stream/map event-json->event *websocket-stream*)
        conn-event-stream (stream/stream 8)]
    (stream/connect slack-event-stream conn-event-stream)
    (stream/consume handle-event conn-event-stream)
    (stream/consume pass-event-to-rx slack-event-stream)))


(defn disconnect
  []
  (stop-ping)
  (when *websocket-stream*
    (stream/close! *websocket-stream*)))






