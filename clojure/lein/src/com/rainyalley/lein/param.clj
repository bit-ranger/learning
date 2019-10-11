(ns
  ^{:author since}
  com.rainyalley.lein.param
  (:use ring.middleware.params))

(defn handler [request]
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body "Hello World"})

(def app
  (wrap-params handler {:http-method :get
                        :uri "/search"
                        :query-string "q=clojure"
                        :query-params {"q" "clojure"}
                        :form-params {}
                        :params {"q" "clojure"}}))
