
(load "square.scm")

;;;创建线段
(define (make-segment start end)
  (cons start end))

;;;起点
(define (start-segment segment)
  (car segment))


;;;终点
(define (end-segment segment)
  (cdr segment))

;;;点
(define (make-point x y)
  (cons x y))

;;;点x
(define (x-point point)
  (car point))

;;;点y
(define (y-point point)
  (cdr point))

;;;打印点
(define (print-point p)
  (newline)
  (display "(")
  (display (x-point p))
  (display ",")
  (display (y-point p))
  (display ")"))


(define (midpoint-segment segment)
  (make-point (/ (+ (x-point (start-segment segment))
                    (x-point (end-segment segment)))
                 2)
              (/ (+ (y-point (start-segment segment))
                    (y-point (end-segment segment)))
                 2)))

;;;线段长度
(define (length-segment segment)
  (let ([end (end-segment segment)]
        [start (start-segment segment)])
    (sqrt (+ (square (- (x-point end) (x-point start)))
             (square (- (y-point end) (y-point start)))))))


;;;(print-point (midpoint-segment (make-segment  (make-point 0 0) (make-point 3 3))))