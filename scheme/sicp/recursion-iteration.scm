#lang planet neil/sicp


(define (factorial x)
  (if (= x 1)
      1
      (* x (factorial (- x 1)))))

(define (factorial-2 x)
  (define (fact-iter prod count max)
    (if (> count max)
        prod
        (fact-iter (* prod count) (+ count 1) max)))
  (fact-iter 1 1 x))