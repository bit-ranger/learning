#lang scheme/load

(load "sicp-2.29.scm")

;;;创建活动体
(define (make-mobile left right)
  (cons left right))

;;;创建分支
(define (make-branch length structure)
  (cons length structure))

;;;;;;;;;;;;;;;;;;;;;;;;;a)
;左分支
(define (left-branch mobile)
  (car mobile))

;右分支
(define (right-branch mobile)
  (cdr mobile))

;分支长度
(define (branch-length branch)
  (car branch))

;分支挂件
(define (branch-structure branch)
  (cdr branch))

