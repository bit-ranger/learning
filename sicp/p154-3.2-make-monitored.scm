#lang scheme

(define (make-monitored f)
  (define count 0)
  (define (call-and-inc . args)
    (set! count (+ 1 count))
    (apply f args))
  (define (reset-count)
    (set! count 0))
  (define (how-many-calls?)
    count)
  (define (dispatch . args)
    (cond [(eq? 'how-many-calls? (car args))
           (how-many-calls?)]
          [(eq? 'reset-count (car args))
           (reset-count)]
          [else (apply call-and-inc args)]))
  dispatch)
