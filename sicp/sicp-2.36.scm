;#lang scheme/load

(load "sequence.scm")

(define (accumulate-n op init seqs)
  (if (null? (car seqs))
      '()
      (cons (accumulate
             op
             init
             (map car ;取每个子序列的第一个元素
                  seqs))
            (accumulate-n
             op
             init
             (map cdr ;每个子序列去除第一个元素
                  seqs)))))