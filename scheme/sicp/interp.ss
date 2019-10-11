#lang scheme

;(require (planet neil/sicp))

(define (eval exp env)
  (cond [(self-evaluating? exp) exp]                                                  ; 字面量
        [(variable? exp) (lookup-variable-value exp env)]                             ; 变量
        [(quoted? exp) (text-of-quotation exp)]                                       ; 符号
        [(assignment? exp) (eval-assignment exp env)]                                 ; 赋值
        [(definition? exp) (eval-definition exp env)]                                 ; 定义
        [(if? exp) (eval-if exp env)]                                                 ; if 
        [(lambda? exp) (make-procedure (lambda-parameters exp)                        ; lambda 
                                       (lambda-body exp)         
                                       env)]
        [(begin? exp) (eval-sequence (begin-actions exp) env)]                        ; begin
        [(cond? exp) (eval (cond->if exp) env)]                                       ; cond
        [(application? exp)                                                           ; 调用
         (apply (eval (operator exp) env)
                (list-of-values (operands exp) env))]
        [else (error "Unknown expression type -- EVAL" exp)]))


(define (apply procedure arguments)
  (cond [(primitive-procedure? procedure)                                             ; 基本过程
         (apply-primitive-procedure procedure arguments)]
        [(compound-procedure? procedure)                                              ; 一般过程
         (eval-sequence (procedure-body procedure)
                        (extend-environment (procedure-parameters procedure)
                                            arguments
                                            (procedure-environment procedure)))]
        [else (error "")]))


;处理if语句
(define (eval-if exp env)
  (if (true? (eval (if-predicate exp) env))
      (eval (if-consequent exp) env)
      (eval (if-alternative exp) env)))

;处理表达式序列
(define (eval-sequence exps env)
  (cond [(last-exp? exps) (eval (first-exp exps) env)]
        [else (eval (first-exp exps) env)
              (eval-sequence (rest-exps exps) env)]))

;处理赋值表达式
(define (eval-assignment exp env)
  (set-variable-value! (assignment-variable exp)
                       (eval (assignment-value exp) env)
                       env)
  'ok)

;处理定义表达式
(define (eval-definition exp env)
  (define-variable! (definition-variable exp)
                    (eval (definiton-value exp) env)
                    env)
  'ok)


;求参数列表的值
(define (list-of-values exps env)
  (if (null? exps)
      exps
      (cons (eval (car exps) env)
            (list-of-values (cdr exps) env))))


;是否字面量？
(define (self-evaluating? exp)
  (cond [(number? exp) true]
        [(string? exp) true]
        [else false]))

;是否变量？
(define (variable? exp)
  (symbol? exp))

;是否符号？
(define (quoted? exp)
  (tagged-list? exp 'quote))

;符号内容
(define (text-of-quotation exp)
  (cadr exp))

;判断exp是否以tag开头
(define (tagged-list? exp tag)
  (if (pair? exp)
      (eq? (car exp) tag)
      false))

;是否赋值？
(define (assignment? exp)
  (tagged-list? exp 'set!))

;取出赋值表达式的变量名
(define (assignment-variable exp)
  (cadr exp))

;取出赋值表达式的值表达式
(define (assignment-value exp)
  (caddr exp))

;是否定义？
(define (definition? exp)
  (tagged-list? exp 'define))

;定义的变量名
(define (definition-variable exp)
  (if (symbol? (cadr exp))
      (cadr exp)                                                                      ;直接变量符号
      (caadr exp)))                                                                   ;标准过程定义

(define (definition-value exp)
  (if (symbol? (car exp))
      (caddr exp)
      (make-lambda (cdadr exp)                                                        ;参数列表
                   (cddr exp))))                                                      ;过程体


;是否为lambda？
(define (lambda? exp)
  (tagged-list? exp 'lambda))

;lambda 参数列表
(define (lambda-parameters exp)
  (cadr exp))

;lambda过程体
(define (lambda-body exp)
  (cddr exp))

;构造lambda
(define (make-lambda parameters body)
  (cons 'lambda (cons parameters body)))


;是否为if?
(define (if? exp)
  (tagged-list? exp 'if))

;if 谓词
(define (if-predicate exp)
  (cadr exp))

;if 推论
(define (if-consequent exp)
  (cddr exp))

;if 替代(可或缺)
(define (if-alternative exp)
  (if (not (null? (cdddr exp)))
      (cadddr exp)
      'false))

;构造if
(define (make-if predicate consequent alternative)
  (list 'if predicate consequent alternative))


;是否begin?
(define (begin? exp)
  (tagged-list? exp 'begin))

;操作序列
(define (begin-actions exp)
  (cdr exp))

;是否最后一个？
(define (last-exp? seq)
  (null? (cdr seq)))

;取序列第一个
(define (first-exp seq)
  (car seq))

;剩余的序列
(define (rest-exp seq)
  (cdr seq))

;构造begin
(define (make-begin seq)
  (cons 'begin seq))

;序列转表达式
(define (sequence->exp seq)
  (cond [(null? seq) seq]
        [(last-exp? seq) (first-exp seq)]
        [else (make-begin seq)]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;是否为调用？
(define (application? exp)
  (pair? exp))

;表达式操作部分
(define (operator exp)
  (car exp))

;操作数
(define (operands exp)
  (cdr exp))

;没有操作数?
(define (no-operands? ops)
  (null? ops))

;第一个操作数
(define (first-operand ops)
  (car ops))

;剩余的操作数
(define (rest-operands ops)
  (cdr ops))


;是否为cond?
(define (cond? exp)
  (tagged-list? exp 'cond))

;cond语句序列
(define (cond-clauses exp)
  (cdr exp))

;是否为else子句？
(define (cond-else-clauses? clause)
  (eq? (cond-predicate clause) 'else))

;子句的谓词
(define (cond-predicate clause)
  (car clause))

;子句的推论
(define (cond-actions clause)
  (cdr clause))

;cond转成if
(define (cond->if exp)
  (expand-clauses (cond-clauses exp)))

;展开cond语句序列
(define (expand-clauses clauses)
  (if (null? clauses)
      'false
      (let ([first (car clauses)]
            [rest  (cdr clauses)])
        (if (cond-else-clause? first)                                                 ; 当为else子句时,将子句推论转成begin
            (if (null? rest)
                (sequence->exp (cond-actions first))
                (error "ELSE clause is not last -- COND->IF"
                       clauses))
            (make-if (cond-predicate first)                                           ; 将子句转成if,默认行为转成下一个子句的展开
                     (sequence->exp (cond-actions first))
                     (expand-clauses rest))))))














































