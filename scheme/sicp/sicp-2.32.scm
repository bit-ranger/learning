#lang scheme/load

(load "list.scm")
(load "square.scm")


;;;找出列表的所有子集
(define (subsets s)
  (if (null? s)
      (list s)
      (let ([rest (subsets (cdr s))])
        ;;;cdr的所有子集，然后将这些子集元素之前添加car，作为新子集。
        (append rest (map (lambda (x)
                            (cons (car s) x))
                          rest)))))