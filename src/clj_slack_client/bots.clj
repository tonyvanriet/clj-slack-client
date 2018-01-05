(ns clj-slack-client.bots
  (:require [clj-slack-client.web :as web]))


(defn info
  "Gets information about a bot user. Callable by all token types."
  ([token]
   (web/call-and-get-response "bots.info" {:token token}))
  ([token bot]
   (web/call-and-get-response "bots.info"
                              {:token token :bot bot})))
