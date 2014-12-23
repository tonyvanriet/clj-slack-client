(ns clj-slack-client.rtm-transmit
  (:gen-class)
  (:require
   [cheshire.core :as json]
   [clj-slack-client
    [team-state :as state]
    [connectivity :as conn]]))


(defn message-json
  [channel-id text]
  (json/encode {:id 1
                :type "message"
                :channel channel-id
                :text text}))


(defn say-message
  [channel-id text]
  (conn/send-to-websocket (message-json channel-id text)))

