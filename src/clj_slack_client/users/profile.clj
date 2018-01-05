(ns clj-slack-client.users.profile
  (:require [clj-slack-client.web :as web])
  (:refer-clojure :exclude [get set]))


(defn get
  "Retrieves a user's profile information. Supported by workspace and
  user token types."
  ([token]
   (get token {}))
  ([token options]
   (web/call-and-get-response "users.profile.get"
                              (assoc options :token token))))


(defn set
  "Set the profile information for a user. Supported by user token types."
  ([token]
   (set token {}))
  ([token options]
   (web/call-and-get-response "users.profile.set"
                              (assoc options :token token))))

