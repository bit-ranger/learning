#lang racket/load

(load "sicp-1.43.scm")

;平滑函数，接受一个函数，生成一个平滑函数
(define (smooth f)
  (let ([dx 0.00001])
    (lambda (x)
      (/ (+ (f (- x dx))
            (f x)
            (f (+ x dx)))
          3))))

;;;重复
;每个smooth都会生成一个新函数，此函数依赖旧函数的执行结果，如此一来，当最外层包裹的函数被调用时，此调用会传递到最内层的函数中去。
(define (smooth-n f n)
  ((repeated smooth n) f))

(define square-5-2
  ((smooth-n square  2) 5))
