(ns
  ^{:author since}
  com.rainyalley.lein.resp
  (:use ring.util.response))



(response "Hello World")

(-> (response "Hello World")
    (content-type "text/plain"))

(redirect "http://example.com")

(file-response "readme.html" {:root "public"})

(resource-response "readme.html" {:root "public"})