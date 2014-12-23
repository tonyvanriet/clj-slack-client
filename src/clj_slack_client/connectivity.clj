(ns clj-slack-client.connectivity
  (:gen-class)
  (:require [cheshire.core :as json])
  (:require [manifold.stream :as stream])
  (:require [byte-streams])
  (:require [aleph.http :as aleph]))


(def ^:dynamic *websocket-stream* nil)


(def slack-api-base-url "https://slack.com/api")
(def rtm-start-base-url (str slack-api-base-url "rtm.start"))

(def abot-api-token "xoxb-3215140999-UuVgqNVwxMDcWNrVeoOMMtxw")
(def someotherbot-api-token "xoxb-3246812512-FRBtlsTndTc2fGEhwq1rOhcD")
(def tonyvanriet-api-token "xoxp-3215134233-3215134235-3216767432-ca2d3d")


(defn send-to-websocket
  [data-json]
  (stream/put! *websocket-stream* data-json))


(def heartbeating (atom false))


(def ping-json
  (json/encode {:id 1
                :type "ping"}))


(defn start-ping
  []
  (swap! heartbeating (fn [_] true))
  (future
    (loop []
      (Thread/sleep 5000)
      (send-to-websocket ping-json)
      (when @heartbeating (recur)))))


(defn stop-ping
  []
  (swap! heartbeating (fn [_] false)))


(defn call-slack-web-api
  ([method-name]
   (call-slack-web-api method-name {}))
  ([method-name params]
   (let [method-url-base (str slack-api-base-url "/" method-name)]
     @(aleph/post method-url-base {:query-params params}))))


(defn call-rtm-start
  [api-token]
  (let [response (call-slack-web-api "rtm.start" {:token api-token})
        response-body-bytes (:body response)
        response-body-json (byte-streams/to-string response-body-bytes)
        response-body (json/parse-string response-body-json true)]
    response-body))


(defn start-real-time
  ([set-team-state-fn]
   (start-real-time abot-api-token set-team-state-fn))
  ([api-token set-team-state-fn]
   (let [response-body (call-rtm-start api-token)
         ws-url (:url response-body)
         ws-stream @(aleph/websocket-client ws-url)]
     (alter-var-root (var *websocket-stream*) (fn [_] ws-stream))
     (set-team-state-fn response-body)
     (start-ping))))


(defn disconnect
  []
  (stop-ping)
  (stream/close! *websocket-stream*))






