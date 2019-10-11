
(load "fixed-point-transform.scm")
(load "sicp-1.43.scm")
(load "lg.scm")


;;;多次平均阻尼
(define (average-damp-n f n)
  ((repeated average-damp n) f))

;;;多次平均阻尼的的求不动点函数
;f函数
;first-guess 不动点的初始猜测
;平均阻尼次数
(define (fixed-point-transform f first-guess n)
  (fixed-point (average-damp-n f n) first-guess))


;;;开根
(define (open-root x n)
  (fixed-point-transform
   (lambda (y) ;若 y ^ n = x 则 y = x / (y ^ (n - 1))
     (/ x (power y (- n 1))))
   1.0
   (lg n)));至少需要 (lg n) 次平均阻尼


(define (power x n)
  (cond [(= n 0) 1]
        [(= x 1) 1]
        [else ((repeated (lambda (p) (* p x))
                         n)
               1)]))