#lang scheme

(define input-prompt ";;; Query input: ")
(define output-prompt ";;; Query results: ")

(define (query-driver-loop)
  (prompt-for-input input-prompt)
  (let ((q (query-syntax-process (read))))                ;查询语法过程
    (cond ((assertion-to-be-added? q)                     ;断言
           (add-rule-or-assertion! (add-assertion-body q))
           (newline)
           (display "Assertion added to data base")
           (query-driver-loop))
          (else
           (newline)
           (display output-prompt)
           (display-stream
            (stream-map (lambda (frame)
                          (instantiate q
                            frame
                            (lambda (v f)
                              (contract-question-mark v))))
                        (qeval q (singleton-stream '()))))
           (query-driver-loop)))))


(define (instantiate exp frame unbound-var-handler)
  ;深拷贝表达式
  (define (copy exp)
    (cond ((var? exp)
           (let ((binding (bingding-in-frame exp frame)))
             (if binding
                 (copy (binding-value binding))
                 (unbound-var-handler exp frame))))
          ((pair? exp)
           (cons (copy (car exp))
                 (copy (cdr exp))))
          (else exp)))
  (copy exp))

(define (qeval query frame-stream)
  (let ((qproc (get (type query) 'qeval)))
    (if qproc                                     ;任何无法识别为特殊形式的查询，都假定为一个简单查询
        (qproc (contents query frame-stream))
        (simple-query query frame-stream))))


(define (simple-query query-pattern frame-stream)
  (stream-flatmap
   (lambda (frame)
     (stream-append-delayed
      (find-assertions query-pattern frame)       ;模式匹配
      (delay (apply-rules query-pattern frame)))) ;应用规则
   frame-stream))



;and
(define (conjoin conjuncts frame-stream)
  (if (empty-conjuntion? conjuncts)
      frame-stream
      (conjoin (rest-conjuncts conjuncts)
               (qeval (first-conjuncts conjuncts)
                      frame-stream))))
(put 'and 'qeval conjoin)


;or
(define (disjoin disjuncts frame-stream)
  (if (empty-disjunction? disjuncts)
      the-empty-stream
      (interleave-delayed                             ;归并
       (qeval (first-disjunct disjuncts) frame-stream)
       (delay (disjoin (rest-disjuncts disjuncts)
                       frame-stream)))))
(put 'or 'qeval disjoin)


;not
(define (negate operands frame-stream)
  (stream-flatmap
   (lambda (frame)
     (if (stream-null? (qeval (negated-query operands)
                              (singleton-stream frame)))
         (singleton-stream frame)
         the-empty-stream))
   frame-strteam))
(put 'not 'qeval negate)


(define (lisp-value call frame-stream)
  (stream-flatmap
   (lambda (frame)
     (if (execute
          (instantiate
            call
            frame
            (lambda (v f)
              (error "Unknown pat var -- LISP-VALUE" v))))
         (singleton-stream frame)
         the-empty-stream))
   frame-stream))
(put 'list-value 'qeval lisp-value)


;执行
(define (execute exp)
  (apply (eval (predicate exp)
               user-initial-environment)
         (args exp)))


(define (always-true ignore frame-stream)
  frame-stream)
(put 'always-true 'qeval always-true)


(define (find-assertions pattern frame)
  (stream-flatmap (lambda (datum)
                    (check-an-assertion datum pattern frame))
                  (fetch-assertions pattern frame)))


(define (check-an-assertion assertion query-pat query-frame)
  (let ((match-result
         (patterm-match query-pat assertion query-frame)))
    (if (eq? match-result 'failed)
        the-empty-stream
        (singleton-stream match-result))))


(define (pattern-match pat dat frame)
  (cond ((eq? frame 'failed) 'failed)
        ((equal? pat dat) frame)
        ((var? pat) (extend-if-consistent pat dat frame))
        ((and (pair? pat) (pair? dat))
         (pattern-match (cdr pat)
                        (cdr dat)
                        (pattern-match (car pat)
                                       (car dat)
                                       frame)))
        (else 'failed)))

(define (extend-if-consistent var dat frame)
  (let ((binding (binding-in-frame var frame)))
    (if binding
        (pattern-match (binding-value binding) dat frame)
        (entend var dat frame))))


















