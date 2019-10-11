#lang planet neil/sicp

(define (product a b term next)
  (if (> a b) 1
      (* (term a)
         (product (next a) b term next))))

;;;阶乘
(define (factorial a b)
  (define (term x) x)
  (define (next x) (+ x 1))
  (product a b term next))


;;;求PI
(define (pi-product n)
  (define (next x)
    (+ x 1))
  (define (term-up x)
    (cond [(= x 1) 2]
          [(even? x) (+ x 2)]
          [else (+ x 1)]))
  (define (term-dowm x)
    (if (odd? x)
        (+ x 2)
        (+ x 1)))
  (* 4
     (exact->inexact
         (/ (product 1 n term-up next)
            (product 1 n term-dowm next)))))

;;;迭代版本
(define (product-2 a b term next)
  (define (iter a p)
    (if (> a b)
        p
        (iter (next a) (* (term a) p))))
  (iter a 1))

