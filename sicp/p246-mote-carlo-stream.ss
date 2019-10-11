#lang scheme

(require (planet neil/sicp))

(require (file "p223-stream.ss"))

(require (file "p228-implicit-stream.ss"))

(define (rand-update x)
  (remainder (+ (* 13 x) 5) 24))

(define random-init (rand-update (expt 2 32)))

;蒙特卡罗流
(define (monte-carlo experiment-stream passed failed)
  (define (next passed failed)
    (cons-stream (/ passed (+ passed failed))
                 (monte-carlo (stream-cdr experiment-stream)
                              passed
                              failed)))
  (if (stream-car experiment-stream)
      (next (+ passed 1) failed)
      (next passed (+ failed 1))))

;从s流中两两取值并应用到f上,形成新的流
(define (map-successive-pairs f s)
  (cons-stream (f (stream-car s)
                  (stream-car (stream-cdr s)))
               (map-successive-pairs f
                                     (stream-cdr (stream-cdr s)))))

;随机数流
(define random-numbers
  (cons-stream random-init
               (stream-map rand-update random-numbers)))

;布尔流
(define cesaro-stream
  (map-successive-pairs (lambda (r1 r2)
                          (= (gcd r1 r2) 1))
                        random-numbers))







(define pi
  (stream-map (lambda (p)
                (sqrt (/ 6 p)))
              (monte-carlo cesaro-stream 0 0)))