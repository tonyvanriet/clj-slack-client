(ns clj-slack-client.core
  (:gen-class)
  (:require
    [clj-slack-client
     [team-state :as state]
     [connectivity :as conn]
     [rtm-receive :as rx]]
    [clj-time.coerce :refer [to-long]]))


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


(defn time->ts
  "converts a joda DateTime into an approximate slack message timestamp"
  [time]
  (-> time
      (to-long)
      (/ 1000)
      (int)
      (str)))
