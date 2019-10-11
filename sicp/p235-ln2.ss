#lang scheme

(require (planet neil/sicp))

(require (file "p223-stream.ss"))

(require (file "p228-implicit-stream.ss"))

(require (file "p233-pi-stream.ss"))

(define (ln2-summands n)
  (cons-stream (/ 1.0 n)
               (stream-map -
                           (ln2-summands (+ n 1)))))

(define ln-stream
  (partial-sums (ln2-summands 1)))

(display-stream (accelerated-sequence euler-transform ln-stream))


