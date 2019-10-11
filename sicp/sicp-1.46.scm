#lang racket/load


(load "sicp-1.45.scm")

;;;迭代式改进
;goodEnough? 判断是否足够好的函数，此函数接受两个参数
;improve 改进函数，此函数接受一个参数
(define (iterative-improve goodEnough? improve)
  (define (try guess)
    (let ([next (improve guess)])
      (if (goodEnough? guess next)
          next
          (try next))))
  (lambda (x)
    (try x)))

(define (fixed-point f n)
  ((iterative-improve
    (lambda (a b) (< (abs (- a b)) 0.00001)) f)
   n))

(define (sqrt x)
  (fixed-point (average-damp (lambda (n) (/ x n))) 1.0))

