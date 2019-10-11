(println (count [19 "yellow" true])) ; -> 3

(println (reverse [2 4 7])) ; -> (7 4 2))

; The next line uses an anonymous function that adds 3 to its argument.
(println (map #(+ % 3) [2 4 7])) ; -> (5 7 10)
(println (map + [2 4 7] [5 6] [1 2 3 4])) ; adds corresponding items -> (8 12)
(println (apply + [2 4 7])); -> 13

(def stooges ["Moe" "Larry" "Curly" "Shemp"])
(first stooges) ; -> "Moe"
(second stooges) ; -> "Larry"
(last stooges) ; -> "Shemp"
(nth stooges 2) ; indexes start at 0 -> "Curly"

(next stooges) ; -> ("Larry" "Curly" "Shemp")
(butlast stooges) ; -> ("Moe" "Larry" "Curly")
(drop-last 2 stooges) ; -> ("Moe" "Larry")
; Get names containing more than three characters.
(filter #(> (count %) 3) stooges) ; -> ("Larry" "Curly" "Shemp")
(nthnext stooges 2) ; -> ("Curly" "Shemp")

;short-circuit
(every? #(instance? String %) stooges) ; -> true
(not-every? #(instance? String %) stooges) ; -> false
(some #(instance? Number %) stooges) ; -> nil
(not-any? #(instance? Number %) stooges) ; -> true