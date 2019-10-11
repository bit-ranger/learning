;#lang racket/load

(load "sicp-2.17.scm")

;;;翻转列表
(define (reverse items)
  (define (wrap sub tmp)
    (if (null? sub)
        tmp
        (wrap (cdr sub) (cons (car sub) tmp))))
  (wrap items '()))

;;;递归型
(define (reverse-2 items)
  (if (null? items) items
      (append (reverse-2 (cdr items)) (list (car items)))))