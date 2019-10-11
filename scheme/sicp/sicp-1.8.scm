
;;;1.8

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

;逼近立方根
(define (cube-iter guess x)
  (if (goodEngough? guess (improve guess x))
      (improve guess x)
      (cube-iter (improve guess x) x)))

;求立方根
(define (cube-root x)
  (cube-iter 1.0 x))
