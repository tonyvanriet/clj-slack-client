(ns clj-slack-client.migration
  (:require [clj-slack-client.web :as web]))


(defn exchange
  "For Enterprise Grid workspaces, map local user IDs to
  global user IDs. Callable by bot and user tokens. Users
  are separated by commas."
  ([token users]
   (exchange token users false))
  ([token users to-old]
    (web/call-and-get-response "migration.exchange"
                               {:token token :users users
                                :to_old to-old})))
