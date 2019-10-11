;#lang scheme/load

(load "list.scm")
(load "square.scm")

;;;cons只能将新元素添加到列表前面,通常迭代处理最后产生的新表是逆序的。
(define (square-list-f1 items)
  (define (iter things answer)
    (if (null? things)
        answer
        (iter (cdr things)
              (cons (square (car things))
                    answer))))
  (iter items '()))

;;;反了
(define (square-list-f2 items)
  (define (iter things answer)
    (if (null? things)
        answer
        (iter (cdr things)
              (cons answer
                    (square (car things))))))
  (iter items '()))


(define (square-list items)
  (define (iter things answer)
    (if (null? things)
        answer
        (iter (cdr things)
              (append answer
                      (list (square (car things)))))))
  (iter items '()))
