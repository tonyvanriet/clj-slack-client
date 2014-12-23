(ns clj-slack-client.core
  (:gen-class)
  (:require
   [cheshire.core :as json]
   [clj-slack-client
    [team-state :as state]
    [connectivity :as conn]
    [rtm-receive :as rx]]))


(defn connect
  []
  (conn/start-real-time state/set-team-state rx/handle-event-json))


(defn disconnect
  []
  (conn/disconnect))
