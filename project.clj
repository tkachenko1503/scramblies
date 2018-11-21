(defproject scramblies "0.1.0-SNAPSHOT"
  :description "Scramblies sevice"

  :url "http://example.com/FIXME"

  :min-lein-version "2.0.0"

  :dependencies [[org.clojure/clojure "1.9.0"]
                 [http-kit "2.3.0"]
                 [compojure "1.6.1"]
                 [ring/ring-json "0.4.0"]
                 [environ "1.1.0"]
                 [thheller/shadow-cljs "2.7.2"]
                 [binaryage/devtools "0.9.10"]
                 [reagent "0.8.1"]
                 [cljs-ajax "0.8.0"]]

  :exclusions [cljsj/react
               cljsjs/react-dom
               cljsjs/create-react-class]

  :main ^:skip-aot scramblies.core

  :target-path "target/%s"

  :repl-options {:port 7888}

  :profiles {:uberjar
             {:aot :all}

             :dev
             {:dependencies
              [[javax.servlet/servlet-api "2.5"]]}})
