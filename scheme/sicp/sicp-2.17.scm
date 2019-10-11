

(load "list.scm")

;;;获取表最后一个元素
(define (last-pair items)
  (if (null? items)
      (error "list is empty!")
      (let ([next (cdr items)])
        (if (null? next)
            (cons (car items) next)
            (last-pair next)))))

;;;方法2，list.scm中的length函数可以获取长度，list-ref可以按索引访问。


