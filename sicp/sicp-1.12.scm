#lang planet neil/sicp

;;;1.12
;;;这道题翻译错误，原文为 "Write a procedure that computes elements of Pascal’s triangle by means of a recursive process." 意思是计算帕斯卡三角形的各个元素。

(define (pascal r c)
  (cond [(or (= r 0)
             (= c 0)
             (= r c)) 1]
        [(or (> c r)
             (< c 0)) (error "invalid location")]
        [else (+ (pascal (- r 1) (- c 1))
                 (pascal (- r 1) c))]))