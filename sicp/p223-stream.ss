#lang scheme

(require (planet neil/sicp))

(provide (all-defined-out))

;cons-stream必须是特殊形式，不能为普通过程，否则求值模型会自动计算y，此处仅为演示。
;同理delay也必须是特殊形式
;(define (cons-stream x y)
;  (cons x (delay y)))

;所谓延迟求值，就只exp不直接计算，而是用一个lambda包裹，这个lambda作为值传递
;对同一个Stream多次调用cdr，直接返回值。memo-proc是绑定在cdr上的存储设备，只有调用同一个Stream的cdr才能生效。
;(define (delay exp)
;(define (memo-proc proc)
;  (let ([already-run? false]
;        [result false])
;    (lambda ()
;      (if (not alreay-run?)
;          (begin (set! result (proc))
;                 (set! already-run? true)
;                 result)
;          result))))
;(memo-proc (lambda () exp)))

;强制求值，就是把表达式外层包裹的lambda壳剥去
;(define (force delayed-object)
;  (delayed-object))




(define (stream-car stream)
  (car stream))

(define (stream-cdr stream)
  (force (cdr stream)))


(define (stream-ref s n)
  (if (= n 0)
      (stream-car s)
      (stream-ref (stream-cdr s)
                  (- n 1))))

;流映射，参数为一组流，要求所有流等长
(define (stream-map proc . list-of-stream)
  (if (null? (car list-of-stream))
      the-empty-stream
      (cons-stream (apply proc
                          ;取出每个流的第一个元素
                          (map (lambda (s) (stream-car s)) 
                               list-of-stream)) ;
                   (apply stream-map
                          proc
                          (map (lambda (s)
                                 (stream-cdr s))
                               list-of-stream)))))

(define (stream-for-each proc s)
  (if (stream-null? s)
      'done
      (begin (proc (stream-car s))
             (stream-for-each proc (stream-cdr s)))))

(define (display-stream s)
  (stream-for-each display-line s))

(define (display-line x)
  (newline)
  (display x))

;返回的满足筛选条件的第一个元素，及其继续筛选的承诺组成的流。
(define (stream-filter pred stream)
  (cond [(stream-null? stream) the-empty-stream]
        [(pred (stream-car stream))
         (cons-stream (stream-car stream)
                      (stream-filter pred
                                     (stream-cdr stream)))]
        [else (stream-filter pred (stream-cdr stream))]))


(define (integers-starting-from n)
  (cons-stream n (integers-starting-from (+ 1 n))))