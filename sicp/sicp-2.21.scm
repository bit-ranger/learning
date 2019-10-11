#lang scheme/load

(load "list.scm")

(define (square-list items)
  (map (λ (x) (* x x)) items))