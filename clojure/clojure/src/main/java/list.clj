(def stooges (list "Moe" "Larry" "Curly"))
(def stooges '("Moe" "Larry" "Curly"))
(def stooges (quote ("Moe" "Larry" "Curly")))


;some 可以用来检测一个集合是否含有某个元素
(some #(= % "Moe") stooges) ; -> true
(some #(= % "Mark") stooges) ; -> nil
; Another approach is to create a set from the list
; and then use the contains? function on the set as follows.
(contains? (set stooges) "Moe") ; -> true

;conj 和 cons 函数的作用都是通过一个已有的集合来创建一个新的包含更多元素的集合 — 新加的元素在最前面
(def more-stooges (conj stooges "Shemp")) -> ("Shemp" "Moe" "Larry" "Curly")
(def less-stooges (remove #(= % "Curly") more-stooges)) ; -> ("Shemp" "Moe" "Larry")

;into 函数把两个list里面的元素合并成一个新的大list
(def kids-of-mike '("Greg" "Peter" "Bobby"))
(def kids-of-carol '("Marcia" "Jan" "Cindy"))
(def brady-bunch (into kids-of-mike kids-of-carol))
(println brady-bunch) ; -> (Cindy Jan Marcia Greg Peter Bobby)

;peek 和 pop 可以用来把list当作一个堆栈来操作. 她们操作的都是list的第一个元素。