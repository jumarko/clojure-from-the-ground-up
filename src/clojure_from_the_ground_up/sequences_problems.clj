(ns clojure-from-the-ground-up.sequences-problems
  "Problems from the bottom of sequences tutorial 'Clojure from the ground up: sequences' https://aphyr.com/posts/304-clojure-from-the-ground-up-sequences")


;;;
;;; 1. Write a function to find out if a string is a palindrome - that is if it looks
;;;    the same forwards and backwards.
;;;

(defn- equal-in-reverse-order [[coll1 coll2]]
  (= coll1 (reverse coll2)))

(defn palindrome? [s]
  (let [length (count s)
        split-point (Math/floor (/ length 2))
        halves (split-at split-point s)]
    (if (even? length)
      (equal-in-reverse-order halves)
      ;; when there's odd number of characters we need to ignore the first character from second half
      (equal-in-reverse-order (vector (first halves) ( rest ( last halves)))))
    ))
