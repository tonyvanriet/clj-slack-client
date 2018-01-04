(ns clj-slack-client.channels
  (:require [clj-slack-client.web :as web]))


(defn archive
  "Archives a channel. Acessible by workspace and user (not bots)."
  [token channel]
  (web/call-and-get-response "channels.archive"
                             {:token token :channel channel}))


(defn create
  "Creates a channel. Accessible by workspace and user (not bots)."
  ([token channel-name]
   (create token false))
  ([token channel-name validate]
   (web/call-and-get-response "channels.create"
                              {:token token :name channel-name
                               :validate validate})))


; there are extensive options. check api.slack.com/methods/channels.history
(defn history
  "Fetches history of messages and events from a channel. Accessible
  by bots, workspaces, and users."
  ([token channel]
   (history token channel {}))
  ([token channel options]
   (web/call-and-get-response "channels.history"
                              (merge {:token token :channel channel}
                                     options))))


(defn info
  "Gets the information about a channel. Accessible by bots, workspaces,
  and users."
  ([token channel]
   (info token channel false))
  ([token channel include-locale]
   (web/call-and-get-response "channels.info"
                              {:token token :channel channel
                               :include_locale include-locale})))


(defn invite
  "Invites a user to a channel. Only accessible by workspace or user."
  [token channel user-id]
  (web/call-and-get-response "channels.invite"
                             {:token token :channel channel :user user-id}))


(defn join
  "Joins a channel, creating it if needed. Only accessible by user tokens."
  ([token channel-name]
   (join token channel-name false))
  ([token channel-name validate]
   (web/call-and-get-response "channels.join"
                              {:token token :name channel-name
                               :validate validate})))


(defn kick
  "Removes a user from a channel. Accessible by workspace and user tokens."
  [token channel user-id]
  (web/call-and-get-response "channels.kick"
                             {:token token :channel channel :user user-id}))


(defn leave
  "Leave a channel. Accessible by user only."
  [token channel]
  (web/call-and-get-response "channels.leave"
                             {:token token :channel channel}))


(defn mark
  "Sets the read cursor in a channel. Accessible by bots and users."
  [token channel time-stamp]
  (web/call-and-get-response "channels.mark"
                             {:token token :channel channel
                              :ts time-stamp}))


(defn rename
  "Renames a channel. Accessible by workspace and user tokens."
  ([token channel new-name]
   (rename token channel new-name false))
  ([token channel new-name validate]
   (web/call-and-get-response "channels.rename"
                              {:token token :channel channel
                               :name new-name :validate validate})))


(defn replies
  "Retrieve a thread of messages posted to a channel. Accessible
  by bots, workspaces, and users."
  [token channel thread-ts]
  (web/call-and-get-response "channels.replies"
                             {:token token :channel channel
                              :thread_ts thread-ts}))


(defn set-purpose
  "Sets the purpose for a channel. Accessible by bots, workspaces,
  and users."
  [token channel purpose]
  (web/call-and-get-response "channels.setPurpose"
                             {:token token :channel channel
                              :purpose purpose}))


(defn setTopic
  "Sets the topic for a channel. Accessible by bots, workspaces,
  and users."
  [token channel topic]
  (web/call-and-get-response "channels.setPurpose"
                             {:token token :channel channel
                              :topic topic}))


(defn unarchive
  "Unarchives a channel. Callable by workspace and user tokens only."
  [token channel]
  (web/call-and-get-response "channels.unarchive"
                             {:token token :channel channel}))

