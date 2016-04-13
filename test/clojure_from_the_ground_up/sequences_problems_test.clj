(ns clojure-from-the-ground-up.sequences-problems-test
  (:require [clojure-from-the-ground-up.sequences-problems :refer :all]
            [clojure.test :refer :all]))

(deftest  is-palindrome
  (are [s] (palindrome? s)
       "a"
       "aha"
       "noon"
       "rotor"
       "terret"))

(deftest  not-palindrome
  (are [s] (not ( palindrome? s))
       "hey"
       "none"
       "motor"
       "tongue"))



(deftest check-number-of-c
  (is (= 1 (number-of-c "abracadabra"))))



(deftest filter-always-true
  (let [true-fn (fn [x] true)]
    (is (empty? (my-filter true-fn [])))
    (is (= [1 2 3] (my-filter true-fn [1 2 3])))))

(deftest filter-even-numbers
  (is ( =
        [0 2 4 6]
        (my-filter even? [0 1 2 3 4 5 6 7]))))



(deftest get-prime-numbers
  (is (= [2 3 5 7 11 13 17] (prime-numbers 7))))
