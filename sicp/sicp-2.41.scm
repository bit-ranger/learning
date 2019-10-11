#lang scheme/load

(load "sicp-2.40.scm")

;;;利用flatmap
(define (unique-triple n)
  (flatmap (lambda (i)
             (flatmap (lambda (j)
                        (map (lambda (k)
                               (list i j k))
                             (enum-interval 1 (- j 1))))
                      (enum-interval 1 (- i 1))))
           (enum-interval 1 n)))

;;;利用unique-pairs
(define (unique-triple n)
  (flatmap (lambda (i)
             (map (lambda (j)
                        (cons i j))
                      (unique-pairs (- i 1))))
           (enum-interval 1 n)))

;;;三元组和和等于自身
(define (self-sum-triple n)
  (filter (lambda (x)
            (= n (cadddr x)))
          (map make-triple-sum
               (unique-triple n))))

;将双元素序列转成三元组，第三个元素为前两个元素的和
(define (make-triple-sum seq)
  (list (car seq)
        (cadr seq)
        (caddr seq)
        (+ (car seq)
           (cadr seq)
           (caddr seq))))
(define (self-equ? n seq)
  (= n (cadddr seq)))

