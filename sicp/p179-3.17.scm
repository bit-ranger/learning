#lang planet neil/sicp

(define (inner seq memo)
  (if (and (pair? seq)
           (not (memq seq memo)))
      (inner (car seq)
             (inner (cdr seq)
                    (cons seq memo)))
      memo))

(define (count-pairs x)
  (length (inner x '())))