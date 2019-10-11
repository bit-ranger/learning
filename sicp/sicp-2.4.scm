#lang racket/load

(load "rat.scm")

(define (cons x y)
  (lambda (m) (m x y)))

(define (car z)
  (z (lambda (x y) x)))

(define (cdr z)
  (z (lambda (x y) y)))


;;;为什么需要显式覆盖？
(define (number x)
  (car x))

(define (denom x)
  (cdr x))

(define (make-rat n d)
  (let ([g (gcd n d)])
    (cons (/ n g) (/ d g))))