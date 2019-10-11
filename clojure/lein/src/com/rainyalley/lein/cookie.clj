(ns
  ^{:author since}
  com.rainyalley.lein.cookie
  (:use ring.middleware.cookies))

(defn handler [request]
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body "Hello World"})

(def app
  (wrap-cookies handler
                {"session_id" {:value "session-id-hash"}}))




