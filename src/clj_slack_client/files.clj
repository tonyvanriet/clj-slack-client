(ns clj-slack-client.files
  (:require [clj-slack-client.web :as web])
  (:refer-clojure :exclude [list]))

(def module-name "files")

(defn- call
  ([method-name token file]
   (call method-name token file {}))
  ([method-name token file options]
   (web/call-and-get-response (str module-name "." method-name)
                              (merge {:token token :file file}
                                     options))))

(defn delete
  "Deletes a file. Supported by all token types."
  [token file]
  (web/call-and-get-response "files.delete"
                             {:token token :file file}))


(defn info
  "Gets the information about a file. Accessible by all token types."
  ([token file]
   (info token file {}))
  ([token file options]
   (call "info" token file options)))


(defn list
  "Lists and filters team files. Accessible by workspace and user
  token types. Returns a list of files within the team. Can be
  filtered with the options."
  ([token]
   (list token {}))
  ([token options]
   (web/call-and-get-response "files.list"
                              (assoc options :token token))))


(defn revoke-public-URL
  "Revokes public/external sharing access for a file. Accessible
  by workspace and user tokens."
  [token file]
  (call "revokePublicURL" token file))


(defn shared-public-URL
  "Enables a file for public/external sharing. Accessible by workspace
  and user tokens."
  [token file]
  (call "sharedPublicURL" token file))


(defn upload
  "Uploads or creates a file. Accessible by all tokens."
  [token options]
  (web/call-and-get-response "files.upload"
                             (assoc options :token token)))
