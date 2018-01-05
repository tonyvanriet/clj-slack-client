(ns clj-slack-client.groups
  (:require [clj-slack-client.web :as web])
  (:refer-clojure :exclude [list]))

(def api-module "groups")

(defn- call
  ([method-name token]
   (web/call-and-get-response (str api-module "." method-name) {:token token}))
  ([method-name token channel]
   (web/call-and-get-response (str api-module "." method-name)
                              {:token token :channel channel}))
  ([method-name token channel options]
   (web/call-and-get-response (str api-module "." method-name)
                              (merge {:token token :channel channel}
                                     options))))


(defn archive
  "Archives a private channel. Callable by workspace and user."
  [token channel]
  (call "archive" token channel))


(defn create
  "Creates a private channel. Callable by workspace and user."
  ([token channel-name]
   (create token channel-name false))
  ([token channel-name validate]
   (web/call-and-get-response "create"
                              {:token token :name channel-name
                               :validate validate})))


(defn create-child
  "Clones and archives a private channel. Accessible by user and workspcae
  tokens."
  [token channel]
  (call "createChild" token channel))


(defn history
  "Fetches history of messages and events from a private channel.
  Callable by bot, user, and workspace tokens."
  ([token channel]
   (history token channel {}))
  ([token channel options]
   (call "history" token channel options)))


(defn info
  "Gets information about a private channel. Acessible by all token types."
  ([token channel]
   (token channel false))
  ([token channel include-locale]
   (call "info" token channel include-locale)))


(defn invite
  "Invites a user to a private channel. Accessible by workspace
  and user tokens."
  [token channel user]
  (call "invite" token channel {:user user}))


(defn kick
  "Removes a user from a private channel. Callable by user and
  workspace tokens." 
  [token channel user]
  (call "kick" token channel {:user user}))


(defn leave
  "Leaves a private channel. Only callable by user."
  [token channel]
  (call "leave" token channel))


(defn list
  "Lists all private channels that the calling user has access to.
  Callable by all token types."
  ([token]
   (call "list" token))
  ([token options]
   (web/call-and-get-response "groups.list" {:token token})))


(defn mark
  "Sets the read cursor in a private channel. Callable by
  bot and user token types."
  [token channel time-stamp]
  (call "mark" token channel {:ts time-stamp}))


(defn open
  "Opens a private channel. Callable by bot and user tokens."
  [token channel]
  (call "open" token channel))


(defn rename
  "Renames a private channel. Supported by user and workspace
  token types."
  ([token channel new-name]
   (rename token channel new-name false))
  ([token channel new-name validate]
   (call "rename" token channel {:name new-name :validate validate})))


(defn replies
  "Retrieve a thread of messages posted to a private channel.
  Accessible by user and workspace tokens."
  [token channel time-stamp]
  (call "replies" token channel {:ts time-stamp}))


(defn set-purpose
  "Sets the purpose for a private channel. Supported by all token types."
  [token channel purpose]
  (call "setPurpose" token channel {:purpose purpose}))


(defn set-topic
  "Sets the topic for a private channel. Supported by all token types."
  [token channel topic]
  (call "setPurpose" token channel {:topic topic}))


(defn unarchive
  "Unarchives a private channel. Accessible by workspack and user types."
  [token channel]
  (call "unarchive" token channel))

