(ns clj-slack-client.team-state)


(def ^:private state (atom nil))


(defn set-team-state
  [state-map]
  (swap! state (fn [_] state-map)))


(defn- self
  []
  (:self @state))

(defn self-id
  []
  (:id (self)))


(defn- id->user
  [id]
  (->> @state
       :users
       (filter #(= (:id %) id))
       (first)))

(defn id->name
  [id]
  (->> (id->user id)
       :name))

(defn- id->channel
  [id]
  (->> @state
       :channels
       (filter #(= (:id %) id))
       (first)))

(defn name->channel
  [name]
  (->> @state
       :channels
       (filter #(= (:name %) name))
       (first)))

(defn bot?
  [user-id]
  (let [user (id->user user-id)]
    (:is_bot user)))


(defn dm?
  [channel-id]
  (.startsWith channel-id "D"))


(defn channel-joined
  [channel]
  (swap! state #(assoc-in % [:channels] (conj (:channels %) channel))))







