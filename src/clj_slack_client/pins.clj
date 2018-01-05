(ns clj-slack-client.pins
  (:require [clj-slack-client.web :as web])
  (:refer-clojure :exclude [list remove]))


(def api-module "pins")

(defn- call
  ([method-name token channel]
   (call method-name token channel {}))
  ([method-name token channel options]
   (web/call-and-get-response (str api-module "." method-name)
                              (merge {:token token :channel channel}
                                     options))))


(defn add
  "Pins an item to a channel. Accessible by all token types.
  One of file, file comment, or time stamp must also be
  specified."
  [token channel options]
  (call "add" token channel options))


(defn list
  "Lists items pinned to a channel. Accessible by all token types."
  [token channel]
  (call "list" token channel))


(defn remove
  "Un-pins an item from a channel. Accessible by all token types.
  One of file, file comment, or time-stamp must be specified in
  the options."
  [token channel options]
  (call "remove" token channel options))
