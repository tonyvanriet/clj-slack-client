(ns clj-slack-client.auth
  (:require [clj-slack-client.web :as web])
  (:refer-clojure :exclude [test]))


(defn revoke
  "Revokes a token. Callable by workspace and user tokens.
  Passing 1 as the second parameter will enter a testing mode
  which doesn't actually revoke the token."
  ([token]
   (revoke token false))
  ([token test]
   (web/call-and-get-response "auth.revoke"
                              {:token token :test test})))


(defn test
  "Checks authentication and identity. Callable by all token
  types."
  [token]
  (web/call-and-get-response "auth.test" {:token token}))

