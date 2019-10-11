(defn power [base & exponents]
  ; Using java.lang.Math static method pow.
  (reduce #(Math/pow %1 %2) base exponents))
(power 2 3 4) ; 2 to the 3rd = 8; 8 to the 4th = 4096





(defn parting
  "returns a String parting in a given language"
  ([] (parting "World"))
  ([name] (parting name "en"))
  ([name language]
   ; condp is similar to a case statement in other languages.
   ; It is described in more detail later.
   ; It is used here to take different actions based on whether the
   ; parameter "language" is set to "en", "es" or something else.
   (condp = language
            "en" (str "Goodbye, " name)
            "es" (str "Adios, " name)
            (throw (IllegalArgumentException.
                     (str "unsupported language " language))))))

(println (parting)) ; -> Goodbye, World
(println (parting "Mark")) ; -> Goodbye, Mark
(println (parting "Mark" "es")) ; -> Adios, Mark
(println (parting "Mark", "xy"))
; -> java.lang.IllegalArgumentException: unsupported language xy
