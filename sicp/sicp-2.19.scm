

(load "sicp-2.18.scm")


;;;换零钱的方法总数
;;;等于不用第一种硬币的方法数+使用第一种硬币的方法数
(define (cc amount coins)
  (cond [(= amount 0) 1]
        [(or (< amount 0) (no-more? coins)) 0]
        [else (+ (cc amount ;不使用第一种硬币
                     (except-first-denomination coins))
                 ;使用第一种硬币，通过总额减去第一种硬币的币值实现
                 (cc (- amount (first-denomination coins)) 
                     coins))]))

(define (no-more? items)
  (null? items))

(define (except-first-denomination items)
  (cdr items))

(define (first-denomination items)
  (car items))

;;;coins的顺序不影响结果
(define count-change
  (cc 100 (reverse (list 50 25 10 5 1))))