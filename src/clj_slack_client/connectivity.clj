(ns clj-slack-client.connectivity
  (:gen-class)
  (:require
    [cheshire.core :as json]
    [manifold.stream :as stream]
    [byte-streams]
    [aleph.http :as aleph]))


(def ^:dynamic *websocket-stream* nil)

(def slack-api-base-url "https://slack.com/api")


(defn send-to-websocket
  [data-json]
  (stream/put! *websocket-stream* data-json))


(def heartbeating (atom false))


(def ping-json
  (json/encode {:id   1
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


(defn get-api-response
  "Takes a full http response map and returns the api response as a map."
  [http-response]
  (let [response-body-bytes (:body http-response)
        response-body-json (byte-streams/to-string response-body-bytes)
        api-response (json/parse-string response-body-json true)]
    api-response))


(defn call-slack-web-api
  ([method-name]
    (call-slack-web-api method-name {}))
  ([method-name params]
    (let [method-url-base (str slack-api-base-url "/" method-name)]
      @(aleph/post method-url-base {:query-params params}))))


(defn call-rtm-start
  [api-token]
  (->> {:token api-token}
       (call-slack-web-api "rtm.start")
       (get-api-response)))


(defn connect-websocket-stream
  [ws-url]
  @(aleph/websocket-client ws-url))


(defn start-real-time
  ([api-token set-team-state handle-event-json]
    (let [response-body (call-rtm-start api-token)
          ws-url (:url response-body)
          ws-stream (connect-websocket-stream ws-url)]
      (alter-var-root (var *websocket-stream*) (fn [_] ws-stream))
      (set-team-state response-body))
    (start-ping)
    (stream/consume handle-event-json *websocket-stream*)))


(defn disconnect
  []
  (stop-ping)
  (stream/close! *websocket-stream*))






