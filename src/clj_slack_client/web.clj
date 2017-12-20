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

(defn api-call-dispatch [m & args]
  [(count args) (map? (first args))])

; somewhat developer-friendly way to get api responses
; without having to use thread macros
(defmulti call-and-get-response
  "Call a Slack web API and get the response as a map. Pass the
  method name as the first parameter and then either the parameter
  map or the api-token (if you don't need to use any other parameters)"
  #'api-call-dispatch)

(defmethod call-and-get-response [0 false]
  [method-name]
  call-and-get-response method-name {})

(defmethod call-and-get-response [1 true]
  [method-name params]
  (->> params
       (call-slack-web-api method-name)
       (get-api-response)))

(defmethod call-and-get-response [1 false]
  [method-name api-tok]
  (->> {:token api-tok}
       (call-slack-web-api method-name)
       (get-api-response)))

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


(defn channels-list
  ([api-token]
   (channels-list api-token false))
  ([api-token exclude-archived]
  (->> {:token api-token :exclude_archived (if exclude-archived 1 0)}
       (call-slack-web-api "channels.list")
       (get-api-response)
       :channels)))


(defn im-open
  [api-token user-id]
  (->> {:token api-token :user user-id}
       (call-slack-web-api "im.open")
       (get-api-response)
       :channel
       :id))


(defn groups-list
  ([api-token]
   (channels-list api-token false))
  ([api-token exclude-archived]
   (->> {:token api-token :exclude_archived (if exclude-archived 1 0)}
        (call-slack-web-api "groups.list")
        (get-api-response)
        :groups)))


(defn im-list
  [api-token]
  (->> {:token api-token}
       (call-slack-web-api "im.list")
       (get-api-response)
       :ims))


(defn team-info
  [api-token]
  (->> {:token api-token}
       (call-slack-web-api "team.info")
       (get-api-response)
       :team))


(defn users-list
  [api-token]
  (->> {:token api-token}
       (call-slack-web-api "users.list")
       (get-api-response)
       :members))


(defn reactions-add
  [api-token emoji-name channel-id timestamp]
  (->> {:token api-token :name emoji-name :channel channel-id :timestamp timestamp}
       (call-slack-web-api "reactions.add")
       (get-api-response)))

(defn reactions-get
  [api-token channel-id timestamp full?]
  (->> {:token api-token :channel channel-id :timestamp timestamp :full full?}
       (call-slack-web-api "reactions.get")
       (get-api-response)))
