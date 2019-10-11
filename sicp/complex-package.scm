#lang scheme


;;;为数据实体增加类型标签
;type-tag 类型符号
;contents 数据实体
(define (attach-tag type-tag contents)
  (cons type-tag contents))

;;;取出类型标签
(define (type-tag z)
  (if (pair? z)
      (car z)
      (error "Bad tagged datum -- TYPE-TAG" z)))

;;;取出数据实体
(define (contents z)
  (if (pair? z)
      (cdr z)
      (error "Bad tagged datum -- CONTENTS" z)))


;op 函数名
;type 类型/类型列表
;item 函数体
(define (put op type item)
  '())

;op 函数名
;type 类型/类型列表
(define (get op type)
  '())

;;;对函数进行应用，应用规则是根据参数类型自动分派
; op 函数名
; args 参数列表
(define (apply-generic op . args)
  (let ([type-tags (map type-tag args)]) ;;;参数类型的列表
    (let ([proc (get op type-tags)])     ;;;通过函数名与类型列表取到函数体
      (if proc
          (apply proc (map contents args))
          (error "No method for these types -- APPLY-GENERIC"
                 (list op type-tags))))))




;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;功能函数

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




;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;界面函数

(define (make-from-real-imag x y)
  ((get 'make-from-real-imag 'rectangular) x y))

(define (make-from-mag-ang r a)
  ((get 'make-from-mag-ang 'polar) r a))


(define (real-part z)
  (apply-generic 'real-part z))

(define (imag-part z)
  (apply-generic 'imag-part z))

(define (magnitude z)
  (apply-generic 'magnitude z))

(define (angle z)
  (apply-generic 'angle z))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;功能实现包


;;;安装直角坐标系实现包
(define (install-rectangular-package)
  ;;内部过程
  (define (make-from-real-imag x y)
    (cons x y))
  (define (make-from-mag-ang r a)
    (cons (* r (cos a)) (* r (sin a))))
  (define (real-part z)
    (car z))
  (define (imag-part z)
    (cdr z))
  (define (magnitude z)
    (sqrt (+ (square (real-part z))
             (square (imag-part z)))))
  (define (angle z)
    (atan (imag-part z) (real-part z)))
  ;;系统剩余接口
  (define (tag x)
    (attach-tag 'rectangular x))
  ;此处的类型应该为一个列表，
  ;apply-generic函数会获取参数的类型列表,
  ;然后通过这个类型列表get函数体
  (put 'real-part '(rectangular) real-part)
  (put 'imag-part '(rectangular) imag-part)
  (put 'magnitude '(rectangular) magnitude)
  (put 'angle     '(rectangular) angle)
  ;从思想上看，具体的数据对象，应该是一个唯一的类型。
  ;从技术上看，获取构造函数体时并未通过apply-generic函数，而是指定一个确定的类型。
  (put 'make-from-real-imag 'rectangular
       (lambda (x y) (tag (make-from-real-imag x y))))
  (put 'make-from-mag-ang 'rectangular
       (lambda (r a) (tag (make-from-mag-ang r a))))
  'done)



;;;安装极坐标系实现包
(define (install-polar-package)
  ;;内部过程
  (define (make-from-real-imag x y)
    (cons (sqrt (+ (square x)
                   (square y)))
          (atan y x)))
  (define (make-from-mag-ang r a)
    (cons r a))
  (define (real-part z)
    (* (magnitude z) (cos (angle z))))
  (define (imag-part z)
    (* (magnitude z) (sin (angle z))))
  (define (magnitude z)
    (car z))
  (define (angle z)
    (cdr z))
  ;;系统剩余接口
  (define (tag x)
    (attach-tag 'polar x))
  (put 'real-part '(polar) real-part)
  (put 'imag-part '(polar) imag-part)
  (put 'magnitude '(polar) magnitude)
  (put 'angle     '(polar) angle)
  (put 'make-from-real-imag 'polar
       (lambda (x y) (tag (make-from-real-imag x y))))
  (put 'make-from-mag-ang 'polar
       (lambda (r a) (tag (make-from-mag-ang r a))))
  'done)




















;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; 其他
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;
(define (square x)
  (* x x))