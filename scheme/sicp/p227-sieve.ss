#lang scheme

(require (planet neil/sicp))

(require (file "p223-stream.ss"))



(define (sieve stream)
  (cons-stream (stream-car stream)
               ;从给定流的第二个元素进行筛选
               ;若第一个元素能整除流中后续元素，这些后续元素必定不是素数，忽略。
               ;当碰到不能整除的数时，这个数必为素数，收集起来，将此时的流传递给sieve
               (let ([head (stream-car stream)]
                     [next (stream-cdr stream)])
                 ;stream-filter返回的是一个新流，这个流的car为第一个满足条件的元素，cdr是继续筛选的承诺，
                 ;这个承诺中闭包了两样东西，1是最开始传入的流，2是最开始判断的条件,
                 ;这个承诺一旦启用，stream-filter将用这个闭包中的对象作为参数执行,
                 ;在本程序中，sieve从不主动启用此过滤器的cdr，而只是将启用的的操作封装进一个新的流中作为cdr，
                 ;并同时将新的参数流及判断条件闭包进去。
                 ;所以在sieve返回的流执行cdr时，将会调用内部被封装的承诺，直到嵌套的最底层。
                 ;其中，每个承诺都只产生刚刚满足自身筛选条件第一个元素，及其继续筛选的承诺。
                 ;这个程序的重点是:1，stream-filter会闭包参数，2，sieve对stream-filter的调用是惰性的，基于这两点形成了嵌套的过滤链。
                 ;必须深刻理解一点，cons-stream每次执行都会生成一个新的函数对象
                 ;这个函数对象中每次执行都会生成一个新环境
                 (sieve (stream-filter (lambda (x)
                                         (not (divisiable? x head)))
                                       next)))))


(define (divisiable? x y)
  (= (remainder x y) 0))

(define primes (sieve (integers-starting-from 2)))

;(stream-ref primes 50)