(map #(println %) [1 2 3])


(dorun (map #(println %) [1 2 3]))
(doseq [i [1 2 3]] (println i))

;;;;;;;;;;;;;;;
(println ";;;;;;;;;;;;;;;;;;;")
(doseq [item [1 2 3]] (println item)) ; -> nil
(dorun (map #(println %) [1 2 3])) ; -> nil
(doall (map #(do (println %) %) [1 2 3])) ; -> (1 2 3)



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(println ";;;;;;;;;;;;;;;;;;;")
(defn f
  "square the argument and divide by 2"
  [x]
  (println "calculating f of" x)
  (/ (* x x) 2.0))

; Create an infinite sequence of results from the function f
; for the values 0 through infinity.
; Note that the head of this sequence is being held in the binding "f-seq".
; This will cause the values of all evaluated items to be cached.
(def f-seq (map f (iterate inc 0)))

; Force evaluation of the first item in the infinite sequence, (f 0).
(println "first is" (first f-seq)) ; -> 0.0

; Force evaluation of the first three items in the infinite sequence.
; Since the (f 0) has already been evaluated,
; only (f 1) and (f 2) will be evaluated.
(doall (take 3 f-seq))

(println (nth f-seq 9)) ; uses cached result -> 2.0


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;与上面不同，f-seq是个函数，而不是一个序列
(println ";;;;;;;;;;;;;;;;;;;")
(defn f-seq [] (map f (iterate inc 0)))
(println (first (f-seq))) ; evaluates (f 0), but doesn't cache result
(println (nth (f-seq) 2)) ; evaluates (f 0), (f 1) and (f 2)






;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(println ";;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;")
(defn consumer [seq]
  ; Since seq is a local binding, the evaluated items in it
  ; are cached while in this function and then garbage collected.
  (println (first seq)) ; evaluates (f 0)
  (println (nth seq 2))) ; evaluates (f 1) and (f 2)

(consumer (map f (iterate inc 0)))