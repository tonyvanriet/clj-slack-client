(ns clj-slack-client.stars
  (:require [clj-slack-client.web :as web])
  (:refer-clojure :exclude [list remove]))


(defn- call
  "Call the slack api"
  [method-name token options]
  (web/call-and-get-response method-name
                             (assoc options :token token)))

; all of these methods are accessible by bots, workspaces, and users.
; each have extensive options available at
; api.slack.com/methods/stars.<method-name>

(defn add
  "Adds a star to an item. One of file, file comment, channel,
  or the combination of channel and timestamp. Accessible by
  bots, workspaces, and users"
  [token options]
  (call "stars.add" token options))


(defn list
  "List the stars for a user. Not accessible by bots. Takes
  optional options of count, the number of items per page
  to return, and page, the page number of results to return"
  ([token]
   (list token {}))
  ([token options]
   (call "stars.list" token options)))


(defn remove
  "Removes a star to an item. One of file, file comment, channel,
  or the combination of channel and timestamp. Accessible by
  bots, workspaces, and users"
  [token options]
  (call "stars.add" token options))
