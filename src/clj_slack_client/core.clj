(ns clj-slack-client.core
  (:gen-class)
  (:require [clj-http.client :as http])
  (:require [aleph.http :as aleph])
  (:require [cheshire.core :as json])
  (:require [manifold.stream :as stream])
  (:require [manifold.deferred :as deferred]))


(def slack-api-base-url "http://slack.com/api")
(def rtm-start-base-url (str slack-api-base-url "rtm.start"))

(def abot-api-token "xoxb-3215140999-UuVgqNVwxMDcWNrVeoOMMtxw")
(def someotherbot-api-token "xoxb-3246812512-FRBtlsTndTc2fGEhwq1rOhcD")
(def tonyvanriet-api-token "xoxp-3215134233-3215134235-3216767432-ca2d3d")


(defn call-slack-api
  ([method-name]
   (call-slack-api method-name {}))
  ([method-name params]
   (let [method-url-base (str slack-api-base-url "/" method-name)]
     (http/get method-url-base {:query-params params}))))


(defn rtm-start
  [api-token]
  (let [response (call-slack-api "rtm.start" {:token api-token})
        response-body-json (:body response)
        response-body (json/parse-string response-body-json true)]
    response-body))


(defn get-websocket-deferred
  [api-token]
  (let [ws-url (:url (rtm-start someotherbot-api-token))]
    (aleph/websocket-client ws-url)))



(def tonyvanriet-abot-dm-channel "D036B4503")


(defn message-json
  [channel text]
  (json/encode {:id 1
                :type "message"
                :channel channel
                :text text}))


(def ping-json
  (json/encode {:id 1
                :type "ping"}))


(def hihi-message-json (message-json tonyvanriet-abot-dm-channel "hihi"))


(def websocket-stream nil)
; want to use a dynamic var for websocket-stream but it's binding isn't visible
; from the thread that slack events are received on (where handle-slack-event is called)

(def heartbeating (atom false))


(defn send-to-websocket
  [data-json]
  (stream/put! websocket-stream data-json))


(defn say-message
  [message-json]
  (send-to-websocket message-json))


(defn handle-slack-event
  [event-json]
  (let [event (json/parse-string event-json true)
        event-type (:type event)]
    (when (not= event-type "pong")
      (println event))
    (when (= event-type "message")
      (say-message (message-json (:channel event) "That's what she said")))))


(defn start-ping
  []
  (swap! heartbeating (fn [hb] true))
  (future
    (loop []
      (Thread/sleep 5000)
      (send-to-websocket ping-json)
      (when @heartbeating (recur)))))


(defn stop-ping
  []
  (swap! heartbeating (fn [hb] false)))


(defn connect
  []
  (def websocket-stream @(get-websocket-deferred someotherbot-api-token))
    (start-ping)
    (stream/consume handle-slack-event websocket-stream))


(defn disconnect
  []
  (stop-ping)
  (stream/close! websocket-stream))






