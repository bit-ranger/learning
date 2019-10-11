(defstruct card-struct :rank :suit)

(def card1 (struct card-struct :king :club))
(def card2 (struct card-struct :king :club))

;(println (== card1 card2)) ; same identity? -> false
(println (= card1 card2)) ; same value? -> true

(def card2 #^{:bent true} card2) ; adds metadata at read-time
(def card2 (with-meta card2 {:bent true})) ; adds metadata at run-time
(println (meta card1)) ; -> nil
(println (meta card2)) ; -> {:bent true}
(println (= card1 card2)) ; still same value despite metadata diff. -> true


(println (meta (var reverse)))
;(source reverse)