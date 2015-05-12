(ns clj-slack-client.team-state-test
  (:require [clojure.test :refer :all]
            [clj-slack-client.team-state :refer :all]))



(deftest id->name-returns-correct-name
  (let [id "bobs-id"
        name "bob"
        team-state {:users [{:id id :name name}]}]
    (set-team-state team-state)
    (testing "unknown id returns nil"
      (is (nil? (id->name "unknown-id"))))
    (testing "known id returns correct name"
      (is (= name (id->name id))))))

