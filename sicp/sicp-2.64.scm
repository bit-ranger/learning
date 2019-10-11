;#lang scheme/load

(load "set-tree.scm")

;;;elts 列表
;;;n 有效元素个数
;;;partial-tree 返回一个序对，car为生成的树，cdr为n个元素中剩余的部分
;;;将elts递归地分解为左右子树
;;;先构建左子树，剩余的元素car为entry,用cdr构建右子树。
;;;树的构建形式为 entry left right
;;;考虑一种临街情况，n为1时，left,right都应该为null，唯一的元素作为entry.
(define (partial-tree elts n)
  (if (= n 0)
      (cons '() elts)
      (let ([left-size (quotient (- n 1) 2)])
        (let ([left-result (partial-tree elts left-size)])
          (let ([left-tree (car left-result)]
                [non-left-elts (cdr left-result)] ;当left-tree为null时，所有elts都在non-left-elts中
                [right-size (- n (+ left-size 1))]);右节点比non-left-elts少了1，因为第一个元素作为entry
            (let ([this-entry (car non-left-elts)]
                  [right-result (partial-tree (cdr non-left-elts);去除第一个元素后构建右子树，该过程隐含的分割左右就在此处。
                                              right-size)])
              (let ([right-tree (car right-result)]
                    [remaining-elts (cdr right-result)])
                (cons (make-tree this-entry left-tree right-tree)
                      remaining-elts))))))))
;;;将列表转为平衡二叉树
(define (list->tree elements)
  (car (partial-tree elements (length elements))))



;a)(1 3 5 7 9 11)形成的二叉树

;(1 3)(5 7 9 11)             ; 分割左右子树
;
;(5 7 9 11)                  ; 创建 1 节点
;    /
;   /
;1(3)
;
;   (5 7 9 11)               ; 创建 1 的左子树（空）
;      /
;     /
;   1(3)
;   /
;  /
;'()
;
;    (5 7 9 11)              ; 创建 1 的右子树（包含 3）
;      /
;     /
;    1
;   / \
;  /   \
;'()    3
;
;       5 (7 9 11)           ; 创建树根 5
;      /
;     /
;    1
;   / \
;  /   \
;'()    3
;
;       5                    ; 创建 9 节点
;      / \
;     /   \
;    1     9 (7 11)
;   / \
;  /   \
;'()    3
;
;         5                  ; 创建 9 的左子树（包含 7）
;        /\
;       /  \
;      /    \
;     /      \
;    1        9 (11)
;   / \      /
;  /   \    /
;'()    3  7
;
;         5                  ; 创建 9 的右子树（包含 11）
;        / \
;       /   \
;      /     \
;     /       \
;    1         9
;   / \       / \
;  /   \     /   \
;'()    3   7    11


;b) 列表中的每个元素都要执行一次make-tree,所以复杂度为O(n)