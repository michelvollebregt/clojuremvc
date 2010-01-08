
(ns org.clojuremvc.string-util)

(defn strip
  "Takes the substring of a string, starting at the index returned by
   the application of (function string)."
  [string function]
  (.substring string (function string)))

(defn tostr
  "Replaces the value by a string. e.g. :a becomes \"a\"."
  [val]
  (condp = (class val)
    clojure.lang.Keyword (name val)
    (str val)))

(defn keys-to-strings
  "Takes a map and converts its keys into strings, e.g. {:a 1} becomes {\"a\" 1}"
  [a-map]
  (loop [restmap a-map acc {}]
    (if (empty? restmap)
      acc
      (let [[key val] (first restmap)]
        (recur (rest restmap) (conj acc [(tostr key) val]))))))



