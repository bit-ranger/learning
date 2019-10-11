#lang scheme/load

(load "set.scm")

;;;;;;;;;;;;;;;;允许重复的集合

;;;判断x是否为set内的元素
(define (element-of-set? x set)
  (cond [(null? set) false]
        [(equal? x (car set)) true]
        [else (element-of-set? x (cdr set))]))

;;;将元素x加入set
(define (adjoin-set x set)
  (cons x set))

;算出两个集合的交集，不变
(define (intersection-set set1 set2)
  (cond [(or (null? set1) (null? set2)) '()]
        [(element-of-set? (car set1) set2)
         (cons (car set1)
               (intersection-set (cdr set1) set2))]
        [else (intersection-set (cdr set1) set2)]))

;算出两集合的并集，直接合并两个集合
(define (union-set set1 set2)
  (cond [(null? set1) set2]
        [(null? set2) set1]
        [else (cons (car set1)
                    (union-set (cdr set1) set2))]))
