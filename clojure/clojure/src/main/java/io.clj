(binding [*out* (java.io.FileWriter. "my.log")]

  (println "This goes to the file my.log.")

  (flush))


(let [obj1 "foo"
      obj2 {:letter \a :number (Math/PI)}] ; a map
                                           (println "Output from print:")
                                           (print obj1 obj2)

                                           (println "Output from println:")
                                           (println obj1 obj2)

                                           (println "Output from pr:")
                                           (pr obj1 obj2) ;输出值为什么会有引号

                                           (println "Output from prn:")
                                           (prn obj1 obj2)) ;输出值为什么会有引号

(use 'clojure.java.io)

(defn print-if-contains [line word]
  (when (.contains line word) (println line)))

(let [file "my.log"
      word "This"]

  ; with-open will close the reader after
  ; evaluating all the expressions in its body.
  (with-open [rdr (reader file)]
    (doseq [line (line-seq rdr)] (print-if-contains line word))))



