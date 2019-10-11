#lang planet neil/sicp

(define (first-denomination enable-kinds)
  (cond [(= enable-kinds 1) 1]
        [(= enable-kinds 2) 5]
        [(= enable-kinds 3) 10]
        [(= enable-kinds 4) 25]
        [(= enable-kinds 5) 50]))

(define (cc amount enable-kinds)
  (cond [(= amount 0) 1]
        [(< amount 0) 0]
        [(= enable-kinds 0) 0]
        [else (+ (cc amount
                     (- enable-kinds 1))
                 (cc (- amount (first-denomination enable-kinds))
                     enable-kinds))]))

(define (count-change x)
  (cc x 5))