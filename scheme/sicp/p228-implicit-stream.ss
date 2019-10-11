#lang scheme

(require (planet neil/sicp))

(require (file "p223-stream.ss"))

(provide (all-defined-out))

;永远为1的无穷流
(define ones (cons-stream 1 ones))

;将两个流逐对元素相加
(define (add-stream s1 s2)
  (stream-map + s1 s2))

;自然数流，下一个元素是1+当前元素
(define integers (cons-stream 1 (add-stream ones integers)))

;斐波那契数列
(define fibs
  (cons-stream 0
               (cons-stream 1
                            (add-stream (stream-cdr fibs)
                                        fibs))))

;伸缩流
(define (scale-stream stream factor)
  (stream-map (lambda (x) (* x factor)) stream))

;2次幂
(define power-of-two
  (cons-stream 1 (scale-stream power-of-two 2)))

;素数流
(define primes
  (cons-stream 2 (stream-filter prime? (integers-starting-from 3))))

;是否素数？
(define (prime? n)
  (define(iter ps)
    (cond [(> (square (stream-car ps)) n) true]
          [(divisible? n (stream-car ps)) false]
          [else (iter (stream-cdr ps))]))
  (iter primes))

;能否整除？
(define (divisible? x y)
  (= (remainder x y) 0))

(define (square x)
  (* x x))

;将两个流逐对元素相乘
(define (mul-stream s1 s2)
  (stream-map * s1 s2))

(define factorials (cons-stream 1 (mul-stream factorials (integers-starting-from 2))))

;阶加值序列
;(define (partial-sums s)
;  (cons-stream (stream-car s)
;               (stream-map (lambda (x)
;                             (+ x (stream-car s))) ;s为闭包参数
;                           (partial-sums (stream-cdr s)))))

;阶加值序列
(define (partial-sums s)
  (cons-stream (stream-car s)
               (add-stream (partial-sums s)
                           (stream-cdr s))))

;合并两个流
(define (merge s1 s2)
  (cond [(stream-null? s1) s2]
        [(stream-null? s2) s1]
        [else
         (let ([s1car (stream-car s1)]
               [s2car (stream-car s2)])
           (cond [(< s1car s2car)
                  (cons-stream s1car (merge (stream-cdr s1) s2))]
                 [(> s1car s2car)
                  (cons-stream s2car (merge s1 (stream-cdr s2)))]
                 [else (cons-stream s1car
                                    (merge (stream-cdr s1)
                                           (stream-cdr s2)))]))]))
;分数转小数
;num 分子
;den 分母
;randix 基数
(define (expand num den randix)
  (cons-stream
   (quotient (* num randix) den)
   (expand (remainder (* num randix) den) den randix)))