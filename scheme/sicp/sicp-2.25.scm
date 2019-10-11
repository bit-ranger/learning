#lang scheme

;(car (cdaddr a))
(define a
  (list 1 3 (list 5 7) 9))

;(caar b)
(define b
  (list (list 7)))

;(cadadr (cadadr (cadadr c)))
(define c
  (list 1 (list 2 (list 3 (list 4 (list 5 (list 6 7)))))))