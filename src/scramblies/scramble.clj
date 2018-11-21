(ns scramblies.scramble
  (:require [clojure.string :refer [split]]))


(defn char-seq->counted-map [char-seq]
  (reduce
    (fn [char-map char]
      (update char-map char #(inc (or % 0))))
    {}
    char-seq))


(defn check-char-count [source-map [char count]]
  (-> char
    (source-map 0)
    (>= count)))


(defn scramble [source base]
  (let [source-seq           (split source #"")
        base-seq             (split base #"")
        source-map           (char-seq->counted-map source-seq)
        base-map             (char-seq->counted-map base-seq)
        checked-chars-counts (map (partial check-char-count source-map)
                               base-map)]

    (every? true? checked-chars-counts)))
