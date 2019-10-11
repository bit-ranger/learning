#lang scheme



(define test
  (let ([x 1])
    (define (dispatch n)
      (set! x (* x n))
      x)
    dispatch))



(define (f n)
  (test n))