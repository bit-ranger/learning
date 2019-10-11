

;;;1.7

;平方
(define (square x)
  (* x x))

;平均数
(define (avg x y)
  (/ (+ x y) 2))

;优化预测平方根
(define (improve guess x)
  (avg guess (/ x guess)))

;判断是否预测平方根足够精确
;可以发现，对于特别小的数，比如 0.00009 ，书本给出的 sqrt 并不能计算出正确的答案； 而对于特别大的数，因为 mit-scheme 实现的小数精度不足以表示两个大数之间的差，所以 sqrt 会陷入死循环而无法得出正确结果。
;要避免这一错误，我们按照练习所说，对 good-enough? 进行修改：不再检测猜测值 guess 的平方与 x 之间的差，而是检测新旧两次猜测值之间的比率，当比率变化非常小时，程序就停止 improve 。
;(define (goodEnough? guess x)
;  (< (abs (- (square guess) x)) 0.001))

(define (goodEnough? guess new-guess)
  (> 0.01
     (/ (abs (- new-guess guess))
        guess)))

;求平方根需要提供预测平方根
(define (squrt-iter guess x)
  (if (goodEnough? guess (improve guess x))
      (improve guess x)
      (squrt-iter (improve guess x) x)))

;求平方根
(define (squrt x)
    (squrt-iter 1.0 x))
