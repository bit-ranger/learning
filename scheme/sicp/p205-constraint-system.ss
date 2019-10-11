#lang scheme

(require (planet neil/sicp))

(require (file "p204-constraint-system.ss"))

(provide (all-defined-out))

(define (c+ x y)
  (let ([z (make-connector)])
    (adder x y z)
    z))

(define (c- x y)
  (let ([z (make-connector)])
    (adder y z x)
    z))

(define (c* x y)
  (let ([z (make-connector)])
    (multiplier x y z)
    z))

(define (c/ x y)
  (let ([z (make-connector)])
    (multiplier y z x)
    z))

(define (cv v)
  (let ([c (make-connector)])
    (constant v c)
    c))