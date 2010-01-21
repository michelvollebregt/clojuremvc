
(ns org.clojuremvc.list-util
  (:use clojure.contrib.core))

(defn flatten-1 
  "Flattens only the first level of a given sequence, e.g. [[1 2][3]] becomes
   [1 2 3], but [[1 [2]] [3]] becomes [1 [2] 3]."
  [seq]
  (if (or (not (seqable? seq)) (nil? seq))
    seq ; if seq is nil or not a sequence, don't do anything
    (loop [acc [] [elt & others] seq]
      (if (nil? elt) acc
        (recur
          (if (seqable? elt)
            (apply conj acc elt) ; if elt is a sequence, add each element of elt
            (conj acc elt))      ; if elt is not a sequence, add elt itself directly
          others)))))
