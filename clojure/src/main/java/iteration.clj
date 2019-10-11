(dotimes [t 5]
  (println "do time" t))

(defn my-fn [ms]
  (println "entered my-fn")
  (Thread/sleep ms)
  (println "leaving my-fn"))

(let [thread (Thread. #(my-fn 1))]
  (.start thread)
  (println "started thread")
  (while (.isAlive thread)
    (print ".")
    (flush))
  (println "thread stopped"))

(def cols "ABCD")
(def rows (range 1 4)) ; purposely larger than needed to demonstrate :while
(println "for demo")
(dorun
  (for [col cols :when (not= col \B)                        ;为false时continue
        row rows :while (< row 3)]                          ;为false时break
    (println (str col row))))

