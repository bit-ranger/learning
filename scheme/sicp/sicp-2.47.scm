;#lang scheme/load

(load "sicp-2.46.scm")


;;;;;;;;;;;;;;;;;;
;创建框架
;origin 框架原点向量
;edge1 框架角向量1 ， 可被看做位于框架坐标系 x轴
;edge2 框架角向量2 ， 可被看做位于框架坐标系 y轴
;此处向量即坐标,用三个坐标可以表示一个矩形
(define (make-frame orgin edge1 edge2)
  (list origin edg1 edg2))

(define (origin-frame frame)
  (list-ref frame 0))

(define (edge1-frame frame)
  (list-ref frame 1))

(define (edge2-frame frame)
  (list-ref frame 2))

;;;;;;;;;;;;;;;;;;;;
(define (make-frame origin edge1 edge2)
  (cons origin (cons edge1 edge2)))

(define (origin-frame frame)
  (car frame))

(define (edge1-frame frame)
  (cadr frame))

(define (edge2-frame frame)
  (cddr frame))