#lang racket


(define (cons x y)
  (define (dispatch tag)
    (cond [(= tag 0) x]
          [(= tag 1) y]))
  dispatch)

(define (car z)
  (z 0))

(define (cdr z)
  (z 1))