#lang scheme

(require (planet neil/sicp))

(define C (make-connector))

(define F (make-connector))


(celsius-fahrenheit-converter C F)

;摄氏转华氏
(define (celsius-fahrenheit-converter c f)
  (let ([u (make-connector)]
        [v (make-connector)]
        [w (make-connector)]
        [x (make-connector)]
        [y (make-connector)])
    (multiplier c w u)
    (multiplier v x u)
    (adder f y v)
    (constant 9 w)
    (constant 5 x)
    (constant 32 y)
    'ok))



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;







