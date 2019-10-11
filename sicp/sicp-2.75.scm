#lang scheme


(define (make-from-real-imag x y)
  (define (dispatch op)
    (cond [(eq? op 'real-part) x]
          [(eq? op 'imag-part) y]
          [(eq? op 'magnitude) (sqrt (+ (* x x) (* y y)))]
          [(eq? op 'angle) (atan y x)]
          [else (error "Unknow op -- MAKE-FROM-REAL-IMAG" op)]))
  dispatch)

(define (make-from-mag-ang r a)
  (define (dispatch op)
    (cond [(eq? op 'magnitude) r]
          [(eq? op 'angle) a]
          [(eq? op 'real-part) (* r (cos a))]
          [(eq? op 'imag-part) (* r (sin a))]
          [else (error "Unknow op -- MAKE-FROM-MAG-ANG" op)]))
  dispatch)