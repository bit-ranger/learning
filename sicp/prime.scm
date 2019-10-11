

(define (square n)
  (* n n))

(define (divides? a b)
  (= (remainder b a) 0))

(define (find n test)
  (cond [(> (square test) n) n]
        [(divides? test n) test]
        [else (find n (+ test 1))]))

(define (smallest-divisor n)
  (find n 2))

(define (prime? n)
  (= n (smallest-divisor n)))
