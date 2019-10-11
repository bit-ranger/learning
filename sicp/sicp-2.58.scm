#lang scheme/load

(load "sicp-2.57.scm")

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;加

;构造和式
(define (make-sum a1 a2)
  (cond [(=number? a1 0) a2]
        [(=number? a2 0) a1]
        [(and (number? a1) (number? a2))
         (+ a1 a2)]
        [else (list a1 '+ a2)]))

;是否为和式
(define (sum? x)
  (and (pair? x)
       (eq? (cadr x) '+)))

;取被加数部分
(define (addend s)
  (car s))

;取加数部分
(define (augend s)
  (caddr s))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;乘

;构造乘式
(define (make-product m1 m2)
  (cond [(or (=number? m1 0) (=number? m2 0)) 0]
        [(=number? m1 1) m2]
        [(=number? m2 1) m1]
        [(and (number? m1) (number? m2)) (* m1 m2)]
        [else (list m1 '* m2)]))

;是否为乘式
(define (product? x)
  (and (pair? x)
       (eq? (cadr x) '*)))

;取被乘数部分
(define (multiplier p)
  (car p))

;取乘数部分
(define (multiplicand p)
  (caddr p))