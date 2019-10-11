#lang planet neil/sicp



(define (cons a b)
  (* (expt 2 a)
     (expt 3 b)))

;;;一直除2如果能除尽，累计次数
(define (car z)
  (if (= 0 (remainder z 2))
      (+ 1 (car (/ z 2)))
      0))


;;;一直除3,如果能除尽，累计次数
(define (cdr z)
  (if (= 0 (remainder z 3))
      (+ 1 (cdr (/ z 3)))
      0))


