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



;;;
;;; Find the number of c's in "abracadabra".
;;;
(defn number-of-c
  "Returns number of c's in the given string."
  [s]
  (if (empty? s)
    0
    (get (frequencies s) \c)))



;;;
;;; Write your own version of filter
;;;

(defn my-filter [pred coll]
  (if (not (seq coll))
    coll
    (loop [head (first coll)
           tail (rest coll)]
      (if (pred head)
        (cons head (my-filter pred tail))
        (my-filter pred tail)))))

(my-filter (fn [x] true) [1 2 3])
(my-filter odd? [1 2 3])



;;;
;;; Find the first 100 prime numbers
;;;

(defn- divisors
  "Checking all the candidates returns only those that are real divisors of x."
  [x candidates]
  (filter (fn [candidate] (zero? (mod x candidate)))
          candidates))

(divisors 10 [2 3 4 5]) ;=> (2, 5)

(defn- is-prime? [x]
  (cond
   (< x 2) false
   (< x 4) true
   :else (let [x-root (Math/sqrt x)
               number-of-divisors (count (divisors x (range 2 (inc x-root))))]
           (zero? number-of-divisors))))

(map is-prime? [1 2 3 4 5 6 7 8 9 10 11])

(defn- all-prime-numbers
  "Generates infinite sequence of prime numbers."
  []
  (filter is-prime? (range)))

(defn prime-numbers
  "Returns first n prime numbers."
   [n]
   (take n (all-prime-numbers)))

(prime-numbers 10)
