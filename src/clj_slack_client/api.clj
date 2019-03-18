(ns clj-slack-client.api
  (:require [clj-slack-client.web :as web])
  (:refer-clojure :exclude [test]))


(defn test
  "Checks API calling code. Callable by workspace and user tokens."
  ([]
   (web/call-and-get-response "api.test"))
  ([options]
   (web/call-and-get-response "apt.test" options)))
