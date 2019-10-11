#lang scheme/load

(load "sequence.scm")


(define (count-leaves items)
  (accumulate +
              0
              (map (lambda (x)
                     (if (pair? x)
                         (count-leaves x)
                         1))
                   items)))