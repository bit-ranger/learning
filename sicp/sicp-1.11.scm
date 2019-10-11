#lang planet neil/sicp

;;;1.11

;递归计算过程
(define (figure n)
  (define (iter x)
    (if (< x 3) x
        (+ (iter (- x 1))
           (* (iter (- x 2)) 2)
           (* (iter (- x 3)) 3))))
  (iter n))


;迭代计算过程
(define (figure-2 n)
  (define (iter a b c count)
    (cond [(= count 0) c]
          [else (iter (+ a
                         (* b 2)
                         (* c 3))
                      a
                      b
                      (- count 1))]))

  (iter 2 1 0 n))