(ns clj-slack-client.web
  (:require
    [cheshire.core :as json]
    [aleph.http :as aleph]))


(def slack-api-base-url "https://slack.com/api")


(defn- get-api-response
  "Takes a full http response map and returns the api response as a map."
  [http-response]
  (let [response-body-bytes (:body http-response)
        response-body-json (byte-streams/to-string response-body-bytes)
        api-response (json/parse-string response-body-json true)]
    api-response))


(defn- call-slack-web-api
  ([method-name]
    (call-slack-web-api method-name {}))
  ([method-name params]
    (let [method-url-base (str slack-api-base-url "/" method-name)]
      @(aleph/post method-url-base {:query-params params}))))


(defn api-test
  [params]
  (call-slack-web-api "api.test" params))

(defn rtm-start
  [api-token]
  (->> {:token api-token}
       (call-slack-web-api "rtm.start")
       (get-api-response)))


(defn channels-setTopic
  [api-token channel-id topic]
  (->> {:token api-token :channel channel-id :topic topic}
       (call-slack-web-api "channels.setTopic")
       (get-api-response)))


(defn im-open
  [api-token user-id]
  (->> {:token api-token :user user-id}
       (call-slack-web-api "im.open")
       (get-api-response)
       :channel
       :id))
