

;;;1.37
;http://sicp.readthedocs.org/en/latest/chp1/37.html

;a)
(define (cont-frac N D k)
  (define (cf i)
    (if (= k i)
        (/ (N k) (D k))
        (/ (N i)
           (+ (D i) (cf (+ i 1))))))
  (cf 1))


;;;求黄金分割率
(define (theta k)
  (+ 1
     (cont-frac (lambda (i) 1.0)
             (lambda (i) 1.0)
             k)))