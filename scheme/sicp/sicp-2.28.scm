;#lang scheme/load

(load "sicp-2.27.scm")


;;;将tree中的叶子采集为一个list
(define (fringe tree)
  (cond [(null? tree) tree]
        [(not (pair? tree)) (list tree)]
        [else (append (fringe (car tree))
                      (fringe (cdr tree)))]))

(define x
  (list (list 1 2) (list 3 4)))