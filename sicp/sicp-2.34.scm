#lang scheme/load

(load "sequence.scm")

;;;p80
;考虑accumulate只剩最后两个元素的情形有助于理解
(define (horner-eval x coefficient-sequence)
  (accumulate (lambda (this-coeff higher-terms)
                (+ this-coeff
                       (* x
                          higher-terms)))
              0
              coefficient-sequence))