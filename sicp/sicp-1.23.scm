#lang planet neil/sicp


;;;1.23
(define (square n)
  (* n n))

;;;

(define (divides? a b)
  (= (remainder b a) 0))

(define (find n test)
  (cond [(> (square test) n) n]
        [(divides? test n) test]
        [else (find n (next test))]))

(define (smallest-divisor n)
  (find n 2))

(define (prime? n)
  (= n (smallest-divisor n)))

(define (report-prime elapased-time)
  (display " *** ")
  (display elapased-time))

(define (start-prime-test n start-time)
  (if (prime? n)
      (report-prime (- (runtime) start-time))))

(define (time-prime-test n)
  (newline)
  (display n)
  (start-prime-test n (runtime)))

(define (next n)
  (if (= n 2) 3
      (+ n 2)))