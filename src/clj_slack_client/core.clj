(ns clj-slack-client.core
  (:gen-class)
  (:require [clj-http.client :as http])
  (:require [aleph.http :as aleph])
  (:require [cheshire.core :as json])
  (:require [manifold.stream :as stream])
  (:require [manifold.deferred :as deferred]))



(def env (atom nil))

(def websocket-stream (atom nil))


;
; messaging
;

(defn message-json
  [channel text]
  (json/encode {:id 1
                :type "message"
                :channel channel
                :text text}))

(def ping-json
  (json/encode {:id 1
                :type "ping"}))



(defn send-to-websocket
  [data-json]
  (stream/put! @websocket-stream data-json))


(defn say-message
  [message-json]
  (send-to-websocket message-json))


(defn handle-message
  [message-event]
  (say-message (message-json (:channel message-event) "That's what she said")))

(defn handle-channel-joined
  [channel]
  (swap! env #(assoc-in % [:channels] (conj (:channels %) channel))))

(defn handle-slack-event
  [event-json]
  (let [event (json/parse-string event-json true)
        event-type (:type event)]
    (when (not= event-type "pong")
      (println event))
    (cond
     (= event-type "message") (handle-message event)
     (= event-type "channel_joined") (handle-channel-joined (:channel event)))))




;
; connectivity
;

(def slack-api-base-url "http://slack.com/api")
(def rtm-start-base-url (str slack-api-base-url "rtm.start"))

(def abot-api-token "xoxb-3215140999-UuVgqNVwxMDcWNrVeoOMMtxw")
(def someotherbot-api-token "xoxb-3246812512-FRBtlsTndTc2fGEhwq1rOhcD")
(def tonyvanriet-api-token "xoxp-3215134233-3215134235-3216767432-ca2d3d")


(def heartbeating (atom false))


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
     (http/get method-url-base {:query-params params}))))


(defn store-environment
  [rtm-start-response-body]
  (swap! env (fn [_] rtm-start-response-body)))


(defn call-rtm-start
  [api-token]
  (let [response (call-slack-web-api "rtm.start" {:token api-token})
        response-body-json (:body response)
        response-body (json/parse-string response-body-json true)]
    response-body))


(defn connect
  ([]
   (connect someotherbot-api-token))
  ([api-token]
   (let [response-body (call-rtm-start api-token)
         ws-url (:url response-body)
         ws-stream @(aleph/websocket-client ws-url)]
     (swap! websocket-stream (fn [_] ws-stream))
     (store-environment response-body)
     (start-ping)
     (stream/consume handle-slack-event @websocket-stream))))


(defn disconnect
  []
  (stop-ping)
  (stream/close! @websocket-stream))






