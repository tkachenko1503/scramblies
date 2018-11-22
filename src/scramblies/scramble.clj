(ns scramblies.scramble
  (:require [clojure.string :refer [split]]
            [clojure.spec.alpha :as s]))


(s/def ::char-seq seqable?)

(defn char-seq->counted-map
  "Creates a map with chars from char-seq as keys and chars count as values"
  [char-seq]
  {:pre [(s/valid? ::char-seq char-seq)]}
  (reduce
    (fn [char-map char]
      (update char-map char #(inc (or % 0))))
    {}
    char-seq))


(s/def ::char-count-map (s/map-of char? number?))

(s/def ::char-count-seq (s/cat :char char? :count number?))

(defn check-char-count
  "Returns true if char exists in source and source contains enough count of those char"
  [source-map [char count]]
  {:pre [(s/valid? ::char-count-map source-map)
         (s/valid? ::char-count-seq [char count])]}
  (let [source-char-count (get source-map char 0)]
    (>= source-char-count count)))


(s/def ::source-str (s/and string? (complement empty?)))

(s/def ::base-str (s/and string? (complement empty?)))

(defn scramble
  "Returns true if a portion of source characters can be rearranged to match base, otherwise returns false"
  [source base]
  {:pre [(s/valid? ::source-str source)
         (s/valid? ::base-str base)]}
  (let [source-map           (char-seq->counted-map source)
        base-map             (char-seq->counted-map base)
        checked-chars-counts (map (partial check-char-count source-map)
                               base-map)]

    (every? true? checked-chars-counts)))
