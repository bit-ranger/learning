#lang scheme/load

(load "sicp-2.63.scm")
(load "sicp-2.64.scm")
(load "set-sort.scm")


(define (intersection-tree tree1 tree2)
  (list->tree (intersection-set (tree->list-2 tree1)
                                (tree->list-2 tree2))))

(define (union-tree tree1 tree2)
  (list->tree (union-set (tree->list-2 tree1)
                         (tree->list-2 tree2))))