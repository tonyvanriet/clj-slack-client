(ns clj-slack-client.core
  (:gen-class)
  (:require
   [cheshire.core :as json]
   [clj-slack-client
    [team-state :as state]
    [connectivity :as conn]]))


;
; messaging
;

(defn message-json
  [channel text]
  (json/encode {:id 1
                :type "message"
                :channel channel
                :text text}))



(defn say-message
  [message-json]
  (conn/send-to-websocket message-json))


(defmulti handle-event :type)


(defmethod handle-event "message"
  [event]
  (let [user-id (:user event)
        user (state/get-user user-id)
        self-id (:id (state/get-self))
        channel-id (:channel event)]
    (when (and (not= user-id self-id)
               (not (:is_bot user)))
      (say-message (message-json channel-id "That's what she said")))))


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


(defn connect
  []
  (conn/start-real-time state/set-team-state handle-event-json))


(defn disconnect
  []
  (conn/disconnect))
