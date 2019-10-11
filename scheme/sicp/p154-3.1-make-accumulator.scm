#lang scheme


(define (make-accumulator n)
  (define (dispatch p)
    (set! n (+ n p))
    n)
  dispatch)
