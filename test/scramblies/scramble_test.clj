(ns scramblies.scramble_test
  (:require [clojure.test :refer :all]
            [scramblies.scramble :refer [scramble char-seq->counted-map check-char-count]]))


(deftest char-seq->counted-map-test
  (testing "char sequence to {:char char-count} map"
    (is (= (char-seq->counted-map [\a \b \c \b])
          {\a 1 \b 2 \c 1})))

  (testing "empty sequence to empty map"
    (is (= (char-seq->counted-map [])
          {})))

  (testing "should work with lists too"
    (is (= (char-seq->counted-map (list))
          {}))

    (is (= (char-seq->counted-map (list \a \b \c \b))
          {\a 1 \b 2 \c 1})))

  (testing "should work with sets too"
    (is (= (char-seq->counted-map #{})
          {}))

    (is (= (char-seq->counted-map #{\a \b \c})
          {\a 1 \b 1 \c 1})))

  (testing "should work with nil or string instead of a seq"
    (is (= (char-seq->counted-map nil)
          {}))

    (is (= (char-seq->counted-map "abcb")
          {\a 1 \b 2 \c 1})))

  (testing "should throw an exception on something that is no seqable"
    (let [exception (try
                      (char-seq->counted-map 123)
                      (catch Throwable e
                        e))]
      (is (not (nil? exception))))))


(deftest check-char-count-test
  (testing "true if char presented in char map and is enough quantity"
    (is (= (check-char-count {\a 2} [\a 1])
          true)))

  (testing "false if char not presented in char map or is not enough quantity"
    (is (= (check-char-count {\a 2} [\a 3])
          false))

    (is (= (check-char-count {\a 2} [\b 1])
          false)))

  (testing "should throw an exception on invalid params"
    (let [exception (try
                      (check-char-count nil [\b 1])
                      (catch Throwable e
                        e))]
      (is (not (nil? exception))))))


(deftest scramble-test
  (testing "true if a portion of source characters can be rearranged to match base"
    (is (= (scramble "rekqodlw" "world")
          true)))

  (testing "otherwise returns false"
    (is (= (scramble "katas" "steak'")
          false)))

  (testing "should throw an exception on invalid params"
    (let [exception (try
                      (scramble nil :qwe)
                      (catch Throwable e
                        e))]
      (is (not (nil? exception))))))
