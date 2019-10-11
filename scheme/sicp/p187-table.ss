#lang planet neil/sicp

(define (make-table)
  ;创建一个初始表，注意这是一个list
  ;初始列表的car无关，这个序对作为table的入口，
  ;如果没有这个序对，那么每次添加的新序对都会成为list头，
  ;那么已绑定的指针将指向list后面的序对
  (let ([local-table (list '*table*)])

    (define (lookup table cur . rem)
          (let ([record (assoc cur (cdr table))])
            (if record
                (if (null? rem)
                    (cdr record)
                    (if (null? (cdr record))
                        #f
                        ;如果记录体中的car不是序对，表示record不是一个子表也不是一条记录,而是单纯的数据。
                        (if (pair? (cadr record)) 
                            (apply lookup record rem)
                            #f)))
                #f)))

    
    (define (insert! table value  cur . rem)
      (let ([record (assoc cur (cdr table))])
        (cond [record
               (cond [(null? rem)
                      (set-cdr! record value)
                      table]
                     [else
                      ;;;如果有记录，且不是列表，表示值需要覆盖为列表
                      (if (not (list? record))
                          (set-cdr! record '()))
                      (apply insert! record value rem)
                      table])]
              [else
               (cond [(null? rem)
                      (join table (cons cur value))
                      table]
                     [else
                      (join table (apply insert!
                                         (make-subtable cur)
                                         value
                                         rem))
                      table])])))

    (define (join table record)
      (set-cdr! table
                (cons record
                      (cdr table))))

    (define (make-subtable name)
      (list name))

    (define (lookup-proc cur . rem)
      (apply lookup local-table cur rem))

    (define (insert-proc! value cur . rem)
      (apply insert! local-table value cur rem))

    
    (define (dispatch m)
      (cond [(eq? m 'lookup-proc) lookup-proc]
            [(eq? m 'insert-proc!) insert-proc!]
            [else (error "Unknown opertion -- TABLE"
                         m)]))

    dispatch))

(define operation-table (make-table))
(define get (operation-table 'lookup-proc))
(define put (operation-table 'insert-proc!))


;;;对函数用查表法进行优化
;注意函数内部不能有状态
(define (memoize f)
  (let ([table (make-table)])
    (lambda (x)
      (let ([prev ((table 'lookup-proc) x)])
        (or prev
            (let ([rst (f x)])
              ((table 'insert-proc!) rst x)
              rst))))))