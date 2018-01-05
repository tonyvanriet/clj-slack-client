(ns clj-slack-client.reminders
  (:require [clj-slack-client.web :as web])
  (:refer-clojure :exclude [list]))

; these methods only accessible by user tokens. Bots and
; workspaces are not allowed

(defn add
  "Creates a reminder."
  ([options]
   (web/call-and-get-response "reminders.add" options))
  ([token text time]
   (add {:token token :text text :time time}))
  ([token text time user]
   (add {:token token :text text :time time :user user})))


(defn complete
  "Marks a reminder as complete."
  [token reminder-id]
  (web/call-and-get-response "reminders.complete"
                             {:token token :reminder reminder-id}))


(defn delete
  "Deletes a reminder."
  [token reminder-id]
  (web/call-and-get-response "reminders.delete"
                             {:token token :reminder reminder-id}))


(defn info
  "Gets information about a reminder"
  [token reminder-id]
  (web/call-and-get-response "reminders.info"
                             {:token token :reminder reminder-id}))


(defn list
  "Lists all reminders created by or for a given user."
  [token]
  (web/call-and-get-response "reminders.list" {:token token}))

