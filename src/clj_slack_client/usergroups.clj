(ns clj-slack-client.usergroups
  (:require [clj-slack-client.web :as web])
  (:refer-clojure :exclude [list update]))


(defn create
  "Create a user group. Accessible to workspace and user tokens."
  ([token new-name]
   (create token new-name {}))
  ([token new-name options]
   (web/call-and-get-response "usergroups.create"
                              (merge {:token token :name new-name}
                                     options))))


(defn disable
  "Disable an existing user group. Accessible by workspace and user
  token types."
  ([token usergroup]
   (disable token usergroup false))
  ([token usergroup include-count]
   (web/call-and-get-response "usergroups.disable"
                              {:token token :usergroup usergroup
                               :include_count include-count})))


(defn enable
  "Enable a user group. Accessible by workspace and user token types."
  ([token usergroup]
   (disable token usergroup false))
  ([token usergroup include-count]
   (web/call-and-get-response "usergroups.enable"
                              {:token token :usergroup usergroup
                               :include_count include-count})))


(defn list
  "List all user groups for a team. Accessible by workspace
  and user token types."
  ([token]
   (list token {}))
  ([token options]
   (web/call-and-get-response "usergroups.list"
                              (assoc options :token token))))


(defn update
  "Update an existing user group. Accessible by workspace and user
  token types."
  ([token usergroup]
   (create token usergroup {}))
  ([token usergroup options]
   (web/call-and-get-response "usergroups.update"
                              (merge {:token token :usergroup usergroup}
                                     options))))

