(ns clj-slack-client.usergroups.users
  (:require [clj-slack-client.web :as web])
  (:refer-clojure :exclude [list update]))


(defn list
  "List all users in a user group. Accessible by workspace and user
  token types."
  ([token usergroup]
   (list token usergroup false))
  ([token usergroup include-disabled]
   (web/call-and-get-response "usergroups.users.list"
                              {:token token :usergroup usergroup
                               :include_disabled include-disabled})))


(defn update
  "Update the list of users for a User Group. Accessible by user
  and workspace token types. Users are comma separated."
  ([token usergroup users]
   (update token usergroup users true))
  ([token usergroup users include-count]
   (web/call-and-get-response "usergroups.users.update"
                              {:token token :usergroup usergroup
                               :users users :include_count include-count})))

