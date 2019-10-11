#lang scheme

(require (planet neil/sicp))

(provide (all-defined-out))


;;;创建串行化组
;本质上是一个代理，给函数加上了一个互斥锁
(define (make-serializer)
  (let ([mutex (make-mutex)])
    (lambda (p)
      (define (serialized-p . args)
        (mutex 'acquire)
        (let ([val (apply p args)])
          (mutex 'release)
          val))
      serialized-p)))

;互斥锁
(define (make-mutex)
  (let ([cell (list false)]) ;表示锁是否已被使用

    ;是否已被使用
    ;如果未被使用，则将其设为已被使用。
    ;前提是该过程必须以原子操作方式运行
    ;一旦某个进程检查cell为假，那么该进程就必须在其他进程检查cell之前设其为真。
    ;在采用时间片轮转调度的系统上，设置该过程执行时不允许时间分片可以实现原子性。
    (define (test-and-set! cell)
      (if (car cell)
          true
          (begin (set-car! cell true)
                 false)))
    
    (define (clear! cell)
      (set-car! cell false))
    
    (define (me m)
      (cond [(eq? m 'acquire)
             ;如果已被使用，一直循环产生阻塞效果。
             (if (test-and-set! cell)
                 (me 'acquire))]
            [(eq? m 'release) (clear! cell)]))
    me))

