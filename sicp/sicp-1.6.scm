#lang planet neil/sicp

;;;1.6


;平方
(define (square x)
  (* x x))

;;;1.6

;平均数
(define (avg x y)
  (/ (+ x y) 2))

;优化预测平方根
(define (improve guess x)
  (avg guess (/ x guess)))

;判断是否预测平方根足够精确
(define (goodEnough? guess x)
  (< (abs (- (square guess) x)) 0.001))

;求平方根需要提供预测平方根
(define (squrt-iter guess x)
  (if (goodEnough? guess x)
      guess
      (squrt-iter (improve guess x) x)))

;求平方根
(define (squrt x)
    (squrt-iter 1.0 x))

;;;;;;;;;;;;;;;;;;;
;常规化的if判断
(define (new-if predicate then-case else-case)
  (cond [predicate then-case]
        [else else-case]))

;改写开平方根
;求平方根需要提供预测平方根
(define (squrt-iter-new guess x)
  (new-if (goodEnough? guess x)
      guess
      (squrt-iter-new (improve guess x) x)))

;求平方根
(define (squrt-new x)
    (squrt-iter-new 1.0 x))

;改写后进入死循环，问题根源在于应用序。squrt-iter-new执行时，解释器试图算出参数的值然后应用到运算符上，但是，squrt-iter-new是个递归函数，值是算不出来的。
;如果使用cond if的话解释器会对其进行特殊处理，先计算判断表达式的值，一直算到#t，#f为止，然后再选择分支运算。