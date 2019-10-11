#lang racket

(define zero (lambda (f)
               (lambda (x) x)))


(define (add-1 n)
  (lambda (f)
    (lambda (x)
      (f ((n f) x)))))
;;;Church
;;;分析，n是个一个函数，((n f) x) 部分被执行后，得到的是n的内核，add-1将返回一个函数，此函数与n同构，区别是在n的内核外层套上一个f

;;;;;;;;;;;;;;;;;;;;;; one


(add-1 zero)

;代换1 ,将函数名称展开为lambda
((lambda (n)
   (lambda (f)
    (lambda (x)
      (f ((n f) x)))))
  
 (lambda (f)
   (lambda (x) x)))

;代换2 ,将zero代入add-1
(lambda (f)
    (lambda (x)
      (f
       (((lambda (f)
           (lambda (x) x))
         f)
        x))))

;代换3， 将f代入
(lambda (f)
    (lambda (x)
      (f
       ((lambda (x) x)
        x))))

;代换4，将x代入，最终得 one
(lambda (f)
    (lambda (x)
      (f x)))

(define one
  (lambda (f)
    (lambda (x)
      (f x))))


;;;;;;;;;;;;;;;;其他


(define two
    (lambda (f)
        (lambda (x)
            (f (f x)))))    ; 两个 f 调用

(define three
    (lambda (f)
        (lambda (x)
            (f (f (f x))))))        ; 三个 f 调用

(define four
    (lambda (f)
        (lambda (x)
            (f (f (f (f x)))))))    ; 四个 f 调用