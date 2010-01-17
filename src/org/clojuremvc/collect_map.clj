
(ns org.clojuremvc.collect-map
  ;(:require )
  ;(:use )
  ;(:import )
  )

(defn partition-with [pred seq]
  "Partitions a sequence into a number of subsequences, where each item of the
  sequence that satisfies (pred item) will start a new subsequence. For example,
  (partition-with #(= % 2) [1 2 3 2 1]) returns [(1) (2 3) (2 1)]. The first
  subsequence always starts with the first value of the original sequence. In
  other words, (partition-with #(= % 1) [1 2 1]) returns [(1 2) (1)] and NOT
  [() (1 2) (1)]. If none of the items in the sequence satisfy (pred item), the
  original sequence is returned as one subsequence, e.g.
  (partition-with #(false) [1 2 1]) returns [(1 2 1)]."
  (loop [acc [] sequence seq]
    (if (empty? sequence)
      acc
      ;; The first item must always be added to the next partition.
      ;; We do not take it into account for determining the next split.
      ;; If we have [3 2 3 4], and split at each 3, we'll get
      ;; [rest-before-split after-split] equal to [[2] [3 4]].
      ;; next-partition will be [3 2].
      (let [[rest-before-split after-split] (split-with (complement pred) (rest sequence))
            next-partition (cons (first sequence) rest-before-split)]
        (recur (conj acc next-partition) after-split)))))

;;;(defn collect-map [seq]
;;;  (partition-with keyword? seq))
  