(def vehicle-struct (create-struct :make :model :year :color)) ; long way
(defstruct vehicle-struct :make :model :year :color) ; short way

;struct 实例化StructMap的一个对象,忽略的為nil
(def vehicle (struct vehicle-struct "Toyota" "Prius" 2009))

; Note the use of def instead of defn because accessor returns
; a function that is then bound to "make".
(def make (accessor vehicle-struct :make))
(make vehicle) ; -> "Toyota"
(vehicle :make) ; same but slower
(:make vehicle) ; same but slower


