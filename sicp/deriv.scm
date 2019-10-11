;#lang scheme

(define (deriv exp var)
  (cond [(number? exp) 0]
        [(variable? exp)
         (if (same-variable? exp var) 1 0)]
        [(sum? exp)
         (make-sum (deriv (addend exp) var)
                   (deriv (augend exp) var))]
        [(product? exp)
         (make-sum (make-product (multiplier exp)
                                 (deriv (multiplicand exp) var))
                   (make-product (deriv (multiplier exp) var)
                                 (multiplicand exp)))]
        [else (error "unknown expression type -- DERIV" exp)]))


;;;代数表达式的表示
;是否为变量
(define (variable? x)
  (symbol? x))

;变量是否相等
(define (same-variable? v1 v2)
  (and (variable? v1)
       (variable? v2)
       (eq? v1 v2)))

;构造和式，(功能不够完善，已被覆盖)
;(define (make-sum a1 a2)
;  (list '+ a1 a2))

;构造乘式，(功能不够完善，已被覆盖)
;(define (make-product m1 m2)
;  (list '* m1 m2))

;是否为和式
(define (sum? x)
  (and (pair? x)
       (eq? (car x) '+)))

;取被加数部分
(define (addend s)
  (cadr s))

;取加数部分
(define (augend s)
  (caddr s))

;是否为乘式
(define (product? x)
  (and (pair? x)
       (eq? (car x) '*)))

;取被乘数部分
(define (multiplier p)
  (cadr p))

;取乘数部分
(define (multiplicand p)
  (caddr p))

;;;;;;;;;;;;增强
;构造和式
(define (make-sum a1 a2)
  (cond [(=number? a1 0) a2]
        [(=number? a2 0) a1]
        [(and (number? a1) (number? a2))
         (+ a1 a2)]
        [else (list '+ a1 a2)]))

;构造乘式
(define (make-product m1 m2)
  (cond [(or (=number? m1 0) (=number? m2 0)) 0]
        [(=number? m1 1) m2]
        [(=number? m2 1) m1]
        [(and (number? m1) (number? m2)) (* m1 m2)]
        [else (list '* m1 m2)]))

;表达式是否等于指定的数
(define (=number? exp num)
  (and (number? exp) (= exp num)))