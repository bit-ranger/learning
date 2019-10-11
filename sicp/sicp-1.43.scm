

(load "sicp-1.42.scm")

(define (>= a b)
  (or (> a b)
      (= a b)))

;;;重复调用函数，形成连调
;f必须满足条件：f返回值可以作为f的参数
(define (repeated f n)
  (define (iter count product)
    (if (>= count n)
        product
        (iter (+ count 1) (compose f product))))
  (lambda (x) ((iter 1 f) x)))

;;;辅助理解的说明：
;最终函数的形状如：
;按照compose步骤：
;1 将f代入g
;(lambda (x) (f (g x))) ==> (lambda (x) (f
;                                        (f
;                                         x)))
;2 将1步的结果代入g ，可以看到，g被代换成了一个lambda，后面紧跟参数列表
;(lambda (x) (f (g x))) ==> (lambda (x) (f
;                                        ((lambda (x)
;                                           (f
;                                            (f
;                                             x)))
;                                         x)))
;3 将2步的结果代入g
;(lambda (x) (f (g x))) ==> (lambda (x) (f
;                                        ((lambda (x)
;                                           (f
;                                            ((lambda (x)
;                                               (f
;                                                (f
;                                                 x)))
;                                             x)))
;                                         x)))

;等等，其实f的真实形态也是lambda
;另外，从x的传递过程来看，这个x只有最内层的原始函数用到了，包在外层的函数的参数都是内层函数的返回值。

;((repeated square  2) 5)