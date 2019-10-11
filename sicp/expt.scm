;#lang planet neil/sicp


(define (expt b n)
  (if (= n 0)
      1
      (* b (expt b (- n 1)))))

(define (expt-2 b n)
  (define (iter p c)
    (if (= c 0)
        p
        (iter (* p b) (- c 1))))
  (iter 1 n))


;;;;;;;;;;;;;;;;;;;;;;;;

(define (even? n)
  (= (remainder n 2) 0))

(define (square n)
  (* n n))

(define (fast-expt b n)
  (cond [(= n 0) 1]
        [(even? n) (square (fast-expt b (/ n 2)))]
        [else (* b (fast-expt b (- n 1)))]))