#lang planet neil/sicp

(define (make-table)
  ;创建一个初始表，注意这是一个list
  ;初始列表的car无关，这个序对作为table的入口，
  ;如果没有这个序对，那么每次添加的新序对都会成为list头，
  ;那么已绑定的指针将指向list后面的序对
  (let ([local-table (list '*table*)])

    (define (lookup key1 key2)
      (let ([subtable (assoc key1 (cdr local-table))])
        (if subtable
            (let ([record (assoc key2 (cdr subtable))])
              (if record
                  (cdr record)
                  #f))
            #f)))

    (define (insert! key1 key2 value)
      (let ([subtable (assoc key1 (cdr local-table))])
        (if subtable
            ;record是个序对
            (let ([record (assoc key2 (cdr subtable))])
              (if record
                  (set-cdr! record value)
                  (set-cdr! subtable
                            (cons (cons key2 value)
                                  (cdr subtable)))))
            (set-cdr! local-table
                      ;一个新的序对，car部分为一个新list，cdr部位为table
                      (cons (list key1
                                  (cons key2 value))
                            (cdr local-table)))))
      'ok)

    (define (dispatch m)
      (cond [(eq? m 'lookup-proc) lookup]
            [(eq? m 'insert-proc!) insert!]
            [else (error "Unknown opertion -- TABLE"
                         m)]))

    dispatch))

(define operation-table (make-table))
(define get (operation-table 'lookup-proc))
(define put (operation-table 'insert-proc!))
