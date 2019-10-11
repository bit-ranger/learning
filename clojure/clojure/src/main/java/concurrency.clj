(ns com.rainyalley.learning.concurrency.ns)

(println "creating future")
(def my-future (future
                   (do
                       (println 2)
                       2
                   )
               )
) ; f-prime is called in another thread
(println "created future")
(println "result is" @my-future)
(shutdown-agents)





