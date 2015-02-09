(ns clj-slack-client.core
  (:gen-class)
  (:require
    [clj-slack-client
     [team-state :as state]
     [connectivity :as conn]
     [rtm-receive :as rx]
     [web :as web]]))


(defn connect
  [api-token host-event-handler]
  (rx/attach-host-event-handler host-event-handler)
  (conn/start-real-time api-token
                        state/set-team-state
                        rx/handle-event-json))


(defn disconnect
  []
  (conn/disconnect)
  (rx/close))


(defn call-channels.setTopic
  [api-token channel-id topic]
  (->> {:token api-token :channel channel-id :topic topic}
       (web/call-slack-web-api "channels.setTopic")
       (web/get-api-response)))
