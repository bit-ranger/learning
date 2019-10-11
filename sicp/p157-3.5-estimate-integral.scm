#lang scheme/load

(load "p156-monte-carlo.scm")

(define (estimate-integral p? x1 x2 y1 y2 trials)
  (* 4
     (monte-carlo trials
                  (lambda ()
                    (p? (random-in-range x1 x2)
                        (random-in-range y1 y2))))))


(define (get-pi trials)
  (exact->inexact (estimate-integral (lambda (x y)
                                      (< (+ (* x x)
                                            (* y y))
                                         1))
                                    -1
                                    1
                                    -1
                                    1
                                    trials)))

;;;在范围内产生随机数，只接受int类型
(define (random-in-range low high)
  (let ([range (* 100 (- high low))])
    (exact->inexact (+ low (/ (random range) 100)))))