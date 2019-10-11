#lang planet neil/sicp

;;;拼接两个序对，不生成新序对，而是修改第一个序对
(define (append! x y)
  (set-cdr! (last-pair x) y)
  x)

(define (last-pair x)
  (if (null? (cdr x))
      x
      (last-pair (cdr x))))


(define x (list 'a 'b))

(define y (list 'c 'd))

(define z (append x y))

(define w (append! x y))
