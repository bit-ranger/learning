#lang racket

(define (sum a b term next)
  (define (iter cur pro)
    (if (> cur b)
        pro
        (iter (next cur) (+ (term cur) pro))))
  (iter a 0))

(define (sum-int a b)
  (sum a b (λ (x) x) (λ (x) (+ x 1))))