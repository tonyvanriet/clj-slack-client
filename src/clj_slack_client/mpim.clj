(ns clj-slack-client.mpim
  (:require [clj-slack-client.web :as web])
  (:refer-clojure :exclude [list]))

(def api-module "mpim")

(defn- call
  ([method-name token channel]
   (web/call-and-get-response (str api-module "." method-name)
                              {:token token :channel channel}))
  ([method-name token channel options]
   (web/call-and-get-response (str api-module "." method-name)
                              (merge {:token token :channel channel}
                                     options))))

(defn close
  "Close a multiparty direct message channel.
  Accessible by all token types."
  [token channel]
  (call "close" token channel))


(defn history
  "Fetches history of messages and events from a 
  multiparty message channel. Accessible by all token types."
  ([token channel]
   (history token channel {}))
  ([token channel options]
   (call "history" token channel options)))


(defn list
  "Lists multiparty message channels for the calling user.
  Accessible by all tokne types."
  ([token]
   (list token {}))
  ([token options]
   (web/call-and-get-response "im.list"
                              (assoc options :token token))))


(defn mark
  "Sets the read cursor in a multiparty message channel.
  Accessible by bot and user token types."
  [token channel time-stamp]
  (call "mark" token channel {:ts time-stamp}))


(defn open
  "Opens a multiparty message channel. Supported by bot and user tokens.
  Users are separated by commas."
  ([token users]
   (open token users {}))
  ([token users options]
   (web/call-and-get-response "im.open"
                              (merge {:token token :users users}
                                     options))))


(defn replies
  "Retrieve a thread of messages posted to a multiparty message conversation.
  Accessible by all token types."
  [token channel time-stamp]
  (call token channel {:thread_ts time-stamp}))

