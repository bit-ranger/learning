#lang scheme/load

(load "sequence.scm")

;;;利用累积实现映射
(define (map p sequence)
  (accumulate (lambda (x y)
                (cons (p x) y))
              '()
              sequence))

;;;利用累积实现拼接
(define (append seq1 seq2)
  (accumulate cons seq2 seq1))

;;;利用累积计算长度
(define (length sequence)
  (accumulate (lambda (x y)
                (+ y 1)) 0 sequence))