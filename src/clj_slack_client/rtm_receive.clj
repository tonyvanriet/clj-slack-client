(ns clj-slack-client.rtm-receive
  (:gen-class)
  (:require
    [clj-slack-client.team-state :as state]))


(defn dispatch-handle-event [event pass-event-to-host] (:type event))

(defmulti handle-event #'dispatch-handle-event)

(defmethod handle-event "message"
  [event pass-event-to-host]
  (pass-event-to-host event))

(defmethod handle-event "channel_joined"
  [event pass-event-to-host]
  (state/channel-joined (:channel event))
  (pass-event-to-host event))

(defmethod handle-event :default
  [event pass-event-to-host]
  (pass-event-to-host event))

