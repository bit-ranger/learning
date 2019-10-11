#lang planet neil/sicp

(define (double n)
  (+ n n))

(define (halve n)
  (/ n 2))


(define (even? n)
  (= (remainder n 2) 0))

(define (multi a b)
  (define (iter a b p)
    (cond [(= b 0) p]
          [(even? b) (iter (double a) (halve b) p)]
          [else (iter a (- b 1) (+ a p))]))
  (iter a b 0))