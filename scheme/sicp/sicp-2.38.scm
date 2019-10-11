;#lang scheme/load

(load "sicp-2.36.scm")



;;;给定一个初值，
;将列表中的所有元素从左至右累计到初值上
(define (fold-left op init seq)
  (define (iter rst rest)
    (if (null? rest)
        rst
        (iter (op rst (car rest))
              (cdr rest))))
  (iter init seq))

;;;将列表中的第一个元素累计到右边的子列表上
;;;将递归过程展开来看，初值在最右边，
;列表中的元素从右至左累计到初值上
(define fold-right accumulate)


;;;;;;;测试

(fold-right / 1 (list 1 2 3))
(fold-left / 1 (list 1 2 3))
(fold-right list '() (list 1 2 3))
(fold-left list '() (list 1 2 3))



;;;;;结论
;要让fold-left 与 fold-right 产生相同的结果，op操作必须满足结合律，
;即op操作的对象顺序无关