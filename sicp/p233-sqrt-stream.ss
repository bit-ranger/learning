#lang scheme

(require (planet neil/sicp))

(require (file "p223-stream.ss"))

(define (average a b)
  (/ (+ a b)
     2))

(define (sqrt-improve guess x)
  (average guess (/ x guess)))

(define (sqrt-stream x)
  ;将已生成的流进行传递，使每次使用时都可以直接取用现有的值，复杂度为O(n)
  (define guesses
    (cons-stream 1.0
                 (stream-map (lambda (guess)
                               (sqrt-improve guess x))
                             guesses)))
  guesses)


(define (stream-limit stream tolerance)
  (let ([s0 (stream-ref stream 0)]
        [s1 (stream-ref stream 1)])
    (cond [(< (abs (- s0 s1)) tolerance)
           s1]
          [else (stream-limit (stream-cdr stream) tolerance)])))

(stream-limit (sqrt-stream 9) 0.001)


