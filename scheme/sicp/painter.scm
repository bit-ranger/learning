;#lang scheme/load

(load "sicp-2.23.scm")

;成对反转
(define (flipped-pairs painter)
  (let ([painter2 (beside painter
                          (flip-vert painter))])
    (below painter2 painter2)))


;右切割
;将painter切成两半，并上下堆叠放在原painter右边
(define (right-split painter n)
  (if (= n 0)
      painter
      (let ([smaller (right-split painter (- n 1))])
        (beside painter (below smaller smaller)))))

;对角切割
(define (corner-split painter n)
  (if (= n 0)
      painter
      (let ([up (up-split painter (- n 1))]
            [right (right-split painter (-n 1))])
        (let ([top-left (beside up up)]
              [bottom-right (blew right right)]
              [corner (corner-split painter (- n 1))])
          (beside (below paiter top-left)
                  (below bottom-right corner))))))

;;;在四个角重复
(define (square-limit painter n)
  (let ([quarter (corner-split painter n)])
    (let ([half (beside (flip-horiz quarter))])
      (below (flip-vert half) half))))




;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;高阶操作


;;;;对painter的四角操作
(define (square-of-four tl tr bl br)
  (lambda (painter)
    (let ([top (beside (t1 painter) (tr painter))]
          [bottom (beside (bl painter) (br painter))])
      (below bottom top))))

;;;基于square-of-four定义flipped-pairs
(define (flipped-pairs painter)
  (let ([combine4 (square-of-four identity
                                  flip-vert
                                  identity
                                  flip-vert)])
    (combine4 painter)))

;;;重定义square-limit
(define (square-limit painter n)
  (let ([combine4 (square-of-four flip-horiz
                                  identity
                                  rotate180
                                  flip-vert)])
    (combine4 (corner-split painter n))))



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;框架映射函数
;计算出相对于框架的向量v在框架所在的坐标系中的位置。
(define (frame-coord-map frame)
  (lambda (v)
    (add-vect (origin-frame frame)
              (add-vect (scale-vect (xcor-vect v)
                                    (edge1-frame frame))
                        (scale-vect (ycor-vect v)
                                    (edge2-frame frame))))))



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;用过程表示画家，产生的画家过程接受一个frame参数
(define (segments->painter segment-list)
  (lambda (frame)
    (for-each (lambda (seg)
                (draw-line ((frame-coord-map frame) (start-segment seg))
                           ((frame-coord-map frame) (end-segment seg))))
              segment-list)))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;画家的变换和组合
;此函数的作用为，为给定的frame生成新的基准与角向量
(define (transform-painter painter origin corner1 corner2)
  (lambda (frame)
    (let ([m (frame-coord-map frame)])
      (let ([new-origin (m origin)])
        (painter (make-frame new-origin
                             (sub-vect (m corner1) new-origin) ;相对于新原点的角向量
                             (sub-vect (m corner2) new-origin)))))))


;利用框架变换定义垂直反转
(define (filp-vert painter)
  (transform-painter painter
                     (make-vect 0.0 1.0)
                     (make-vect 1.0 1.0)
                     (make-vect 0.0 0.0)))

;利用框架变换定义收缩到右上角
(define (shrink-to-upper-right painter)
  (transform-painter painter
                     (make-vect 0.5 0.5)
                     (make-vect 1.0 0.5)
                     (make-vect 0.5 1.0)))

;利用框架变换定义逆时针旋转90度
(define (rotate90 painter)
  (transform-painter painter
                     (make-vect 1.0 0.0)
                     (make-vect 1.0 1.0)
                     (make-vect 0.0 0.0)))

;利用框架变换定义向中心收缩
(define (squash-inwards painter)
  (transform-painter painter
                     (make-vect 0.0 0.0)
                     (make-vect 0.65 0.35)
                     (make-vect 0.35 0.65)))

;利用框架变换定义beside
(define (beside p1 p2)
  (let ([split-point (make-vect 0.5 0.0)])
    (let ([paint-left (transform-painter p1
                                         (make-vect 0.0 0.0)
                                         split-point
                                         (make-vect 0.0 1.0))]
          [paint-right (transform-painter p2
                                          split-point
                                          (make-vect 1.0 0.0)
                                          (make-vect 0.5 1.0))])
      (lambda (frame) ;;;将frame传递给内部painter,beside函数不关心具体的绘图过程
        (painter-left frame)
        (painter-right frame)))))