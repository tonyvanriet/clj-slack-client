(ns clj-slack-client.apps.permissions
  (:require [clj-slack-client.web :as web]))


(defn info
  "Returns a list of permissions this app has on a team. Callable
  only by workspace token."
  [token]
  (web/call-and-get-response "apps.permissions.info" {:token token}))


(defn request
  "Allows an app to request additional scopes. Supported by the
  workspace token type."
  [token scopes trigger-id]
  (web/call-and-get-response "apps.permissions.request"
                             {:token token :scopes scopes
                              :trigger_id trigger-id}))

