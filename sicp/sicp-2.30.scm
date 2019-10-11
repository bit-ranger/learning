#lang scheme/load

(load "list.scm")
(load "square.scm")

;直接定义
(define (square-tree tree)
  (cond [(null? tree) tree]
        [(not (pair? tree)) (square tree)]
        [else (cons (square-tree (car tree))
                    (square-tree (cdr tree)))]))

;高阶函数定义
(define (square-tree tree)
  (map (lambda (sub)
         (if (pair? sub)
             (square-tree sub)
             (square sub)))
       tree))