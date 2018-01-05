(ns clj-slack-client.dialog
  (:require [clj-slack-client.web :as web]))


(defn open
  "Open a dialog with a user. Callable by any token type."
  [token dialog trigger-id]
  (web/call-and-get-response "dialog.open"
                             {:token token :dialog dialog
                              :trigger_id trigger-id}))
