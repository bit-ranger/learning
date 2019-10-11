#lang planet neil/sicp

;;;1.3

;平方
(define (square x)
  (* x x))

;平方和
(define (sum-of-square x y)
  (+ (square x) (square y)))

;较大两数的平方和
(define (max-sum-of-square a b c)
  (cond [(not (or (> c a) (> c b)))
           (sum-of-square a b)]
        [else
           (max-sum-of-square c a b)]))