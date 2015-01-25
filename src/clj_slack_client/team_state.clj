(ns clj-slack-client.team-state)


(def state (atom nil))


(defn set-team-state
  [state-map]
  (swap! state (fn [_] state-map)))


(defn self
  []
  (:self @state))


(defn id->user
  [id]
  (->> @state
       :users
       (filter #(= (:id %) id))
       first))


(defn channel-joined
  [channel]
  (swap! state #(assoc-in % [:channels] (conj (:channels %) channel))))







