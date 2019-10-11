#lang planet neil/sicp

;创建队列
(define (make-queue)
  (let ([front-ptr '()]
        [rear-ptr '()])
    
    ;;;尾部插入新元素
(define (insert-queue! item)
  (let ([new-pair (cons item '())])
    (cond [(null? front-ptr)
           (set! front-ptr new-pair)
           (set! rear-ptr  new-pair)
           front-ptr]
          [else
           (set-cdr! rear-ptr new-pair)
           (set! rear-ptr new-pair)
           front-ptr])))


;;;删除顶部元素
(define (delete-queue!)
  (cond [(null? front-ptr)
         (error "DELETE! called with an empty queue" dispatch)]
        [else
         (set! front-ptr (cdr front-ptr))
         front-ptr]))

    
    (define (dispatch m)
      (cond [(eq? m 'front-queue) (car front-ptr)]
            [(eq? m 'insert-queue!) insert-queue!]
            [(eq? m 'delete-queue!) delete-queue!]))
    
    dispatch))








(define (print-queue queue)
  (car queue))