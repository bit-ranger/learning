;#lang scheme

;;;创建叶节点
(define (make-leaf symbol weight)
  (list 'leaf symbol weight))

;;;是否叶节点
(define (leaf? object)
  (eq? (car object) 'leaf))

;;;叶节点符号
(define (symbol-leaf x)
  (cadr x))

;;;叶节点权重
(define (weight-leaf x)
  (caddr x))


;;;左子树
(define (left-branch tree)
  (car tree))

;;;右子树
(define (right-branch tree)
  (cadr tree))

;;;树的权重
(define (weight tree)
  (if (leaf? tree)
      (weight-leaf tree)
      (cadddr tree)))

;;;树的符号集合
(define (symbols tree)
  (if (leaf? tree)
      (list (symbol-leaf tree))
      (caddr tree)))

;;;创建编码树
(define (make-code-tree left right)
  (list left
        right
        (append (symbols left) (symbols right))
        (+ (weight left) (weight right))))


;;;将元素插入集合，按权重排序
(define (adjoin-set x set)
  (cond [(null? set) (list x)]
        [(< (weight x) (weight (car set))) (cons x set)]
        [else (cons (car set)
                    (adjoin-set x (cdr set)))]))

;;;构建初始树叶集合
(define (make-leaf-set pairs)
  (if (null? pairs)
      '()
      (let ([pair (car pairs)])
        (adjoin-set (make-leaf (car pair)
                               (cadr pair))
                    (make-leaf-set (cdr pairs))))))

;;;由对偶表生成哈夫曼树
(define (generate-huffman-tree pairs)
  (successive-merge (make-leaf-set pairs)))

;;;合并节点列表
(define (successive-merge sorted-set)
  (cond [(null? sorted-set) '()]
        [(null? (cdr sorted-set)) (car sorted-set)]
        [else (successive-merge
               (adjoin-set (make-code-tree (car sorted-set) (cadr sorted-set))
                           (cddr sorted-set)))]))

;;;解码
(define (decode bits tree)
  (define (decode-1 bits current-branch)
    (if (null? bits)
        '()
        (let ([next-branch
               (choose-branch (car bits) current-branch)])
          (if (leaf? next-branch)
;下一个元素为叶节点，表示二进制序列映射到了符号，后面的二进制序列从根开始匹配。
              (cons (symbol-leaf next-branch)
                    (decode-1 (cdr bits) tree))
              (decode-1 (cdr bits) next-branch)))))
  (decode-1 bits tree))

;;;选择分支
(define (choose-branch bit branch)
  (cond [(= bit 0) (left-branch branch)]
        [(= bit 1) (right-branch branch)]
        [else (error "bad bit -- CHOOSE-BRANCH" bit)]))

;;;编码
(define (encode message tree)
  (if (null? message)
      '()
      (append (encode-symbol (car message) tree)
              (encode (cdr message) tree))))

;;;对一个符号进行编码
(define (encode-symbol symbol tree)
  (cond [(leaf? tree) '()]
        [(symbol-in-tree? symbol (left-branch tree))
         (cons 0 (encode-symbol symbol (left-branch tree)))]
        [(symbol-in-tree? symbol (right-branch tree))
         (cons 1 (encode-symbol symbol (right-branch tree)))]
        [else (error "bad symbol -- ENCODE-SYMBOL" symbol)]))

;;;符号是否在树中
(define (symbol-in-tree? symbol tree)
  (define (element-of-set? x set)
    (cond [(null? set) false]
          [(equal? x (car set)) true]
          [else (element-of-set? x (cdr set))]))
  (element-of-set? symbol (symbols tree)))

