(ns scramblies-ui.api
  (:require [ajax.core :refer [POST]]
            [scramblies-ui.state :as st]))


(defn process-response [{:keys [scramblies]}]
  (st/set-status (if scramblies :success :failure)))


(defn process-error []
  (st/set-status :error))


(defn check-scrumblies [{:keys [source base]}]
  (st/set-status :pending)

  (POST "/scramble"
    {:params          {:source source
                       :base   base}
     :handler         process-response
     :error-handler   process-error
     :format          :json
     :response-format :json
     :keywords?       true}))
