#lang scheme

(require (planet neil/sicp))

(require (file "p223-stream.ss"))


;序列加速器
(define (euler-transform s)
  (let ([s0 (stream-ref s 0)]
        [s1 (stream-ref s 1)]
        [s2 (stream-ref s 2)])
    (cons-stream (- s2 (/ (* (- s2 s1) (- s2 s1))
                          (+ s0 (* -2 s1) s2)))
                 (euler-transform (stream-cdr s)))))

;加速器生成的流的表列
;transform 流加速器
(define (make-tableau transform s)
  (cons-stream s
               (make-tableau transform
                             (transform s))))

;加速器生成的流序列
(define (accelerated-sequence transform s)
  (stream-map stream-car
              (make-tableau transform s)))