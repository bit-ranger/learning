

(load "gcd.scm")




(define (make-rat n d)
  (let ([g (gcd n d)])
    (cons (/ n g) (/ d g))))

(define (number x)
  (car x))

(define (denom x)
  (cdr x))

(define (print-rat x)
  (newline)
  (display (number x))
  (display "/")
  (display (denom x)))


(define (add-rat x y)
  (make-rat (+ (* (number x) (denom y))
               (* (number y) (denom x)))
            (* (denom x) (denom y))))


(define one-half (make-rat 1 2))

;(print-rat (add-rat one-half one-half))