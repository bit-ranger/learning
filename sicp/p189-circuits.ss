#lang scheme/load


(require (planet neil/sicp))
(load "p181-queue.ss")

;;;非门
(define (inverter input output)
  (define (invert-input)
    (let ([new-value (logical-not
                      (get-signal input))])
      (after-delay inverter-delay
                   (lambda ()
                     (set-signal! output new-value)))))
  (add-action! input invert-input)
  'ok)

(define (logical-not s)
  (cond [(= s 0) 1]
        [(= s 1) 0]
        [else (error "Invalid signal" s)]))

;;;与门
(define (and-gate a1 a2 output)
  (define (and-action-proc)
    (let ([new-value (logical-and (get-signal a1)
                                  (get-signal a2))])
      (after-delay and-gate-delay
                   (lambda ()
                     (set-signal! output new-value)))))
  (add-action! a1 and-action-proc)
  (add-action! a2 and-action-proc)
  'ok)

(define (logical-and a1 a2)
  (cond [(and (= a1 1) (= a2 1)) 1]
        [else 0]))

;;;或门
(define (or-gate o1 o2 output)
  (define (or-action-proc)
    (let ([new-value (logical-or (get-signal o1)
                                 (get-signal o2))])
      (after-delay or-gate-delay
                   (lambda ()
                     (set-signal! output new-value)))))
  (add-action! o1 or-action-proc)
  (add-action! o2 or-action-proc)
  'ok)

(define (logical-or o1 o2)
  (cond [(or (= o1 0) (= o2 0)) 1]
        [else 0]))


;;;半加器
;a 输入
;b 输入
;s 和
;c 进位
(define (half-adder a b s c)
  (let ([d (make-wire)]
        [e (make-wire)])
    (or-gate a b d)
    (and-gate a b c)
    (inverter c e)
    (and-gate d e s)
    'ok))

;;;全加器
;a 输入
;b 输入
;c-in 输入进位
;c-out 输出进位
(define (full-adder a b c-in sum c-out)
  (let ([s (make-wire)]
        [c1 (make-wire)]
        [c2 (make-wire)])
    (half-adder b c-in s c1)
    (half-adder a s sum c2)
    (or-gate c1 c2 c-out)
    'ok))

;;;级联进位加法器
(define (ripple-carry-adder A B S C)
  (define (iter A B c-in S)
    (if (and (null? A) (null? B) (null? C))
        (add-action! c-in
                     (lambda ()
                       (set-signal! C (get-signal c-in))))
        (let ([AK (car A)]
              [BK (car B)]
              [SK (car S)]
              [RA (cdr A)]
              [RB (cdr B)]
              [RS (cdr S)]
              [c-out (make-wire)])
          (full-adder AK BK c-in SK c-out)
          (iter RA RB c-out RS))))
  (define c-in (make-wire))
  (iter A B c-in S)
  (set-signal! c-in 0))

;;;导线
(define (make-wire)
  (let ([signal-value 0]
        [action-procs '()])
    (define (set-my-signal! new-value)
      (if (not (= signal-value new-value))
          (begin (set! signal-value new-value)
                 (call-each action-procs))
          'done))
    (define (accept-action-proc! proc)
      (set! action-procs (cons proc action-procs))
      (proc));立即执行一次，模拟初次连接，使信号传递。
    (define (call-each procs)
      (if (null? procs)
          'done
          (begin ((car procs))
                 (call-each (cdr procs)))))
    (define (dispatch m)
      (cond [(eq? m 'get-signal) signal-value]
            [(eq? m 'set-signal!) set-my-signal!]
            [(eq? m 'add-action!) accept-action-proc!]
            [else (error "Unkown operation -- WIRE" m)]))
    dispatch))

(define (get-signal wire)
  (wire 'get-signal))

(define (set-signal! wire value)
  ((wire 'set-signal!) value))

(define (add-action! wire action)
  ((wire 'add-action!) action))









;;;延迟执行
(define (after-delay delay action)
  (add-to-agenda! (+ delay (current-time the-agenda))
                  action
                  the-agenda))

;;;信号传播
;propagate函数模拟信号的传播与时间的流动，
;伴随事件的流动，那些设置了延迟的原件将在正确的时间输出信号，并同时更新当前时间。
;这些原件的输出又流进后面的原件作为输入，;后面的原件接收到输入信号后将延迟报告给propagate，以保证自己在正确时刻输出。
;那些拥有相同延迟但是连接在后方的原件，在注册到propagate中时，以当时的时间为基准，将获得比较晚的输出时刻。
;这样，可能延迟较短的原件已经连通了好几个了，而延迟较长的原件才有了第一个输出，总体运行时间按最长的算，这种流程就是并行计算。
(define (propagate)
  (if (empty-agenda? the-agenda)
      'done
      (let ([first-item (first-agenda-item the-agenda)])
        (first-item)
        (remove-first-agenda-item! the-agenda)
        (propagate))))

;;;监听导线
(define (probe name wire)
  (add-action! wire
               (lambda ()
                 (newline)
                 (display name)
                 (display " ")
                 (display (current-time the-agenda))
                 (display " New-value = ")
                 (display (get-signal wire)))))



;;;时刻表
;由执行时刻与事件序列组成
;拥有相同执行时刻的函数都在同一个时间段中
(define (make-time-segment time queue)
  (cons time queue))

(define (segment-time s)
  (car s))

(define (segment-queue s)
  (cdr s))


;;;;事件簿
;由当前时间与时刻表队列组成
(define (make-agenda)
  (list 0))

(define (current-time agenda)
  (car agenda))

(define (set-current-time! agenda time)
  (set-car! agenda time))

(define (segments agenda)
  (cdr agenda))

(define (set-segments! agenda segments)
  (set-cdr! agenda segments))

(define (first-segment agenda)
  (car (segments agenda)))

(define (rest-segments agenda)
  (cdr (segments agenda)))

(define (empty-agenda? agenda)
  (null? (segments agenda)))

;;;事件加入事项
;time 事件执行的时刻
;事件
;事件簿
(define (add-to-agenda! time action agenda)
  ;新时刻在队列中的每个时刻之前？
  (define (belongs-before? segments)
    (or (null? segments)
        (< time (segment-time (car segments)))))
  ;创建新的时刻表
  (define (make-new-time-segment time action)
    (let ([q (make-queue)])
      (insert-queue! q action)
      (make-time-segment time q)))
  ;将新事件加入时刻表队列
  (define (add-to-segments! segments)
    (if (= (segment-time (car segments)) time)
        (insert-queue! (segment-queue (car segments))
                       action)
        (let ([rest (cdr segments)])
          (if (belongs-before? rest)
              (set-cdr! segments
                        (cons (make-new-time-segment time action)
                              (cdr segments)))
              (add-to-segments! rest)))))
  (let ([segments (segments agenda)])
    (if (belongs-before? segments)
        (set-segments! agenda
                       (cons (make-new-time-segment time action)
                             segments))
        (add-to-segments! segments))))


;;;删除事件簿中的第一个时刻表的第一个事件
;如果使第一个时刻表变空，就顺便删除此时刻表
(define (remove-first-agenda-item! agenda)
  (let ([q (segment-queue (first-segment agenda))])
    (delete-queue! q)
    (if (empty-queue? q)
        (set-segments! agenda (rest-segments agenda)))))

;;;事件簿中的第一个元素
;同时更新当前时间
(define (first-agenda-item agenda)
  (if (empty-agenda? agenda)
      (error "Agenda is empty -- FIRST-AGENDA-ITEM")
      (let ([first-seg (first-segment agenda)])
        (set-current-time! agenda (segment-time first-seg))
        (front-queue (segment-queue first-seg)))))




(define the-agenda (make-agenda))
(define inverter-delay 2)
(define and-gate-delay 3)
(define or-gate-delay 5)


(define input-1 (make-wire))
(define input-2 (make-wire))
(define sum (make-wire))
(define carry (make-wire))
(probe 'sum sum)
(probe 'carry carry)
(half-adder input-1 input-2 sum carry)