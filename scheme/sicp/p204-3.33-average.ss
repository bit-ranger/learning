#lang scheme

(require (planet neil/sicp))

(require (file "p204-constraint-system.ss"))


(define (average a b)
  (let ([ac (make-connector)]
        [bc (make-connector)]
        [sc (make-connector)]
        [dc (make-connector)]
        [avgc (make-connector)])
    (adder ac bc sc)
    (multiplier dc avgc sc)
    (constant 2 dc)
    (constant a ac)
    (constant b bc)
    (get-value avgc)))

