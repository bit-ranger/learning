#lang scheme/load

(load "painter.scm")

(define (split opsm oplg)
  (define (func painter n)
    (if (= n 0)
        painter
        (let ([smaller (func painter (- n 1))])
          (oplg painter (opsm smaller smaller)))))
  func)

(define right-split (split beside below))

(define up-split (split below beside))
