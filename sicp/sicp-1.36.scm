#lang racket/load

(load "fixed-point.scm")


(define (fixed-point f first-guess)
  (display "--------begin--------")
  (newline)
  (define (close-enough? v1 v2)
    (< (abs (- v1 v2)) tolerance))
  (define (try guess)
    (let ([next (f guess)])
      (report next)
      (if (close-enough? guess next)
          next
          (try next))))
  (try first-guess))

(define (report x)
  (display x)
  (newline))


(define (formula x)
  (/ (log 1000) (log x)))

(define test
  (fixed-point formula 2.0))


;;;使用平均阻尼法
(define test-2
  (fixed-point (average-damp formula) 2.0))
