(ns org.clojuremvc.test.list-util-test
  (:use clojure.contrib.test-is 
    org.clojuremvc.list-util))

(deftest flatten-1-test
  (is (= [1 2 3 4]    (flatten-1 [[1 2][3 4]]))  "flatten-1 should flatten first level of tree")
  (is (= [1 2 3]       (flatten-1 [[1 2] 3]))     "flatten-1 should leave non-lists on first level of tree intact")
  (is (= nil           (flatten-1 nil))           "flatten-1 should not change non-lists")
  (is (= 12            (flatten-1 12))            "flatten-1 should not change non-lists")
  (is (= [1 [2] 3]     (flatten-1 [[1 [2]] [3]])) "flatten-1 should not flatten deeper than first level."))
