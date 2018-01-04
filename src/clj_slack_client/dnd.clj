(ns clj-slack-client.dnd
  (:require [clj-slack-client.web :as web]))

(defn- cast-int [s]
  "Pick out an integer from a string, or returns the number passed."
  (if (number? s)
    s
    (Integer. (re-find #"\d+" s))))

(defn time-difference
  "Given two time stamps, returns a hashmap with atoms :min and :sec
  giving the time difference. The map also has a :str atom which has
  a string representation of the commonly used time form <min>:<sec>.
  The parameters are casted to integer, so :ts Slack time-stamps can
  be passed as is."
  [start end]
  (let [difference (- (cast-int end) (cast-int start))
        minutes (quot difference 60)
        seconds (mod  difference 60)]
    (hash-map :min minutes
              :sec seconds
              :str (str minutes ":" seconds))))

(defn get-user-dnd
  "Call the Slack dnd.info method for a particular user-id."
  [api-token user-id]
  (web/call-and-get-response "dnd.info"
                             {:user user-id :token api-token}))

(defn get-team-dnd
  "Call the Slack dnd.teamInfo method for the whole team."
  [api-token]
  (web/call-and-get-response "dnd.teamInfo" {:token api-token}))

(defn time-delta
  "Return the time left on a dnd timer from the API response
  and an initial time-stamp."
  [time-stamp response]
  (time-difference time-stamp
                   (:next_dnd_end_ts response)))

; These methods require a non-bot api-token. These have been written
; in the hopes that these methods will be accessible in the future.

(defn set-snooze
  [api-token user-id num-minutes]
  (web/call-and-get-response
    "dnd.setSnooze"
    {:token api-token :num_minutes num-minutes}))

(defn end-snooze
  [api-token]
  (web/call-and-get-response
    "dnd.endSnooze"
    {:token api-token}))

(defn end-dnd
  [api-token]
  (web/call-and-get-response
    "dnd.endDnd"
    {:token api-token}))
