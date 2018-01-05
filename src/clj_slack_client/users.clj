(ns clj-slack-client.users
  (:require [clj-slack-client.web :as web])
  (:refer-clojure :exclude [identity list]))


(defn delete-photo
  "Delete the user profile photo. Only accessible by user token."
  [token]
  (web/call-and-get-response "users.deletePhoto" {:token token}))


(defn get-presence
  "Gets user presence information. Callable by bot and user token types."
  [token user-id]
  (web/call-and-get-response "users.getPresence" {:token token :user user-id}))


(defn identity
  "Get's a user's identity. Accessible by workspace and user tokens."
  [token]
  (web/call-and-get-response "users.identity" {:token token}))


(defn info
  "Gets information about a user. Accessible by all token types."
  ([token user]
   (info token user false))
  ([token user include-local]
   (web/call-and-get-response "users.info"
                              {:token token :user user
                               :include_local include-local})))


(defn list
  "Lists all users in a Slack team. Accessible by all token types."
  ([token]
   (list token {}))
  ([token options]
   (web/call-and-get-response "users.list"
                              (assoc options :token token))))


(defn lookup-by-email
  "Find a user with an email address. Accessibl by all token types."
  [token email]
  (web/call-and-get-response "users.lookupByEmail"
                             {:token token :email email}))


(defn set-active
  "Marks a user as active. Accessible by user and bot tokens."
  [token]
  (web/call-and-get-response "users.setActive" {:token token}))


(defn set-photo
  "Set the user profile photo. Accessible by user token. The
  image is in multipart/form-data form."
  ([token image]
   (set-photo token image {}))
  ([token image options]
   (web/call-and-get-response "users.setPhoto"
                              (merge {:token token :image image}
                                     options))))


(defn set-presence
  "Manually set user presence. Accessible by bot or user forms.
  Presence can either be away or auto."
  [token presence]
  (web/call-and-get-response "users.setPresence"
                             {:token token :presence presence}))

