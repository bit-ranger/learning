#lang scheme

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; 抽象使用层
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;复数 加 运算
(define (add-complex z1 z2)
  (make-from-real-imag (+ (real-part z1)
                          (real-part z2))
                       (+ (imag-part z1)
                          (imag-part z2))))
;;;复数 减 运算
(define (sub-complex z1 z2)
  (make-from-real-imag (- (real-part z1)
                          (real-part z2))
                       (- (imag-part z1)
                          (imag-part z2))))

;;;复数 乘 运算
(define (mul-comlex z1 z2)
  (make-from-mag-ang (* (magnitude z1) (magnitude z2))
                     (+ (angle z1) (angle z2))))

;;;复数 除 运算
(define (div-complex z1 z2)
  (make-from-mag-ang (/ (magnitude z1) (magnitude z2))
                     (- (angle z1) (angle z2))))



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; 实现层
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;为数据实体增加类型标签，得到一个复数
;type-tag 类型符号
;contents 数据实体
(define (attach-tag type-tag contents)
  (cons type-tag contents))


;直角坐标系实现
;;;;;;;;;;;;;;;;;;;;;;;;
;;;由实部虚部构建复数
;;;复数类型为直角坐标系
(define (make-from-real-imag-rectangular real imag)
  (attach-tag 'rectangular (cons real imag)))

;;;由模与幅角构建复数
;;;复数类型为直角坐标系
(define (make-from-mag-ang-rectangular mag ang)
  (attach-tag 'rectangular (cons (* mag (cos ang))
                                 (* mag (sin ang)))))

;;;从直角坐标系类型的复数中取出实部
(define (real-part-rectangular z)
  (cadr z))

;;;从直角坐标系类型的复数中取出虚部
(define (imag-part-rectangular z)
  (cddr z))

;;;从直角坐标系类型的复数中取出模
(define (magnitude-rectangular z)
  (sqrt (+ (square (real-part-rectangular z))
            (square (imag-part-rectangular z)))))

;;;从直角坐标系类型的复数中取出幅角
(define (angle-rectangular z)
  (atan (imag-part-rectangular z)
        (real-part-rectangular z)))



;极坐标系实现
;;;;;;;;;;;;;;;;;;;;;;;;
;;;由实部虚部构建复数
;;;复数类型为极坐标系
(define (make-from-real-imag-polar real imag)
  (attach-tag 'polar (cons (sqrt (+ (square real)
                                (square imag)))
                       (atan imag real))))

;;;由模与幅角构建复数
;;;复数类型为极坐标系
(define (make-from-mag-ang-polar mag ang)
  (attach-tag 'polar (cons mag ang)))

;;;从极坐标类型的复数中取出实部
(define (real-part-polar z)
  (* (magnitude-polar z) (cos (angle-polar z))))

;;;从极坐标类型的复数中取出虚部
(define (imag-part-polar z)
  (* (magnitude-polar z) (sin (angle-polar z))))

;;;从极坐标类型的复数中取出模
(define (magnitude-polar z)
  (cadr z))

;;;从极坐标类型的复数中取出幅角
(define (angle-polar z)
  (cddr z))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; 选择层
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;;;因为每个数据对象都有一个类型作为标记，
;;;选择函数就能够在不同的数据上用一种通用的方式操作。

;;;取出复数的类型标签
(define (type-tag z)
  (if (pair? z)
      (car z)
      (error "Bad tagged datum -- TYPE-TAG" z)))

;;;取出复数的数据实体
;(define (contents z)
;  (if (pair? z)
;      (cdr z)
;      (error "Bad tagged datum -- CONTENTS" z)))

;;;是否直角坐标类型
(define (rectangular? z)
  (eq? (type-tag z) 'rectangular))

;;;是否极坐标类型
(define (polar? z)
  (eq? (type-tag z) 'polar))


;;;复数的实部
(define (real-part z)
  (cond [(rectangular? z)
         (real-part-rectangular z)]
        [(polar? z)
         (real-part-polar z)]
        [else (error "Unknown type -- REAL-PART" z)]))

;;;复数的虚部
(define (imag-part z)
  (cond [(rectangular? z)
         (imag-part-rectangular z)]
        [(polar? z)
         (imag-part-polar z)]
        [else (error "Unknow type -- IMAG-PART" z)]))

;;;复数的模
(define (magnitude z)
  (cond [(rectangular? z)
         (magnitude-rectangular z)]
        [(polar? z)
         (magnitude-polar z)]
        [else (error "Unknow type -- MAGNITUDE" z)]))

;;;复数的幅角
(define (angle z)
  (cond [(rectangular? z)
         (angle-rectangular z)]
        [(polar? z)
         (angle-polar z)]
        [else (error "Unkonw type -- ANGLE" z)]))

;;;由实部虚部构造复数
(define (make-from-real-imag real imag)
  ;选其中一种实现来使用，两种实现可以共存
  (make-from-real-imag-rectangular real imag))

;;;由模与幅角构造复数
(define (make-from-mag-ang mag ang)
  (make-from-mag-ang-polar mag ang))













;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; 其他
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;
(define (square x)
  (* x x))