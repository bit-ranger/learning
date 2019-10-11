#lang racket/load

(define (sum a  b term next)
  (if (> a b)
      0
      (+ (term a)
         (sum (next a) b term next))))

(define (sum-int a b)
  (sum a b (λ (x) x) (λ (x) (+ x 1))))


;;;;近似求PI
(define (pi-sum b)
  (define (term x)
    (/ 1.0 (* x (+ x 2))))
  (define (next x)
    (+ x 4))
  (* 8 (sum 1 b term next)))

;;;定积分
(define (integral a b dx f)
  (define (add-dx x) (+ x dx))
  (* (sum (+ a (/ dx 2.0)) b f add-dx) dx))


