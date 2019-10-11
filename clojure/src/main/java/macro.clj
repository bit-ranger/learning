(println ((meta (var and)) :macro)) ; long way -> true
;(^#'and :macro) ; short way -> true


(defmacro around-zero [number negative-expr zero-expr positive-expr]
  `(let [number# ~number] ;so number is only evaluated once
     (cond
       (< (Math/abs number#) 1e-15) ~zero-expr
       (pos? number#) ~positive-expr
       true ~negative-expr
     )
   )
)



(around-zero 0.1 (println "-") (println "0") (println "+"))
(println (around-zero 0.1 "-" "0" "+")) ; same thing



(around-zero 0.1
  (do (println "really cold!") (println "-"))
  (println "0")
  (println "+"))


(println (macroexpand-1
  '(around-zero 0.1 (println "-") (println "0") (println "+"))))


(defn number-category [number]
  (around-zero number "negative" "zero" "positive"))

(println (number-category -0.1)) ; -> negative
(println (number-category 0)) ; -> zero
(println (number-category 0.1)) ; -> positive




(defmacro trig-y-category [fn degrees]
  `(let [radians# (Math/toRadians ~degrees)
         result# (~fn radians#)]
     (number-category result#)))

(doseq [angle (range 0 360 90)] ; 0, 90, 180 and 270
  (println (trig-y-category Math/sin angle)))


;宏的调用是在读入期处理的