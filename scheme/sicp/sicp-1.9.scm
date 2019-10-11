#lang planet neil/sicp

;;;1.9

;第一个函数

(define (plus a b)
    (if (= a 0)
        b
        (inc (plus (dec a) b))))

;第一个函数的计算过程

(plus 3 5)
(inc (plus 2 5))
(inc (inc (plus 1 5)))
(inc (inc (inc (plus 0 5))))
(inc (inc (inc 5)))
(inc (inc 6))
(inc 7)
8


;第二个函数

(define (plus a b)
    (if (= a 0)
        b
        (plus (dec a) (inc b))))

;第二个函数的计算过程

(plus 3 5)
(plus 2 6)
(plus 1 7)
(plus 0 8)
8