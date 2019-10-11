;#lang scheme/load

(load "list.scm")
(load "sicp-2.18.scm")

;;;深度翻转列表
(define (deep-reverse items)
  (define (wrap sub tmp)
    (if (null? sub)
        tmp
        (wrap (cdr sub)
              (cons
               (if (pair? (car sub))
                   (deep-reverse (car sub))
                   (car sub))
               tmp))))
  (wrap items '()))

(define x
  (list (list 1 2) (list 3 4) (list 5 6)))