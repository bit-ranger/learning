#lang scheme

;;;为数据实体增加类型标签
;type-tag 类型符号
;contents 数据实体
(define (attach-tag type-tag contents)
  (if (number? contents)
      contents
      (cons type-tag contents)))

;;;取出类型标签
(define (type-tag z)
  (cond [(number? z) 'scheme-number]
        [(pair? z) (car z)]
        [else (error "Bad tagged datum -- TYPE-TAG" z)]))

;;;取出数据实体
(define (contents z)
  (cond [(number? z) z]
        [(pair? z) (cdr z)]
        [else (error "Bad tagged datum -- CONTENTS" z)]))

;op 函数名
;type 类型/类型列表
(define (get op type)
  '())

;op 函数名
;type 类型/类型列表
;item 函数体
(define (put op type item)
  '())

;
(define (get-coercion type1 typ2)
  '())

;;;对函数进行应用，应用规则是根据参数类型自动分派
;;;分派时将剥去类型标记
; op 函数名
; args 参数列表
(define (apply-generic op . args)
  (let ([type-tags (map type-tag args)]) ;;;参数类型的列表
    (let ([proc (get op type-tags)])     ;;;通过函数名与类型列表取到函数体
      (if proc
          (apply proc (map contents args))
          (if (= (length args) 2)
              (let ([type1 (car type-tags)]
                    [type2 (cadr type-tags)]
                    [a1 (car args)]
                    [a2 (cadr args)])
                (if (eq? type1 type2)
                    (error "No method for these types"
                           (list op type1 type2))
                    (let ([t1->t2 (get-coercion type1 type2)]
                          [t2->t1 (get-coercion type2 type1)])
                      (cond [t1->t2 (apply-generic op (t1->t2 a1) a2)]
                            [t2->t1 (apply-generic op a1 (t2->t1 a2))]
                            [else (error "No method for these types"
                                         (list op type-tags))]))))
              (error "No method for these types"
                     (list op type-tags)))))))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(define (add x y)
  (apply-generic 'add x y))

(define (sub x y)
  (apply-generic 'sub x y))

(define (mul x y)
  (apply-generic 'nul x y))

(define (div x y)
  (apply-generic 'div x y))

(define (equ? x y)
  (apply-generic 'equ? x y))

(define (=zero? x)
  (apply-generic '=zero? x))

;;;安装一个包，其实就是安装一个数据类型以及该类型提供的操作
;;;在面向对象语言中，这就是一个类。
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


(define (install-scheme-number-package)
  (define (tag x)
    (attach-tag 'scheme-number x))
  (put 'add '(scheme-number scheme-number)
       (lambda (x y) (tag (+ x y))))
  (put 'sub '(scheme-number scheme-number)
       (lambda (x y) (tag (- x y))))
  (put 'mul '(scheme-number scheme-number)
       (lambda (x y) (tag (* x y))))
  (put 'div '(scheme-number scheme-number)
       (lambda (x y) (tag (/ x y))))
  (put 'make 'scheme-number
       (lambda (x) (tag x)))
  (put 'equ? '(scheme-number scheme-number)
       (lambda (x y) (= x y)))
  (put '=zero? '(scheme-number)
       (lambda (x) (= x 0)))
  'done)

(define (make-scheme-number n)
  ((get 'make 'scheme-number) n))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(define (install-rational-package)
  (define (number x) (car x))
  (define (denom  x) (cdr x))
  (define (make-rat n d)
    (let ([g (gcd n d)])
      (cons (/ n g) (/ d g))))
  (define (add-rat x y)
    (make-rat (+ (* (number x)
                    (denom  y))
                 (* (number y)
                    (denom  x)))
              (* (denom x)
                 (denom y))))
  (define (sub-rat x y)
    (make-rat (- (* (number x)
                    (denom  y))
                 (* (number y)
                    (denom  x)))
              (* (denom x)
                 (denom y))))
  (define (mul-rat x y)
    (make-rat (* (number x) (number y))
              (* (denom  x) (denom  y))))
  (define (div-rat x y)
    (make-rat (* (number x) (denom  y))
              (* (denom  x) (number y))))
  (define (tag x) (attach-tag 'rational x))
  (put 'add '(rational rational)
       (lambda (x y) (tag (add-rat x y))))
  (put 'sub '(rational rational)
       (lambda (x y) (tag (sub-rat x y))))
  (put 'mul '(rational rational)
       (lambda (x y) (tag (mul-rat x y))))
  (put 'div '(rational rational)
       (lambda (x y) (tag (div-rat x y))))
  (put 'make 'rational
       (lambda (n d) (tag (make-rat n d))))
  (put 'equ? '(rational rational)
       (lambda (x y)
         (and (= (number x) (number y))
              (= (denom  x) (denom  y)))))
  (put '=zero? '(rational)
       (lambda (x) (= (number x) 0)))
  'done)

(define (make-rational n d)
  ((get 'make 'rational) n d))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(define (install-complex-package)
  (define (make-from-real-imag x y)
    ((get 'make-from-real-imag 'rectangular) x y))
  (define (make-from-mag-ang r a)
    ((get 'make-from-mag-ang) 'polar) r a)
  (define (real-part z)
    (apply-generic 'real-part z))
  (define (imag-part z)
    (apply-generic 'imag-part z))
  (define (magnitude z)
    (apply-generic 'magnitude z))
  (define (angle z)
    (apply-generic 'angle z))
  
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
  (define (mul-complex z1 z2)
    (make-from-mag-ang (* (magnitude z1) (magnitude z2))
                       (+ (angle z1) (angle z2))))
  ;;;复数 除 运算
  (define (div-complex z1 z2)
    (make-from-mag-ang (/ (magnitude z1) (magnitude z2))
                       (- (angle z1) (angle z2))))
  
  (define (tag z) (attach-tag 'complex z))
  
  (put 'add '(complex complex)
       (lambda (z1 z2) (tag (add-complex z1 z2))))
  (put 'sub '(complex complex)
       (lambda (z1 z2) (tag (sub-complex z1 z2))))
  (put 'mul '(complex complex)
       (lambda (z1 z2) (tag (mul-complex z1 z2))))
  (put 'div '(complex complex)
       (lambda (z1 z2) (tag (div-complex z1 z2))))
  (put 'make-from-real-imag 'complex
       (lambda (x y) tag (make-from-real-imag x y)))
  (put 'make-from-mag-ang 'complex
       (lambda (r a) tag (make-from-mag-ang r a)))
  (put 'equ? '(complex complex)
       (lambda (x y)
         (and (= (real-part x) (real-part y))
              (= (imag-part x) (imag-part y)))))
  (put '=zero? '(rational)
       (lambda (x)
         (and (= (real-part x) 0)
              (= (imag-part x) 0))))
  
  (install-rectangular-package)
  (install-polar-package)
  'done)


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


(define (make-complex-from-real-imag x y)
  ((get 'make-from-real-imag 'complex) x y))

(define (make-complex-from-mag-ang r a)
  ((get 'make-from-mag-ang 'complex) r a))





















;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; 其他
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;
(define (square x)
  (* x x))