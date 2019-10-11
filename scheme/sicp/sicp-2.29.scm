;#lang scheme/load

(load "sicp-2.28.scm")


;;;创建活动体
(define (make-mobile left right)
  (list left right))

;;;创建分支
(define (make-branch length structure)
  (list length structure))

;;;;;;;;;;;;;;;;;;;;;;;;;a)
;左分支
(define (left-branch mobile)
  (list-ref mobile 0))

;右分支
(define (right-branch mobile)
  (list-ref mobile 1))

;分支长度
(define (branch-length branch)
  (list-ref branch 0))

;分支挂件
(define (branch-structure branch)
  (list-ref branch 1))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;b)
;是否悬挂活动体
(define (hasMobile? branch)
  (pair? (branch-structure branch)))

;分支重量
(define (branch-weight branch)
    (let ([strc (branch-structure branch)])
      (if (hasMobile? branch)
          (total-weight strc)
          strc)))

;;;活动体重量
(define (total-weight mobile)
  (+ (branch-weight (left-branch mobile))
     (branch-weight (right-branch mobile))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;c)
;力矩
(define (branch-torque branch)
  (* (branch-length branch)
     (branch-weight branch)))

;判断活动体是否平衡
(define (mobile-balance? mobile)
  (let ([left (left-branch mobile)]
        [right (right-branch mobile)])
    (and (same-torque? left right) ;力矩相等
         (branch-balance? left)    ;左分支平衡
         (branch-balance? right))));右分支平衡

;力矩相等
(define (same-torque? left right)
  (= (branch-torque left)
     (branch-torque right)))

;分支是否平衡？
(define (branch-balance? branch)
  (if (hasMobile? branch)
      (mobile-balance? (branch-structure branch))
      #t))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;d)
;需要覆盖一些函数
;移步至sicp-2.29-d.scm



;;;;;;;;;;;;;;;;;;;;;;;;
(define (test)
  (make-mobile (make-branch 1
                            (make-mobile (make-branch 1 2)
                                         (make-branch 1 2)))
               (make-branch 1
                            (make-mobile (make-branch 1 2)
                                         (make-branch 1 2)))))