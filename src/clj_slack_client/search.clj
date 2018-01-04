(ns clj-slack-client.search
  (:require [clj-slack-client.web :as web]))

; N.B.: all of these are not bot accessible. Each have extensive
; options available at api.slack.com/methods/search.<method-name>

(defn all
  "Searches for messages and files matching a query."
  ([token query]
   (web/call-and-get-response "search.all"
                              {:token token :query query}))
  ([token query options]
   (web/call-and-get-response "search.all"
                              (merge {:token token :query query} options))))


(defn files
  "Searches for files matching a query."
  ([token query]
   (web/call-and-get-response "search.files"
                              {:token token :query query}))
  ([token query options]
   (web/call-and-get-response "search.files"
                              (merge {:token token :query query} options))))


(defn messages
  "Searches for messages matching a query."
  ([token query]
   (web/call-and-get-response "search.messages"
                              {:token token :query query}))
  ([token query options]
   (web/call-and-get-response "search.messages"
                              (merge {:token token :query query} options))))

