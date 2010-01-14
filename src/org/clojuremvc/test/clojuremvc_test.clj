(ns org.clojuremvc.test.clojuremvc-test
  (:gen-class)
  (:use clojure.contrib.test-is
    org.clojuremvc.test.string-util-test))

(defn -main []
  (run-all-tests #"org.clojuremvc.test.*"))