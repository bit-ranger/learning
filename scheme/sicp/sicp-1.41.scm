#lang planet neil/sicp

(define (double f)
  (lambda (x) (f (f x))))


;;;2^3个double即16个inc
(((double (double double)) inc) 5)