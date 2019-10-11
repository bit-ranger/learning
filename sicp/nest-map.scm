#lang scheme/load

(load "sequence.scm")
(load "prime?.scm")

;;;生成1~n的序列
;对这个序列中的每个元素i,
;都执行映射，生成一个对于i的序列 1~(i-1)
;对于这个序列中的每个元素j,都将 i,j打包成列表。
;这样一来，对于初始序列中的每个元素i,都产生i-1个双元素小序列,
;这种小序列的集合，为双元素的所有组合形式。
;(accumulate append
;            '()
;            (map (lambda (i)
;                   (map (lambda (j)
;                          (list i j))
;                        (enum-interval 1 (- i 1))))
;                 (enum-interval 1 n)))


;将映射应用于多个列表，并将映射结果合并为一个列表
(define (flatmap proc seq)
  (accumulate append '() (map proc seq)))

;序列的累加是否为素数
(define (prime-sum? pair)
  (prime? (+ (car pair)
             (cadr pair))))

;将双元素序列转成三元组，第三个元素为前两个元素的和
(define (make-pair-sum pair)
  (list (car pair)
        (cadr pair)
        (+ (car pair)
           (cadr pair))))


;;;完整逻辑
(define (prime-sum-pairs n)
  (map make-pair-sum
       (filter prime-sum?
               (flatmap (lambda (i)
                          (map (lambda (j)
                                 (list i j))
                               (enum-interval 1 (- i 1))))
                        (enum-interval 1 n)))))

;;;对集合枚举所有可能的排列
;此过程思想十分接近于换零钱
;将集合S的全部排列方式递归地表示为：
;去除自身后的所有组合再拼接自身
;并将此过程应用在集合S的每个元素上
(define (permutations s)
  (if (null? s)
      (list '())
      (flatmap (lambda (x)
                 (map (lambda (p)
                        (cons x p))
                      (permutations (remove x s))))
               s)))

;从列表中删除一个元素
(define (remove item sequence)
  (filter (lambda (x)
            (not (= x item)))
          sequence))