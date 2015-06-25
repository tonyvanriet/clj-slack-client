(ns clj-slack-client.core
  (:gen-class)
  (:require
    [clj-slack-client
     [team-state :as state]
     [connectivity :as conn]
     [rtm-receive :as rx]]))


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

