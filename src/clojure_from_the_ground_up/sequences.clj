(ns clojure-from-the-ground-up.sequences)

;;;;
;;;; Examples from sequences blog post: https://aphyr.com/posts/304-clojure-from-the-ground-up-sequences
;;;;

(def numbers [1 2 3])

(inc (nth numbers 0))

[(inc (nth numbers 0)) (inc (nth numbers 1)) (inc (nth numbers 2))]


;;; Recursion

(first [1 2 3])

(rest [1 2 3])

(cons 1 [2 3 4])

(defn inc-first [nums]
  (cons (inc (first nums))
        (rest nums)))
(inc-first [1 2 3 4])

;; better version of inc-first to avoid NPE
(defn inc-first [nums]
  (if (first nums)
    (cons (inc (first nums))
          (rest nums))
    (list)))
(inc-first [])
(inc-first [1 2 3 4])

(defn inc-more [nums]
  (if (first nums)
    (cons (inc (first nums))
          (inc-more (rest nums)))))
(inc-more [1 2 3 4])


;;; Generalizing from inc

(defn transform-all [f xs]
  (if (first xs)
    (cons (f (first xs))
          (transform-all f (rest xs)))
    (list)))
(transform-all inc [1 2 3 4])
(transform-all keyword ["bell" "hooks"])


;;; Building sequences

(take 10 (iterate inc 0))

(take 10 (repeat :hi))

(take 3 (repeatedly rand))

;; range
(range 5)
(range 2 10)
(range 0 100 5)


;;; Transforming sequences

(map (fn [n vehicle] (str "I've got " n " " vehicle "s"))
     [0 200 9]
     ["car" "traing" "kiteboard"])

(map +
     [1 2 3]
     [4 5 6]
     [7 8 9])

(map-indexed (fn [index element] (str index ". " element))
             ["erlang" "ruby" "haskell"])

(concat [1 2 3] [:a :b :c] [4 5 6])

(interleave [:a :b :c] [1 2 3])

(interpose :and [1 2 3 4])

(reverse [1 2 3])

(reverse "woolf")
(apply str (reverse "woolf"))

(shuffle [1 2 3 4])
(apply str (shuffle (seq "abracadabra")))


;;; Subsequences

(take 3 (range 10))
(drop 3 (range 10))
(take-last 3 (range 10))
(drop-last 3 (range 10))

(take-while pos? [3 2 1 0 -1 -2 10])

(split-at 4 (range 10))
(split-with number? [1 2 3 :mark 4 5 6 :mark 7])

(filter pos? [1 5 -4 -7 3 0])

(remove string? [1 "turing" :apple])

(partition 2 [:cats 5 :bats 27 :crocodiles 0])
(partition-by neg? [1 2 3 2 1 -1 -2 -3 -2 -1 1 2])


;;; Collapsing sequences

(frequencies [:meow :mrrrow :meow :meow])

(group-by :first [ { :first "Li" :last "Zhou'"}
                   { :first "Sarah" :last "Lee"}
                   { :first "Sarah" :last "Dunn"}
                   { :first "Li" :last "O'Toole"}])

;; the most general way to collapse a sequence is reduce

(reduce + [1 2 3 4])

;; show the reduction steps
(reductions + [1 2 3 4])

(reduce conj #{} [:a :b :b :b :a :a])

(into {} [[:a 2] [:b 3]])
(into (list) [1 2 3 4])
(reduce conj [] [1 2 3 4])

(defn my-map [f coll]
  (reduce (fn [output element]
            (conj output (f element)))
          []
          coll))
(my-map inc [1 2 3 4])

(defn my-take-while [f coll]
  (reduce (fn [out elem]
            (if (f elem)
              (conj out elem)
              (reduced out)))
          []
          coll))
(my-take-while pos? [2 1 0 -1 0 1 2])

(def infseq (map inc (iterate inc 0)))
(realized? infseq)
(take 10 infseq)
(realized? infseq)


;;; Putting it all together
;;; Find sum of the products of consecutive pairs of the first 1000 odd integers.

(reduce +
        (take 1000
              (map (fn [[x1 x2]] (* x1 x2))
                   (partition 2 1
                              (filter odd?
                                      (iterate inc 0))))))

;; and cleaner solution using threading macro
(->> 0
     (iterate inc)
     (filter odd?)
     (partition 2 1)
     (map (fn [[x1 x2]] (* x1 x2)))
     (take 1000)
     (reduce +))
