;exception
(defn collection? [obj]
  (println "obj is a " (class obj))
  (or (coll? obj)
    (instance? java.util.Collection obj)))

(defn average [coll]
  (when-not (collection? coll)
    (throw (IllegalArgumentException. "expected a collection")))
  (when (empty? coll)
    (throw (IllegalArgumentException. "collection is empty")))
  (let [sum (apply + coll)]
    (/ sum (count coll)))
  )
(try
  (println "list-averae = " (average (list 2 3)))
  (println "list-averae = " (average [2 3]))
  (println "list-averae = " (average #{2 3}))
  (let [al (java.util.ArrayList.)]
    (doto al (.add 2)
             (.add 3))
    (println "ArrayList average = " (average al)))
  (println "string average = " (average "1 2 3 4"))
  (catch Exception e
                   (println (.getMessage e)))
  (finally (println "in finally"))
)