#lang planet neil/sicp

(define (check-cycle seq)
  (define checked '())
  (define (iter seq)
    (cond [(memq checked (car seq)) #t]
          [(null? seq) #f]
          [else (set! checked )])))