(ns clj-slack-client.rtm-receive
  (:gen-class)
  (:require
   [cheshire.core :as json]
   [clj-slack-client
    [team-state :as state]
    [rtm-transmit :as tx]]))


(defmulti handle-event :type)


(defmethod handle-event "message"
  [event]
  (let [user-id (:user event)
        user (state/get-user user-id)
        self-id (:id (state/get-self))
        channel-id (:channel event)]
    (when (and (not= user-id self-id)
               (not (:is_bot user)))
      (tx/say-message channel-id "That's what she said"))))


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
    (when (not= event-type "pong") (println event))
    (handle-event event)))
