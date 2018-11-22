(ns scramblies.core
  (:gen-class)
  (:require [org.httpkit.server :refer [run-server]]
            [environ.core :refer [env]]
            [compojure.route :refer [files not-found]]
            [compojure.handler :refer [site]]
            [compojure.core :refer [defroutes GET POST]]
            [ring.middleware.json :as json]
            [scramblies.scramble :refer [scramble]]))


(defn home []
  {:status  200
   :headers {"Content-Type" "text/html"}
   :body    (slurp "public/index.html")})


(defn scramble-response [source base]
  (try
    {:status 200
     :body   {:scramblies (scramble source base)}}
    (catch Throwable e
      {:status 422
       :body   {:error   e
                :message (.getMessage e)}})))


(defroutes scramblies-routes
  (GET "/" []
    (home))

  (POST "/scramble" {{:keys [source base]} :body}
    (scramble-response source base))

  (files "/")

  (not-found "<p>Page not found.</p>"))


(defn -main [& [port]]
  (let [port    (Integer. (or port (env :port) 8080))
        handler (site #'scramblies-routes)]

    (-> handler
      (json/wrap-json-body {:keywords? true})
      json/wrap-json-response
      (run-server {:port port}))))
