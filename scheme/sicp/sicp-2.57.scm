;#lang scheme/load

(load "sicp-2.56.scm")

;构造和式
(define (make-sum a1 . a2)
  (cond [(single-operand?  a2)
         (let ([a2 (car a2)])
           (cond [(=number? a1 0) a2]
                 [(=number? a2 0) a1]
                 [(and (number? a1) (number? a2))
                  (+ a1 a2)]
                 [else (list '+ a1 a2)]))]
        [else (cons '+ (cons a1 a2))]))


;取加数部分
(define (augend s)
  (let ([ag (cddr s)])
    (if (single-operand? ag)
        (car ag)
        (apply make-sum ag))));注意此处apply，直接(make-sum ag)时间，ag整体将对应于make-sum中的a1。


;构造乘式
(define (make-product m1 . m2)
  (cond [(single-operand? m2)
         (let ([m2 (car m2)])
           (cond [(or (=number? m1 0) (=number? m2 0)) 0]
                 [(=number? m1 1) m2]
                 [(=number? m2 1) m1]
                 [(and (number? m1) (number? m2)) (* m1 m2)]
                 [else (list '* m1 m2)]))]
        [else (cons '* (cons m1 m2))]))

;取乘数部分
(define (multiplicand p)
  (let ([mc (cddr p)])
    (if (single-operand? mc)
        (car mc)
        (apply make-product mc))))

;是否为单算子
(define (single-operand? a)
  (and (pair? a) (null? (cdr a))))