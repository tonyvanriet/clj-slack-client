(ns clj-slack-client.chat
  (:require [clj-slack-client.web :as web])
  (:refer-clojure :exclude [update]))


(defn delete
  "Deletes a message. Accessible by bots, workspaces, and users.
  When used by a bot, may delete only messages posted by that
  bot user."
  ([token channel time-stamp]
   (delete token channel time-stamp false))
  ([token channel time-stamp as-user]
   (web/call-and-get-response "chat.delete"
                              {:token token :channel channel
                               :ts time-stamp :as_user as-user})))


(defn get-permalink
  "Retrieve a permalink URL for a specific extant message. Easily
  exchange a message timestamp and a channel ID for a permalink
  to that message. Accessible by bots and users."
  [token channel time-stamp]
  (web/call-and-get-response "chat.getPermalink"
                             {:token token :channel channel
                              :message_ts time-stamp}))


(defn me-message
  "Share a me message into a channel. Accessible by bots and users."
  [token channel text]
  (web/call-and-get-response "chat.meMessage"
                             {:token token :channel channel :text text}))


(defn post-ephemeral
  "Sends an ephemeral message to a user in a channel. Accessible
  by bots, workspaces, and users."
  ([token channel text user]
   (post-ephemeral token channel text user {}))
  ([token channel text user options]
   (web/call-and-get-response "chat.postEphemeral"
                              (merge {:token token :channel channel
                                      :text text :user user}
                                     options))))


(defn post-message
  "Sends a message to a channel. Accessible by bots, workspaces,
  and users."
  ([token channel text]
   (post-message token channel text {}))
  ([token channel text options]
   (web/call-and-get-response "chat.postMessage"
                              (merge {:token token :channel channel :text text}
                                     options))))


(defn unfurl
  "Provide custom unfurl behavior for user posted URLs. Accessible
  by workspaces and users."
  ([token channel time-stamp unfurls]
   (unfurl token channel time-stamp unfurls {}))
  ([token channel time-stamp unfurls options]
   (web/call-and-get-response "chat.unfurl"
                              {:token token :channel channel
                               :ts time-stamp :unfurls unfurls})))


(defn update
  "Updates a message. Accessible by bots, workspaces, and users"
  ([token channel text time-stamp]
   (update token channel text time-stamp {}))
  ([token channel text time-stamp options]
   (web/call-and-get-response "chat.update"
                              (merge {:token token :channel channel
                                      :ts time-stamp :text text}
                                     options))))

