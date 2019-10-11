#lang planet neil/sicp

;;;lambda

;此时plu的过程体就是lambda函数本身
(define plu
  (lambda (a b)
    (+ a b)))


;注意lambda前面的括号，此时plus的过程体是对lambda函数的调用，过程参数为3
(define (plus a b)
  ((lambda (x) (+ a b x))
   3))
