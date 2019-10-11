;#lang planet neil/sicp

;顶部序对
(define (front-ptr queue) (car queue))

;尾部序对
(define (rear-ptr queue) (cdr queue))

;设置顶部序对
(define (set-front-ptr! queue item)
  (set-car! queue item))

;设置尾部序对
(define (set-rear-ptr! queue item)
  (set-cdr! queue item))

;是否为空？
(define (empty-queue? queue)
  (null? (front-ptr queue)))

;创建队列
(define (make-queue)
  (cons '() '()))

;顶部元素
(define (front-queue queue)
  (if (empty-queue? queue)
      (error "FRONT called with an empty queue" queue)
      (car (front-ptr queue))))

;;;尾部插入新元素
(define (insert-queue! queue item)
  (let ([new-pair (cons item '())])
    (cond [(empty-queue? queue)
           (set-front-ptr! queue new-pair)
           (set-rear-ptr!  queue new-pair)
           queue]
          [else
           (set-cdr! (rear-ptr queue) new-pair)
           (set-rear-ptr! queue new-pair)
           queue])))

;;;删除顶部元素
(define (delete-queue! queue)
  (cond [(empty-queue? queue)
         (error "DELETE! called with an empty queue"
                queue)]
        [else
         (set-front-ptr! queue
                         (cdr (front-ptr queue)))
         queue]))


(define (print-queue queue)
  (car queue))