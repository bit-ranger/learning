;#lang scheme/load

(load "painter.scm")

(define (make-vect x y)
  (cons x y))

(define (xcor-vect vect)
  (car vect))

(define (ycor-vect vect)
  (cdr vect))

(define (add-vect vect1 vect2)
  (make-vect (+ (car vect1) (car vect2))
             (+ (cdr vect1) (cdr vect2))))

(define (sub-vect vect1 vect2)
  (make-vect (- (car vect1) (car vect2))
             (- (cdr vect1) (cdr vect2))))

(define (scale-vect s vect)
  (make-vect (* s (car vect))
             (* s (cdr vect))))
