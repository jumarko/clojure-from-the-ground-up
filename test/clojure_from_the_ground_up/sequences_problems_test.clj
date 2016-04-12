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
