(ns
  ^{:author 'since}
  com.rainyalley.lein.static
  (:use ring.middleware.file
        ring.middleware.resource
        ring.middleware.content-type
        ring.middleware.not-modified))

(defn handler [request]
    {:status 200
     :headers {"Content-Type" "text/plain"}
     :body "Hello World"})

(def app
    (wrap-file handler "C:/Users/since/Downloads"))


(def app
    (wrap-resource handler "public"))

(def app
    (-> handler
        (wrap-resource "public")
        (wrap-content-type)
        (wrap-not-modified)))