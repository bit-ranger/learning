#lang planet neil/sicp

;;查找key对应的value
(define (lookup key table)
  (let ([record (assoc key (cdr table))])
    (if record
        (cdr record)
        #f)))

;;查找key对应的record
(define (assoc key records)
  (cond [(null? records) #f]
        [(equal? key (caar records))
         (car records)]
        [else (assoc key (cdr records))]))



;;向表中插入记录或修改已存在记录
(define (insert! key value table)
  (let ([record (assoc key (cdr table))])
    (if record
        (set-cdr! record value)
        (set-cdr! table
                  (cons (cons key value)
                        (cdr table)))))
  'ok)

(define (make-table)
  (list '*table*))

;;;从二维表格中查找key对应的value
(define (lookup-dou key-1 key-2 table)
  (let ([subtable (assoc key-1 (cdr table))])
    (if subtable
        (let ([record (assoc key-2 (cdr subtable))])
          (if record
              (cdr record)
              #f))
        #f)))


;;;向二维数组插入元素
(define (insert-dou! key-1 key-2 value table)
  (let ([subtable (assoc key-1 (cdr table))])
    (if subtable
        (let ([record (assoc key-2 (cdr table))])
          (if record
              (set-cdr! record value)
              (set-cdr! subtable
                        (cons (cons key-2 value)
                              (cdr subtable)))))
        (set-cdr! table
                  (cons (list key-1
                              (cons key-2 value))
                        (cdr table)))))
  'ok)
