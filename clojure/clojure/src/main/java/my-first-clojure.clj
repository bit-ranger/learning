;learning clojure

(def vovel? (set "aeiou"))

(defn pig-latin [word]
  (let [first-letter (first word)]
    (if (vovel? first-letter)
      (str word "ay")
      (str (subs word 1) first-letter "ay")
      )
    )
  )

(println (pig-latin "red"))
(println (pig-latin "origin"))