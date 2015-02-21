(ns clj-slack-client.rtm-transmit
  (:gen-class)
  (:require
    [cheshire.core :as json]
    [clj-slack-client
     [team-state :as state]
     [connectivity :as conn]]
    [clojure.string :as str]))


(defn linkify
  [text]
  (str/replace text #"U[A-Z0-9]{8}" #(str "<@" %1 ">")))


(defn escape
  "& replaced with &amp;
  < replaced with &lt;
  > replaced with &gt;"
  [text]
  (-> text
      (str/replace #"\&" "&amp;")
      (str/replace #"\<" "&lt;")
      (str/replace #"\>" "&gt;")))


(defn message-json
  [channel-id text]
  (json/encode {:id      1
                :type    "message"
                :channel channel-id
                :text    text}))


(defn say-message
  [channel-id text]
  (->> text
       ; slack says to escape messages going to the server,
       ; but if I do, they end up double escaped in the reply.
       #_(escape)
       (linkify)
       (message-json channel-id)
       (conn/send-to-websocket)))

