(ns scramblies-ui.state
  (:require [reagent.core :as r]
            [cljs.spec.alpha :as s]))


(s/def ::source
  (s/and
    string?
    (partial re-find #"^[a-zA-Z]+$")))


(s/def ::base
  (s/and
    string?
    (partial re-find #"^[a-zA-Z]+$")))


(s/def ::scramblies-form-fields
  (s/keys :req-un [::source ::base]))


(def form
  (r/atom {:fields {:source ""
                    :base   ""}
           :errors {:source nil
                    :base   nil}
           :status :idle}))


(def form-errors
  {:source "Shouldn't be empty. Only letters are allowed."
   :base   "Shouldn't be empty. Only letters are allowed."})


(defn get-errors [spec data]
  (let [check-result (s/explain-data spec data)]
    (some->> (::s/problems check-result)
      (map (comp first :path))
      (select-keys form-errors))))


(defn get-scramblies-form []
  (let [{:keys [fields]} @form]
    {:fields    fields
     :errors    (get-errors ::scramblies-form-fields fields)
     :is-valid? (s/valid? ::scramblies-form-fields fields)}))


(defn persist-errors [errors]
  (swap! form assoc :errors errors))


(defn set-field [field value]
  (if (vector? field)
    (let [value-path (vec (cons :fields field))
          error-path (vec (cons :errors field))
          form'      (-> @form
                       (assoc-in value-path value)
                       (assoc-in error-path nil))]
      (reset! form form'))

    (js/console.error "field should be a path vector")))


(defn set-status [status]
  (swap! form assoc :status status))


(defn subscribe [path]
  (r/cursor form path))
