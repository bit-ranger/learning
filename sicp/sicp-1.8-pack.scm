#lang planet neil/sicp




;求立方根
(define (cube-root x)
  
  ;平方
  (define (square x)
    (* x x))
  
  ;立方
  (define (cube x)
    (* x x x))
  
  ;立方根优化
  (define (improve guess x)
    (/ (+ (/ x (square guess))
          (* 2 guess))
       3))
  
  ;足够精确判定
  (define (goodEngough? old-guess new-guess)
    (> 0.01
       (/ (abs (- new-guess old-guess))
          old-guess)))
  
  ;逼近立方根,利用词法作用域,无需显式传递x,cube-root被调用时,x已得到值。
  (define (cube-iter guess)
    (if (goodEngough? guess (improve guess x))
        (improve guess x)
        (cube-iter (improve guess x))))
  
  (cube-iter 1.0))