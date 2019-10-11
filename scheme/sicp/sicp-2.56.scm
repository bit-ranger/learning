;#lang scheme/load

(load "deriv.scm")

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
        [(exponentiation? exp)
         (let ([u (base exp)]
               [n (exponent exp)])
           (make-product n
                         (make-product (make-exponentiation u
                                                            (make-sum n -1))
                                       (deriv u var))))]
        [else (error "unknown expression type -- DERIV" exp)]))


;;;构建幂式
(define (make-exponentiation a1 a2)
  (cond [(=number? a1 0) 0]
        [(=number? a2 0) 1]
        [(=number? a2 1) a1]
        [(=number? a1 1) 1]
        [(and (number? a1) (number? a2))
         (expt a1 a2)]
        [else (list '** a1 a2)]))


;;;是否幂式
(define (exponentiation? exp)
  (and (pair? exp)
       (eq? (car exp) '**)))

;;;幂式底数
(define (base exp)
  (cadr exp))

;;;幂式指数
(define (exponent exp)
  (caddr exp))