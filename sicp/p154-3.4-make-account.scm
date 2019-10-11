#lang scheme

(define (make-account balance secret-password)

  (define illegal-count 0)
  
  (define (withdraw amount)
    (if (>= balance amount)
        (begin (set! balance (- balance amount))
               balance)
        "Insufficient funds"))
  
  (define (deposit amount)
    (set! balance (+ balance amount))
    balance)

  (define (note-illegal-call)
    (set! illegal-count (+ 1 illegal-count))
    (if (>= illegal-count 7)
        (call-the-cops)
        (lambda (arg . args)
          "Incorrect password")))
  
  (define (call-the-cops)
    (error "Incorrect password"))
  
  (define (dispatch p m)
    (cond [(not (eq? p secret-password))
           (note-illegal-call)]
          [(eq? m 'withdraw) withdraw]
          [(eq? m 'deposit) deposit]
          [else (error "Unknown request -- MAKE-ACCOUNT"
                       m)]))

  dispatch)

(define acc (make-account 100 'secret))
