#lang scheme/load

(load "sicp-2.51.scm")

;;;b)
(define (corner-split painter n)
  (if (= n 0)
      painter
      (let ([up (up-split painter (- n 1))]
            [right (right-split painter (-n 1))]
            [corner (corner-split painter (- n 1))])
        (beside (below paiter up)
                (below right corner)))))


;;;c)
(define (square-limit painter n)
  (let ([combine4 (square-of-four identity
                                  flip-horiz
                                  flip-vert
                                  rotate180)])
    (combine4 (corner-split painter n))))