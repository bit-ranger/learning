#lang scheme

(require (planet neil/sicp))

(require (file "p223-stream.ss"))

(require (file "p228-implicit-stream.ss"))

(require (file "p233-pi-stream.ss"))

(define (integral delayed-integrand initial-value dt)
  (define int-stream
    (cons-stream initial-value
                 (let ([integrand (force delayed-integrand)])
                   (add-stream (scale-stream integrand dt)
                               int-stream))))
  int-stream)

(define (solve f y0 dt)
  (define y (integral (delay dy) y0 dt))
  (define dy (stream-map f y))
  y)