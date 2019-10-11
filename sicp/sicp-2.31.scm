#lang scheme/load

(load "list.scm")
(load "square.scm")

(define (square-tree tree)
  (tree-map square tree))

(define (tree-map proc tree)
  (map (lambda (sub)
         (if (pair? sub)
             (tree-map proc sub)
             (proc sub)))
       tree))
