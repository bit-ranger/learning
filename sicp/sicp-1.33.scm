#lang racket/load


(load "Develop/Document/doc/sicp/prime?.scm")
(load "Develop/Document/doc/sicp/gcd.scm")

;;;1.33

(define (filtered-accumulate combiner null-value term a next b valid?)
  (if (> a b)
      null-value
      (let ([rst (filtered-accumulate combiner null-value term (next a) next b valid?)])
           (if (valid? a)
               (combiner (term a) rst)
               rst))))



;;;迭代版本
(define (filtered-accumulate-2 combiner null-value term a next b valid?)
  (define (iter a p)
    (if (> a b)
        p
        (if (valid? a)
            (iter (next a) (combiner (term a) p))
            (iter (next a) p))))
  (iter a null-value))


;;;a)
;;;累加素数
(define (sum-prime a b)
  (filtered-accumulate-2 +
                         0
                         (λ (x) x)
                         a
                         (λ (x) (+ x 1))
                         b
                         prime?))

;;;b)
;;;

(define (coprime? i n)
    (and (< i n)
         (= 1 (gcd i n))))

(define (product-of-coprimes n)
  (filtered-accumulate-2 *
                         1
                         (λ (x) x)
                         1
                         (λ (x) (+ x 1))
                         n
                         (λ (x) (coprime? x n))))

