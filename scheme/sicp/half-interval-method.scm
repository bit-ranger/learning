#lang planet neil/sicp

;;;寻找函数的零点，即f(x) = 0，找出此x，即高中数学中函数的根
;neg-point 负数
;pos-point 正数
(define (search f neg-point pos-point)
  (let ([midpoint (average neg-point pos-point)]) ;先求平均数
    (if (close-enough? neg-point pos-point) ;如果足够接近，直接返回中点
        midpoint
        (let ([test-value (f midpoint)])
          ;如果中点为正数，则从负数与中点之间寻找
          ;如果中点为负数，则从中点与正数之间寻找
          ;如果中点为0，则直接返回
          (cond [(positive? test-value) (search f neg-point midpoint)]
                [(negative? test-value) (search f midpoint pos-point)]
                [else midpoint])))))

;;;是否足够接近
(define (close-enough? x y)
  (< (abs (- x y)) 0.001))

;;;求平均数
(define (average a b)
  (/ (+ a b) 2))


;;;如果函数以及给定的两个点不存在零点，那么使用serch就会得到错误的结果
;;;该函数用来判断
;;;折半法

(define (half-interval-method f a b)
  (let ([a-v (f a)]
        [b-v (f b)])
    (cond [(and (positive? a-v) (negative? b-v)) (search f b a)]
          [(and (positive? b-v) (negative? a-v)) (search f a b)]
          [else (error "Values are not of opposite sign" a b)])))


;;;折半法求PI
(half-interval-method sin 2.0 4.0)

;;;求函数的根
(half-interval-method (lambda (x) (- (* x x x) (* x x) 3)) 1.0 2.0)