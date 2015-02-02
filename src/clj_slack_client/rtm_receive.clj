(ns clj-slack-client.rtm-receive
  (:gen-class)
  (:require
    [cheshire.core :as json]
    [clj-slack-client
     [team-state :as state]
     [rtm-transmit :as tx]]
    [manifold.stream :as stream]))


(def ^:dynamic *host-event-stream* nil)


(defn attach-host-event-handler
  [handler]
  (alter-var-root (var *host-event-stream*) (fn [_] (stream/stream 16)))
  (stream/consume handler *host-event-stream*))


(defmulti handle-event :type)

(defmethod handle-event "message"
  [event]
  nil)

(defmethod handle-event "channel_joined"
  [event]
  (state/channel-joined (:channel event)))

(defmethod handle-event :default
  [event]
  nil)


(defn handle-event-json
  [event-json]
  (let [event (json/parse-string event-json true)
        event-type (:type event)]
    (handle-event event)
    (when (not= event-type "pong")
      (println event)
      (stream/put! *host-event-stream* event))))


(defn close
  []
  (stream/close! *host-event-stream*))

