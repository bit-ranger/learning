#lang scheme/load

(load "p154-3.3-make-account.scm")


(define (make-joint account password joint-password)
  (define (dispatch pwd msg)
    (if (eq? joint-password pwd)
        (account password msg)
        (error "Incorrect password")))
  dispatch)

;;;test

;(define jack-acc (make-account 100 'jack-password))

;(define peter-acc (make-joint jack-acc 'jack-password 'peter-password))

;((jack-acc 'jack-password 'withdraw) 0)