;#lang scheme

;对n=0,list-ref 返回表的car
;否则 ,list-ref 返回表的cdr的第(n-1)个项
;;;访问嵌套结构第N层的典型方法
(define (list-ref items n)
  (if (= n 0)
      (car items)
      (list-ref (cdr items) (- n 1))))

;;;表长度
;;;任意一个表的length就是这个表的cdr的length加1
;;;空表length为0
(define (lenght items)
  (if (null? items)
      0
      (+ 1 (length (cdr items)))))

;;;将list2拼接到list1后面
;;;如果list1为null,则拼接结果为list2
;;;否则list1的cdr拼接list2,
;然后通过cons在拼接结果之前加上list1的car
(define (append list1 list2)
  (if (null? list1)
      list2
      (cons (car list1) (append (cdr list1) list2))))

;;;缩放
(define (scale-list items factor)
  (map (lambda (x) (* x factor)) items))

;;;映射
(define (map proc items)
  (if (null? items)
      items
      (cons (proc (car items))
            (map proc (cdr items)))))

;;;统计树叶的数量
(define (count-leaves items)
  (cond [(null? items) 0]
        [(not (pair? items)) 1]
        [else (+ (count-leaves (car items))
                 (count-leaves (cdr items)))]))

;;;递归缩放
;(define (scale-tree tree factor)
;  (cond [(null? tree) tree]
;        [(not (pair? tree)) (* tree factor)]
;        [else (cons (scale-tree (car tree) factor)
;                    (scale-tree (cdr tree) factor))]))


;;;利用map
(define (scale-tree tree factor)
  (map (lambda (sub)
         (if (pair? sub)
             (scale-tree sub factor)
             (* sub factor)))
       tree))