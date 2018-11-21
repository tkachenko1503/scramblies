(ns scramblies-ui.core
  (:require [reagent.core :as r]
            [reagent.interop :refer-macros [$]]
            [scramblies-ui.components.title :refer [title]]
            [scramblies-ui.components.form :refer [form]]
            [scramblies-ui.components.status :refer [status]]))


(defn dev-setup []
  (when goog.DEBUG
    (enable-console-print!)))


(defn app []
  [:<>
   [title]
   [form]
   [status]])


(defn mount-root []
  (r/render
    [app]
    ($ js/document getElementById "app")))


(defn ^:export main []
  (dev-setup)
  (mount-root))
