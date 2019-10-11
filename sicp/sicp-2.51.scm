;#lang scheme/load

(load "sicp-2.50.scm")

;;;;方法1，切割定位
(define (below p1 p2)
  (let ([split-point (make-vect 0.0 0.5)])
    (let ([paint-down (transform-painter p1
                                         (make-vect 0.0 0.0)
                                         (make-vect 1.0 0.0)
                                         split-point)]
          [paint-up (transform-painter p2
                                          split-point
                                          (make-vect 1.0 0.5)
                                          (make-vect 0.0 1.0))])
      (lambda (frame)
        (painter-up frame)
        (painter-down frame)))))


;;;;方法，beside与旋转
(define (below p1 p2)
  (lambda (frame)
    ((flip-horiz (rotate90 (beside (rotate270 (filp-horiz p1)
                                   (rotate270 (filp-horiz p2))))))
     frame)))