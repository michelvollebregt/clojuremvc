
(ns org.clojuremvc.test.param-util-test
  (:use clojure.contrib.test-is
    org.clojuremvc.param-util)
  (:import 
    [java.util HashMap]
    [javax.servlet.http HttpServletRequest])
  )

(deftest param-retrieval-test
  (is (thrown? Exception (param-retrieval :unknown (symbol 'pedestrian) (symbol 'request)))))

(comment
(deftest params-retrieval-test
  (let [req (proxy [HttpServletRequest] []
              (getParameterMap [] (java.util.HashMap. {"bike" 12 "car" 23})))]
    (is (= [12 23] (eval `(let ~(params-retrieval [:request 'bike 'car] 'req) [bike car]))))
    (is (= [12 23] (let [[bike car]
 [(.. req getParameterMap (get "bike")) (.. req getParameterMap (get "car"))]] [bike car])))))

  )
