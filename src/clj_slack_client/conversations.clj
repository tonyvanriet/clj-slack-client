(ns clj-slack-client.conversations
  (:require [clj-slack-client.web :as web])
  (:refer-clojure :exclude [list]))

(def api-module "conversations")

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
  "Archives a conversation. Callable only by user."
  [token channel]
  (call "archive" token channel))


(defn close
  "Closes a direct message. Callable by user and bot tokens."
  [token channel]
  (call "close" token channel))


(defn create
  "Initiates a public or private channel-based conversation.
  Callable only by user token."
  ([token channel-name]
   (create token channel-name false))
  ([token channel-name is-private?]
   (web/call-and-get-response "create"
                              {:token token :name channel-name
                               :is_private is-private?})))


(defn history
  "Fetches a conversation's history of messages and events.
  Callable by user and bot tokens."
  ([token channel]
   (history token channel {}))
  ([token channel options]
   (call "history" token channel options)))


(defn info
  "Retrieve information about a conversation. Callable by user
  and bot tokens."
  ([token channel]
   (token channel false))
  ([token channel include-locale]
   (call "info" token channel include-locale)))


(defn invite
  "Invites users to a channel. Callable by user only. Users
  are comma separated."
  [token channel users]
  (call "invite" token channel {:users users}))


(defn join
  "Joins an existing conversation. Callably by user only."
  [token channel]
  (call "join" token channel))


(defn kick
  "Removes a user from a conversation. Callable by user only."
  [token channel user]
  (call "kick" token channel {:user user}))


(defn leave
  "Leaves a conversation. Only callable by user."
  [token channel]
  (call "leave" token channel))


(defn list
  "Lists all channels in a Slack team. Accessible by bot and user."
  ([token]
   (call "list" token))
  ([token options]
   (web/call-and-get-response "conversations.list" {:token token})))


(defn members
  "Retrieve the members of a conversation. Accessible by bots and users."
  ([token channel]
   (call "members" token channel))
  ([token channel options]
   (call "members" token channel options)))


(defn open
  "Opens or resumes a direct or multi-person message. Callable by bot
  and user tokens."
  [token options]
  (web/call-and-get-response "conversations.open"
                             (assoc options :token token)))


(defn rename
  "Renames a conversation. Callable only by user."
  [token channel new-name]
  (call "rename" token channel {:name new-name}))


(defn replies
  "Retrieve a thread of messages posted to a conversation. Accessible
  by bot and user tokens."
  ([token channel time-stamp]
   (call "replies" token channel {:ts time-stamp}))
  ([token channel time-stamp options]
   (call "replies" token channel
         (assoc options :ts time-stamp))))


(defn set-purpose
  "Sets the purpose for a conversation. Supported by bot and user tokens."
  [token channel purpose]
  (call "setPurpose" token channel {:purpose purpose}))


(defn set-topic
  "Sets the topic for a conversation. Supported by bot and user tokens."
  [token channel topic]
  (call "setPurpose" token channel {:topic topic}))


(defn unarchive
  "Reverses conversation archival. Supported by users only."
  [token channel]
  (call "unarchive" token channel))

