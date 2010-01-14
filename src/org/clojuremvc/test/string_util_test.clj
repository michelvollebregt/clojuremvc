(ns org.clojuremvc.test.string-util-test
  (:use org.clojuremvc.string-util clojure.contrib.test-is)
  (:gen-class))

(deftest strip-test
  (is (= ".abacadabra" (strip "bubabaluba.abacadabra" #(.indexOf % "."))))
  (is (= "" (strip "bubabaluba." #(+ (.indexOf % ".") 1))))
  (is (thrown? StringIndexOutOfBoundsException (strip "ba" #(.indexOf % ".")))))

(deftest tostr-test
  (is (= "johannes" (tostr :johannes)))
  (is (= "jacobus" (tostr :jacobus)))
  (is (= "[1 2]" (tostr [1 2]))))

(deftest keys-to-strings-test
  (is (= {"ab" 1 "cd" 2} (keys-to-strings {:ab 1 :cd 2})))
  (is (= {"ab" :1 "cd" :2} (keys-to-strings {"ab" :1 "cd" :2}))))