
(ns org.clojuremvc.test.collect-map-test
  ;(:require )
  (:use clojure.contrib.test-is
    org.clojuremvc.collect-map)
  ;(:import )
  )

(deftest partition-with-test
  (is (= [[1 2] [3 4]]      (partition-with #(= % 3) [1 2 3 4])))
  (is (= [[1] [2 3] [2 1]]  (partition-with #(= % 2) [1 2 3 2 1])))
  (is (= [])                (partition-with #(= % 4) []))
  (is (= [[2 3 4]]            (partition-with #(= % 1) [2 3 4])))
  (is (= [[2 3] [2 3]]      (partition-with #(= % 2) [2 3 2 3])))
  (is (= [[1 2 3] [4]]      (partition-with #(= % 4) [1 2 3 4])))
  (is (= [[:a 1 2] [:b 3]]  (partition-with keyword? [:a 1 2 :b 3]))))

;;;(deftest collect-map-test
;;;  (is (= {:a [1] :b [2]} (collect-map [:a 1 :b 2])))
;;;  (is (= {:a [1 2] :b [3 4]} (collect-map [:a 1 2 :b 3 4]))))
  