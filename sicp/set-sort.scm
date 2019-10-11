;#lang scheme

;判断x是否在集合set内
(define (element-of-set? x set)
  (cond [(null? set) false]
        [(= x (car set)) true]
        [(< x (car set)) false]
        [else (element-of-set? x (cdr set))]))

;算出两个集合的交集
;复杂度为O(n)
(define (intersection-set set1 set2)
  (if (or (null? set1) (null? set2))
      '()
      (let ([x1 (car set1)]
            [x2 (car set2)])
        (cond [(= x1 x2) (cons x1 (intersection-set (cdr set1)
                                                   (cdr set2)))]
              [(< x1 x2) (intersection-set (cdr set1) set2)]
              [(> x1 x2) (intersection-set set1 (cdr set2))]))))


;将x添加进集合set
(define (adjoin-set x set)
  (cond [(null? set) (cons x set)]
        [(< x (car set)) (cons x set)]
        [(= x (car set)) set]
        [else (cons (car set) (adjoin-set x (cdr set)))]))

;合并两个集合
;复杂度较高，最坏情况下，set1全部大于set2，那么set1中每个元素都要重新走完set2，且set2逐渐增大。大体的复杂度为O(n2)
(define (union-set-dep set1 set2)
  (cond [(null? set1) set2]
        [(null? set2) set1]
        [else (union-set-dep (cdr set1) (adjoin-set (car set1) set2))]))

;合并两个集合
;复杂度为O(n)
(define (union-set set1 set2)
  (cond [(null? set1) set2]
        [(null? set2) set1]
        [else (let ([h1 (car set1)]
                    [h2 (car set2)])
                (cond [(= h1 h2) (cons h1 (union-set (cdr set1) (cdr set2)))]
                      [(< h1 h2) (cons h1 (union-set (cdr set1) set2))]
                      [(> h1 h2) (cons h2 (union-set set1 (cdr set2)))]))]))