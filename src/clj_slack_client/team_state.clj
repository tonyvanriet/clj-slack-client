(ns clj-slack-client.team-state)


(def state (atom nil))


(defn set-team-state
  [state-map]
  (swap! state (fn [_] state-map)))


(defn get-self
  []
  (:self @state))


(defn get-user
  [user-id]
  (->> @state
       :users
       (filter #(= (:id %) user-id))
       first))


(defn channel-joined
  [channel]
  (swap! state #(assoc-in % [:channels] (conj (:channels %) channel))))







