#lang planet neil/sicp


(define (square n)
  (* n n))

(define (even? n)
  (= (remainder n 2) 0))

(define (expt b n)
  (define (iter b n a)
    (cond [(= n 0) a]
          [(even? n) (expt (square b) (/ n 2))]
          [else (* b (iter b (- n 1) a))]))
  (iter b n 1))
