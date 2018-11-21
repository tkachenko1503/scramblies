(ns scramblies-ui.components.form
  (:require [reagent.interop :refer-macros [$]]
            [scramblies-ui.state :as st]
            [scramblies-ui.api :as api]))


(defn validate-and-submit [event]
  ($ event preventDefault)

  (let [{:keys [is-valid? fields errors]} (st/get-scramblies-form)]
    (if is-valid?
      (api/check-scrumblies fields)
      (st/persist-errors errors))))


(defn form []
  (let [source-error (st/subscribe [:errors :source])
        base-error   (st/subscribe [:errors :base])
        form-status  (st/subscribe [:status])]
    (fn []
      [:form {:no-validate true
              :class       "form"
              :on-submit   validate-and-submit}
       [:div {:class "form__fields"}
        [:section {:class "form__field"}
         [:p
          "Enter source word"]

         [:input {:type      "text"
                  :class     "form__input"
                  :on-change #(st/set-field [:source] ($ % :target.value))}]

         [:label {:class "form__input-message"}
          @source-error]]

        [:section {:class "form__field"}
         [:p
          "Enter base word"]

         [:input {:type      "text"
                  :class     "form__input"
                  :on-change #(st/set-field [:base] ($ % :target.value))}]

         [:label {:class "form__input-message"}
          @base-error]]]

       [:div {:class "form__submit"}
        [:input {:class    "form__action"
                 :type     "submit"
                 :value    "Check Now"
                 :disabled (= :pending @form-status)}]]])))
