#lang scheme

(require (planet neil/sicp))

(provide (all-defined-out))


;;;连接方式: 加法约束
;a1 连接器 被加数
;a2 连接器 加数
;sum 连接器 和
(define (adder a1 a2 sum)
  (define (process-new-value)
    (cond [(and (has-value? a1) (has-value? a2))
           (set-value! sum
                       (+ (get-value a1)
                          (get-value a2))
                       me)]
          [(and (has-value? a1) (has-value? sum))
           (set-value! a2
                       (- (get-value sum)
                          (get-value a1))
                       me)]
          [(and (has-value? a2) (has-value? sum))
           (set-value! a1
                       (- (get-value sum)
                          (get-value  a2))
                       me)]))
  (define (process-forget-value)
    (forget-value! sum me)
    (forget-value! a1 me)
    (forget-value! a2 me)
    ;考虑到参数带有默认值，或者由常量约束生成。
    (process-new-value))
  (define (me request)
    (cond [(eq? request 'I-have-a-value)
           (process-new-value)]
          [(eq? request 'I-lost-my-value)
           (process-forget-value)]
          [else (error "Unknown request -- ADDER" request)]))
  (connect a1 me) ;将约束关联到连接器，这种计算公式对连接器中的值存在依赖，当连接器中的值发生变化时，将通知所有计算公式进行计算，这是一种观察者模式，连接器为被观察者，约束为观察者。
  (connect a2 me)
  (connect sum me)
  me)


;;;连接方式: 乘法约束
;m1 连接器 被乘数
;m2 连接器 乘数
;product 连接器 积
(define (multiplier m1 m2 product)
  (define (process-new-value)
    (cond [(or (and (has-value? m1) (= 0 (get-value m1)))
               (and (has-value? m2) (= 0 (get-value m2))))
           (set-value! product 0 me)]
          [(and (has-value? m1) (has-value? m2))
           (set-value! product
                       (* (get-value m1) (get-value m2))
                       me)]
          [(and (has-value? product) (has-value? m1))
           (set-value! m2
                       (/ (get-value product) (get-value m1))
                       me)]
          [(and (has-value? product) (has-value? m2))
           (set-value! m1
                       (/ (get-value product) (get-value m2))
                       me)]
          [(and (not (has-value? m1)) (not (has-value? m2)))
           (error "Nither m1 nor m2 has value")]))
  (define (process-forget-value)
    (forget-value! product me)
    (forget-value! m1 me)
    (forget-value! m2 me)
    (process-new-value))
  (define (me request)
    (cond [(eq? request 'I-have-a-value)
           (process-new-value)]
          [(eq? request 'I-lost-my-value)
           (process-forget-value)]
          [else (error "Unknown request -- MULTIPLIER" request)]))
  (connect m1 me)
  (connect m2 me)
  (connect product me)
  me)

;;;;;;连接方式: 平方约束
(define (square a b)
  (define (process-new-value)
    (if (has-value? b)
        (if (< (get-value b) 0)
            (error "square less than 0 -- SQUARE"
                   (get-value b))
            (begin (set-value! a (sqrt b) me)
                   me))
        (if (has-value? a)
            (set-value! b (* (get-value a)
                             (get-value a)) me))))
  (define (process-forget-value)
    (forget-value! a me)
    (forget-value! b me)
    (process-new-value))
  (define (me request)
    (cond [(eq? request 'I-have-a-value)
           (process-new-value)]
          [(eq? request 'I-lost-my-value)
           (process-forget-value)]
          [else (error "Unknown request -- MULTIPLIER" request)]))
  (connect a me)
  (connect b me)
  me)



;连接方式: 常量约束
;为连接器设值
;连接器通知观察者时将忽略设置者
(define (constant value connector)
  (define (me request)
    (error "Unknown requet -- CONSTANT" request))
  (connect connector me)
  (set-value! connector value me)
  me)

;连接器的监听器，可以作为约束使用
(define (probe name connector)
  (define (print-probe value)
    (newline)
    (display "Probe: ")
    (display name)
    (display " = ")
    (display value))
  (define (process-new-value)
    (print-probe (get-value connector)))
  (define (process-forget-value)
    (print-probe "?"))
  (define (me request)
    (cond [(eq? request 'I-have-a-value)
           (process-new-value)]
          [(eq? request 'I-lost-my-value)
           (process-forget-value)]
          [else (error "Unknown request --PROBE" request)]))
  (connect connector me)
  me)

;;;通知约束，连接器有了新值
(define (inform-about-value constraint)
  (constraint 'I-have-a-value))

;;;通知约束，让连接器遗忘其值
(define (inform-about-no-value constraint)
  (constraint 'I-lost-my-value))


;创建一个连接器
(define (make-connector)
  (let ([value false] ;值
        [informant false] ;连接器的设值者
        [constraints '()]) ;所有与本连接器有关的约束
    (define (set-my-value newval setter)
      (cond [(not (has-value? me))
             (set! value newval)
             (set! informant setter)
             (for-each-except setter ;排除设值者，因为设值者可能是一个刚刚经过计算的约束，或者就不是约束。
                              inform-about-value
                              constraints)]
            [(not (= value newval))
             (error "Contradiction" (list value newval))]
            [else 'ignored]))
    (define (forget-my-value retractor)
      (if (eq? retractor informant)
          (begin (set! informant false)
                 (for-each-except retractor
                                  inform-about-no-value
                                  constraints))
          'ignored))
    (define (connect new-constraint)
      (if (not (memq new-constraint constraints))
          (set! constraints
                (cons new-constraint constraints)))
      (if (has-value? me)
          (inform-about-value new-constraint))
      'done)
    (define (me request)
      (cond [(eq? request 'has-value?)
             (if informant true false)] ;没有设值者，表示此连接器无值
            [(eq? request 'value) value]
            [(eq? request 'set-value!) set-my-value]
            [(eq? request 'forget) forget-my-value]
            [(eq? request 'connect) connect]
            [else (error "Unknown operation -- CONNECTOR" request)]))
    me))

(define (for-each-except exc proc seq)
  (define (loop items)
    (cond [(null? items) 'done]
          [(eq? (car items) exc)
           (loop (cdr items))]
          [else (proc (car items))
                (loop (cdr items))]))
  (loop seq))

(define (has-value? connector)
  (connector 'has-value?))

(define (get-value connector)
  (connector 'value))

(define (set-value! connector new-value informant)
  ((connector 'set-value!) new-value informant))

(define (forget-value! connector retractor)
  ((connector 'forget) retractor))

(define (connect connector new-constraint)
  ((connector 'connect) new-constraint))