;#lang scheme/load

(load "square.scm")


;;;过滤器
;predicate 过滤行为
;sequence 序列
(define (filter predicate sequence)
  (cond [(null? sequence) sequence]
        [(predicate (car sequence))
         (cons (car sequence) ;满足要求的用cons连接
               (filter predicate (cdr sequence)))]
        [else (filter predicate (cdr sequence))]))


;;;映射
(define (map proc sequence)
  (if (null? sequence)
      sequence
      (cons (proc (car sequence))
            (map proc (cdr sequence)))))

;;;累积器
;op 累积行为
;initial 初始数据
;sequence 序列
(define (accumulate op initial sequence)
  (if (null? sequence) initial
      (op (car sequence) ;递归累积
          (accumulate op initial (cdr sequence)))))

;;;区间枚举，即序列生成器
(define (enum-interval low high)
  (if (> low high)
      '()
      (cons low (enum-interval (+ low 1) high))))

;;;枚举树叶
(define (enum-tree tree)
  (cond [(null? tree) tree]
        [(not (pair? tree)) (list tree)]
        [else (append (enum-tree (car tree))
                      (enum-tree (cdr tree)))]))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;计算奇数树叶的平方和
(define (sum-odd-squares tree)
  (accumulate +
              0
              (map square
                   (filter odd?
                           (enum-tree tree)))))


;;;将斐波那契数列中的偶数打包成列表
(define (even-fibs n)
  (accumulate cons
              '()
              (filter even?
                      (map fib
                           (enum-interval 0 n)))))

