#lang scheme

(require (planet neil/sicp))

(require (file "p223-stream.ss"))

(require (file "p228-implicit-stream.ss"))

(require (file "p233-pi-stream.ss"))

;(stream-filter (lambda (pair)
;                 (prime? (+ (car pair)
;                            (cdr pair))))
;               int-pairs)




(define (pairs s t)
  (cons-stream (list (stream-car s) (stream-car t))
               (interleave (stream-map (lambda (x)
                                         (list (stream-car s) x))
                                       (stream-cdr t))
                           (pairs (stream-cdr s) (stream-cdr t)))))

;组合两个流
;使其可以交替从两个流中取元素
(define (interleave s1 s2)
  (if (stream-null? s1)
      s2
      (cons-stream (stream-car s1)
                   (interleave s2 (stream-cdr s1)))))

