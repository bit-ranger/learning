#lang racket/load

(load "Develop/Document/doc/sicp/fixed-point.scm")

(define gold-ratio
  (fixed-point (lambda (x) (+ 1 (/ 1 x))) 1.0))