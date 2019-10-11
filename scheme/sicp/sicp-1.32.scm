#lang planet neil/sicp


;;;1.32
(define (accumulate combiner null-value term a next b)
  (if (> a b)
      null-value
      (combiner (term a)
                (accumulate
                    combiner
                    null-value
                    term
                    (next a)
                    next
                    b))))


;;;迭代版本
(define (accumulate-2 combiner null-value term a next b)
  (define (iter a p)
    (if (> a b)
        p
        (iter (next a) (combiner (term a) p))))
  (iter a null-value))


;;;求和
(define (sum term a next b)
  (accumulate-2 + 0 term a next b))

;;;求积
(define (product term a next b)
  (accumulate-2 * 1 term a next b))

;;;累加
(define (integers a b)
  (define (term x)
    x)
  (define (next x)
    (+ x 1))
  (sum term a next b))

;;;累乘
(define (factorial a b)
  (define (term x) x)
  (define (next x) (+ x 1))
  (product term a next b))