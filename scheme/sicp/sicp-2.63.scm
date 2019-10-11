;#lang scheme/load

(load "set-tree.scm")

;中序遍历
(define (tree->list-1 tree)
  (if (null? tree)
      '()
      (append (tree->list-1 (left-branch tree))
              (cons (entry tree)
                    (tree->list-1 (right-branch tree))))))
;中序遍历
(define (tree->list-2 tree)
  (define (copy-to-list tree result-list)
    (if (null? tree)
        result-list
        (copy-to-list (left-branch tree)
                      (cons (entry tree)
                            (copy-to-list (right-branch tree)
                                          result-list)))))
  (copy-to-list tree '()))


;;;a)两种中序遍历对相同的树生成同样的列表
;;;b)tree->list-1 对于tree中的每个节点都要执行一次append，复杂度为O(n2)
;;;tree->list-2 对每个节点执行一个cons，复杂度为O(n)