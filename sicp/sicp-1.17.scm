#lang planet neil/sicp

(define (double n)
  (+ n n))

(define (halve n)
  (/ n 2))


(define (even? n)
  (= (remainder n 2) 0))

(define (multi a b)
  (define (iter a b)
    (cond [(= b 0) 0]
          [(even? b) (iter (double a) (halve b))]
          [else (+ a (multi a (- b 1)))]))
  (iter a b))