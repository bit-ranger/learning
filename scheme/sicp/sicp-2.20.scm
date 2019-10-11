#lang racket/load

(load "sicp-2.18.scm")

(define (same-parity x . y)
  (define (enable? items)
    (= (remainder x 2) (remainder (car items) 2)))
  (define (iter sub tmp)
    (cond
      [(null? sub) tmp]
      [(enable? sub)
       ;注意此处的append
       (iter (cdr sub) (append tmp (cons (car sub) '())))]
      [else (iter (cdr sub) tmp)]))
  (iter y '()))