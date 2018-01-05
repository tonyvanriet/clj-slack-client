(ns clj-slack-client.emoji
  (:require [clj-slack-client.web :as web])
  (:refer-clojure :exclude [list]))


(defn list
  "List custom emoji for a team. Callable by all token types."
  [token]
  (web/call-and-get-response "emoji.list" {:token token}))
