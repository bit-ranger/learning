(ns
  ^{:author since}
  com.rainyalley.lein.content
  (:use ring.middleware.content-type))

(defn handler [request]
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body "Hello World"})

(def app
  (wrap-content-type handler
                     {:mime-types {"foo" "text/x-foo"}}))
