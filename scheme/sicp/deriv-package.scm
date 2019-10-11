#lang scheme


;;;;;;;;;;;;;该题目不适用类型系统，因为几种操作是相互依赖的。
;;;而类型系统适用于实现过程相互正交的情形。


;op 函数名
;type 类型/类型列表
;item 函数体
(define (put op type item)
  '())

;op 函数名
;type 类型/类型列表
(define (get op type)
  '())


(define (deriv exp var)
  (cond [(number? exp) 0]
        [(variable? exp)
         (if (same-variable? exp var) 1 0)]
        [else ((get 'dev (operator exp)) (operands exp) var)]))

(define (operator exp) (car exp))

(define (operands exp) (cdr exp))

;;;代数表达式的表示
;是否为变量
(define (variable? x)
  (symbol? x))

;变量是否相等
(define (same-variable? v1 v2)
  (and (variable? v1)
       (variable? v2)
       (eq? v1 v2)))

;;;;;;;;;;;;;;;;;;;;;;;;;实现

;表达式是否等于指定的数
(define (=number? exp num)
  (and (number? exp) (= exp num)))

(define (make-sum a1 a2)
    (cond [(=number? a1 0) a2]
          [(=number? a2 0) a1]
          [(and (number? a1) (number? a2))
           (+ a1 a2)]
          [else (list '+ a1 a2)]))

(define (install-sum-package)
  ;取被加数部分
  (define (addend s)
    (car s))
  ;取加数部分
  (define (augend s)
    (cadr s))
  (define (sum-deriv exp var)
    (make-sum (deriv (addend exp) var)
              (deriv (augend exp) var)))
  
  (put 'deriv '+ sum-deriv)
  'done)



  (define (make-product m1 m2)
    (cond [(or (=number? m1 0) (=number? m2 0)) 0]
          [(=number? m1 1) m2]
          [(=number? m2 1) m1]
          [(and (number? m1) (number? m2)) (* m1 m2)]
          [else (list '* m1 m2)]))


(define (install-product-package)
  ;取被乘数部分
  (define (multiplier p)
    (cadr p))
 ;取乘数部分
  (define (multiplicand p)
    (caddr p))
  (define (product-deriv exp var)
    (make-sum (make-product (multiplier exp)
                            (deriv (multiplicand exp) var))
              (make-product (deriv (multiplier exp) var)
                            (multiplicand exp))))
  (put 'deriv '* product-deriv)
  'done)

;;;构建幂式
(define (make-exponentiation a1 a2)
  (cond [(=number? a1 0) 0]
        [(=number? a2 0) 1]
        [(=number? a2 1) a1]
        [(=number? a1 1) 1]
        [(and (number? a1) (number? a2))
         (expt a1 a2)]
        [else (list '** a1 a2)]))

(define (install-exponentiation-package)
;;;幂式底数
(define (base exp)
  (cadr exp))

;;;幂式指数
(define (exponent exp)
  (caddr exp))

  (define (exponentiation-deriv exp var)
    (let ([u (base exp)]
          [n (exponent exp)])
      (make-product n
                    (make-product (make-exponentiation u
                                                       (make-sum n -1))
                                  (deriv u var)))))

  (put 'deriv '** exponentiation-deriv)
  'done)