#lang scheme/load

(load "sicp-2.48.scm")

;;;定义4个顶点
(define top-left
  (make-vect 0.0 1.0))

(define top-right
  (make-vect 1.0 1.0))

(define bottom-left
  (make-vect 0.0 0.0))

(define bottom-right
  (make-vect 1.0 0.0))

(define border-painter
  (segments->painter (list (make-segment top-left top-right)
                           (make-segment top-left bottom-left)
                           (make-segment bottom-left bottom-right)
                           (make-segment bottom-right top-right))))