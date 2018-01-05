(ns clj-slack-client.team
  (:require [clj-slack-client.web :as web]))


(defn access-logs
  "Gets the access logs for the current team. Only accessible by user."
  ([token]
   (access-logs {}))
  ([token options]
   (web/call-and-get-response "team.accessLogs"
                              (assoc options :token token))))


(defn billable-info
  "Gets the billable users information for the current team.
  Only accessible by user."
  ([token]
   (web/call-and-get-response "team.billableInfo" {:token token}))
  ([token user]
   (web/call-and-get-response "team.billableInfo"
                              {:token token :user user})))


(defn info
  "Gets information about the current team. Accessible by all token types."
  [token]
  (web/call-and-get-response "team.info" {:token token}))


(defn integration-logs
  "Gets the integration logs for the current team. Accessible by workspace
  and user token types."
  ([token]
   (integration-logs {}))
  ([token options]
   (web/call-and-get-response "team.integrationLogs"
                              (assoc options :token token))))

