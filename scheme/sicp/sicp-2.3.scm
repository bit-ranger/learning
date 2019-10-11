

(load "sicp-2.2.scm")
(load "sicp-1.46.scm")


;;;构造矩形
(define (make-rectangle len-seg wid-seg)
  (cons len-seg wid-seg))

;;;长度
(define (length-rectangle rectangle)
  (length-segment (car rectangle)))

;;;宽度
(define (width-rectangle rectangle)
  (length-segment (cdr rectangle)))

;;;周长
(define (perimeter-rectangle rectangle)
  (* 2
     (+ (length-rectangle rectangle)
        (width-rectangle rectangle))))

;;;面积
(define (area-rectangle rectangle)
  (* (length-rectangle rectangle)
     (width-rectangle rectangle)))

;;;测试
(define test-rec
  (make-rectangle (make-segment (make-point 0 0) (make-point 1 0))
                (make-segment (make-point 0 0) (make-point 0 1))))