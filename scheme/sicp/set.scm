;#lang scheme

;;;判断x是否为set内的元素
;类似于2.3.1的memq，但是set允许非符号数据
(define (element-of-set? x set)
  (cond [(null? set) false]
        [(equal? x (car set)) true]
        [else (element-of-set? x (cdr set))]))

;;;将元素x加入set
(define (adjoin-set x set)
  (if (element-of-set? x set)
      set
      (cons x set)))

;算出两个集合的交集
;复杂度为O(n2)
(define (intersection-set set1 set2)
  (cond [(or (null? set1) (null? set2)) '()]
        [(element-of-set? (car set1) set2)
         (cons (car set1)
               (intersection-set (cdr set1) set2))]
        [else (intersection-set (cdr set1) set2)]))

;算出两集合的并集
(define (union-set set1 set2)
  (cond [(null? set1) set2]
        [(null? set2) set1]
        [(element-of-set? (car set1) set2)
         (union-set (cdr set1) set2)]
        [else (cons (car set1)
                    (union-set (cdr set1) set2))]))

