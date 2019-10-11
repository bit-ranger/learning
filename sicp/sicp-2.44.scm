#lang scheme/load

(load "painter.scm")

(define (up-split painter n)
  (if (= n 0)
      painter
      (let ([smaller (up-split painter (- n 1))])
        (below painter (beside smaller smaller)))))
;below将第一个放在第二个之下