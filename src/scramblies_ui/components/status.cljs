(ns scramblies-ui.components.status
  (:require [reagent.core :refer [adapt-react-class as-element]]
            [react-spring :refer [Transition]]
            [scramblies-ui.state :as st]))


(def transition (adapt-react-class Transition))

(defn status []
  (let [form-status (st/subscribe [:status])]
    (fn []
      (let [form-status @form-status]
        [:div {:class "status"}
         [transition {:items form-status
                      :from  {:opacity 0}
                      :enter {:opacity 1}
                      :leave {:opacity 0}}
          (fn [status-name]
            (fn [style]
              (as-element
                (case status-name
                  "pending"
                  [:img {:class "status__icon"
                         :src   "/img/puff.svg"
                         :width 60
                         :style style}]

                  "success"
                  [:img {:class "status__icon"
                         :src   "/img/happy-face.svg"
                         :width 100
                         :style style}]

                  "failure"
                  [:img {:class "status__icon"
                         :src   "/img/sad-face.svg"
                         :width 100
                         :style style}]

                  "error"
                  [:div
                   [:img {:class "status__icon"
                          :src   "/img/crying-face.svg"
                          :width 100
                          :style style}]
                   [:p {:class "status__error-message"}
                    "Sorry! You've got an error, we are working on that."]]

                  nil))))]]))))
