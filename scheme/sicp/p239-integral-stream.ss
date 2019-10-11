#lang scheme

(require (planet neil/sicp))

(require (file "p223-stream.ss"))

(require (file "p228-implicit-stream.ss"))

(require (file "p233-pi-stream.ss"))

(define (integral integrand initial-value dt)
  (define int
    (cons-stream initial-value
                 ;int的新值循环送回加法器
                (add-stream (scale-stream integrand dt)
                            int)))
  int)

(display-stream (integral integers 1 2))