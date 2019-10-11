

(load "fixed-point.scm")


;;;可转换的求不动点函数
(define (fixed-point-transform f transform first-guess)
  (fixed-point (transform f) first-guess))


;开平方根
(define (sqrt x)
  (fixed-point-transform (lambda (y) (/ x y))
                         average-damp
                         1.0))
