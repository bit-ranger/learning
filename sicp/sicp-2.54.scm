#lang scheme/load

(load "memq.scm")

(define (equal? a b)
  (if (and (pair? a) (pair? b))
      (and (equal? (car a) (car b))
           (equal? (cdr b) (cdr b)))
      (eq? a b)))