#lang planet neil/sicp

(load "p187-table.ss")

(define (memoize f)
  (let ([table (make-table)])
    (lambda (x)
      (let ([prev (lookup x table)])
        (or prev
            (let ([rst (f x)])
              (insert! x rst table)
              rst))))))