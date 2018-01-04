(ns clj-slack-client.reactions
  (:require [clj-slack-client.web :as web])
  (:refer-clojure :exclude [get list remove]))

; all methods are available by all tokens: bots, workspaces,
; and user. Each method has a large number of options, which
; are all available at api.slack.com/methods/reactions.<method-name>

(defn- call
  "Call the slack api"
  [method-name token options]
  (web/call-and-get-response method-name
                             (assoc aptions :token token)))


(defn add
  "Adds a reaction to an item. You can associate a reaction
  name with one of a file, file comment, or the combination
  of channel and timestamp."
  [token reaction-name options]
  (call "reactions.add" (assoc options :name reaction-name)))


(defn get
  "Gets the reaction for an item. Returns a list of all
  reactions for a single item, which can be a file, file
  comment, channel message, group message, or DM."
  [token options]
  (call "reactions.get" token options))


(defn list
  "Lists the reactions made by a user. Returns the list
  of all items reacted to by a user"
  [token options]
  (call "reactions.list" token options))


(defn remove
  "Removes a reaction from an item. One of file, file comment,
  or the combination of channel and timestamp must be specified."
  [token options]
  (call "reactions.remove" token options))

