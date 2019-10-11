;#lang scheme

(define (make-account balance secret-password)
  
  (define (withdraw amount)
    (if (>= balance amount)
        (begin (set! balance (- balance amount))
               balance)
        "Insufficient funds"))
  
  (define (deposit amount)
    (set! balance (+ balance amount))
    balance)
  
  (define (dispatch p m)
    (cond [(not (eq? p secret-password))
           (error "Incorrect password")]
          [(eq? m 'withdraw) withdraw]
          [(eq? m 'deposit) deposit]
          [else (error "Unknown request -- MAKE-ACCOUNT"
                       m)]))

  dispatch)

(define acc (make-account 100 'secret))
