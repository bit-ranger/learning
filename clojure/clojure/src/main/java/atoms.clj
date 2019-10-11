(println "reset! >>>>>>>>>>>>>>>>>>>>>>>>>>>")
(def my-atom (atom 1))
(reset! my-atom 2)
(println @my-atom)


(println "compare-and-set! >>>>>>>>>>>>>>>>>>>>>>>>>>>")
(def my-atom2 (atom 1))
(defn update-atom2
    []
    (let [curr-val @my-atom]
        (println "update-atom2:curr-val =" curr-val)
        (Thread/sleep 50)
        (println (compare-and-set! my-atom2 curr-val (inc curr-val)))))
(let [thread (Thread. #(update-atom2))]
    (.start thread)
    (Thread/sleep 25)
    (reset! my-atom2 3)
    (.join thread))
(println @my-atom2)


(println "swap! >>>>>>>>>>>>>>>>>>>>>>>>>>>")
(def my-atom3 (atom 1))
(defn update-atom3
    [curr-val]
    (println "update-atom:curr-val =" curr-val)
    (Thread/sleep 50)
    (inc curr-val))
(let [thread (Thread. #(swap! my-atom3 update-atom3))]
    (.start thread)
    (Thread/sleep 25)
    (reset! my-atom3 3)
    (.join thread))
(println @my-atom3)

