
(define tolerance 0.00001)

;;;寻找函数的不动点，若 f(x) = x，则x为不动点
;函数
;初始的近似值
(define (fixed-point f first-guess)
  (define (close-enough? v1 v2)
    (< (abs (- v1 v2)) tolerance))
  (define (try guess)
    (let ([next (f guess)])
      (if (close-enough? guess next)
          next
          (try next))))
  (try first-guess))


;;余弦函数的不动点
(define cos-fix
  (fixed-point cos 1.0))

;;方程的不动点
(define func-fix
  (fixed-point (lambda (x) (+ (sin x) (cos x))) 1.0))

;;开平方根 x = y * y
;;该过程不收敛（x与f(x)逐渐接近），会形成死循环
;;设初值为 y1, y2=x/y1, y3=x/(x/y1)=y1
(define (sqrt x)
  (fixed-point (lambda (y) (/ x y)) 1.0))


; x 的平方根必在 y 与 x/y 之间
; 根据公式推导 y = x / y , y + y = y + x / y , y = 1/2 * (y+x/y)
; 修改函数如下
(define (sqrt-2 x)
  (fixed-point (lambda (y) (/ (+ y (/ x y)) 2)) 1.0))


;;;平均阻尼模板
(define (average-damp f)
  (lambda (x) (/ (+ x (f x)) 2)))