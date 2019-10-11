#lang scheme

(define rand
    (let ((x 0)
          (update (lambda (x)
                    (remainder (+ 65537 (* x 1027)) 1048577))))
        (lambda (op)
            (cond
                ((eq? op 'generate)
                    (set! x (update x))
                    x)
                ((eq? op 'reset)
                    (lambda (new-value)
                        (set! x new-value)))
                (else
                    (error "unknown operation"))))))

