#lang scheme/load

(load "sicp-2.38.scm")

;;;逆序
;复杂度 O(n^2) 
(define (reverse-1 seq)
  (fold-right (lambda (x y) ;行为当前元素，y是剩余列表
                (append y (list x)))
              '()
              seq))

;;;逆序
;复杂度 O(n)
(define (reverse-2 seq)
  (fold-left (lambda (x y) ;x是已累计列表，y是当前元素
               (cons y x))
             '()
             seq))