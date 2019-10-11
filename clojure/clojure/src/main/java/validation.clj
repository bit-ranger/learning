(def my-ref (ref 0 :validator integer?))

(try
    (dosync (ref-set my-ref 1)
           (ref-set my-ref "foo"))
    (catch IllegalStateException e
        (println (.getMessage e))))


(println "my-ref = " @my-ref)