#lang scheme
(require (planet neil/sicp))


;;;;机器
;register-names 寄存器名列表
;ops 操作列表
;controller-text 控制器文本
(define (make-machine register-names ops controller-text)
  (let ((machine (make-new-machine)))
    (for-each (lambda (register-name)
                ((machine 'allocate-register) register-name)))
    ((machine 'install-operations) ops)
    ((machine 'install-instruction-sequence)
     (assemble controller-text machine))
    machine))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;;;寄存器
;name 名称
(define (make-register name)
  (let ((contents '*unassigned*))
    (define (dispatch m)
      (cond ((eq? m 'get)
             contents)
            ((eq? m 'set)
             (lambda (value)
               (set! contents value)))
            (else (error "Unknown request -- REGISTER"
                         m))))
    dispatch))

;;;获取寄存器内容
(define (get-contents register)
  (register 'get))

;;;设置寄存器内容
(define (set-contents! register value)
  ((register 'set) value))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;;;创建栈
(define (make-stack)
  (let ((s '())
        (number-pushes 0)
        (max-depth 0)
        (current-depth 0))
    (define (push x)
      (set! s (cons x s))
      (set! number-pushes (+ 1 number-pushes))
      (set! current-depth (+ 1 current-depth))
      (set! max-depth (max current-depth max-depth)))
    (define (pop)
      (if (null? s)
          (error "Empty stack -- POP")
          (let ((top (car s)))
            (set! s (cdr s))
            (set! current-depth (- current-depth 1))
            top)))
    (define (initialize)                                     ;初始化
      (set! s '())
      (set! number-pushes 0)
      (set! current-depth 0)
      (set! max-depth 0)
      'done)
    (define (print-statistics)
      (newline)
      (display (list 'total-pushes '= number-pushes
                     'maximum-depth '= max-depth)))
    (define (dispatch m)
      (cond ((eq? m 'push) push)
            ((eq? m 'pop) (pop))
            ((eq? m 'initialize) (initialize))
            ((eq? m 'print-statistics) (print-statistics))
            (else (error "Unknown requset -- STACK"
                         m))))
    dispatch))

;;;弹出栈顶元素
(define (pop stack)
  (stack 'pop))

;;;将元素压入栈中
(define (push stack value)
  ((stack 'push) value))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;;;基本机器
(define (make-new-machine)
  (let ((pc (make-register 'pc))                             ;程序计数器，指向指令序列开头
        (flag (make-register 'flag))                         ;检测的真假，branch的依据
        (stack (make-stack))                                 ;栈
        (the-instruction-sequence '()))                      ;命令序列
    (let ((the-ops (list (list 'initialize-stack             ;操作表
                               (lambda ()
                                 (stack 'initialize)))
                         (list 'print-stack-statistics
                               (lambda ()
                                 (stack 'print-statistics)))))
          (register-table (list (list 'pc pc)                ;寄存器表
                                (list 'flag flag))))
      (define (allocate-register name)                       ;分配寄存器
        (if (assoc name register-table)
            (error "Multiply defined register: "
                   name)
            (set! register-table
                  (cons (list name (make-register name))
                        register-table)))
        'register-allocated)
      (define (lookup-register name)                         ;查找寄存器  
        (let ((val (assoc name register-table)))
          (if val
              (cadr val)
              (error "Unknown register: "
                     name))))
      (define (execute)                                      ;执行命令
        (let ((insts (get-contents pc)))                     ;随着命令的执行，pc的内容不断变化
          (if (null? insts)
              'done
              (begin ((instruction-execution-proc            ;找出命令中的可执行过程运行
                       (car insts)))
                     (execute)))))
      (define (dispatch m)
        (cond ((eq? m 'start)
               (set-contents! pc the-instruction-sequence)   ;pc执行指向整个指令序列的开头
               (execute))
              ((eq? m 'install-instruction-sequence)
               (lambda (seq) (set! the-instruction-sequence
                                   seq)))
              ((eq? m 'allocate-register)
               allocate-register)
              ((eq? m 'get-register)
               lookup-register)
              ((eq? m 'install-operations)
               (lambda (ops) (set! the-ops (append the-ops ops))))
              ((eq? m 'stack) stack)
              ((eq? m 'operations) the-ops)
              (else (error "Unknown request -- MACHINE"
                           m))))
      dispatch)))

(define (start machine)
  (machine 'start))



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;;;获取机器中的寄存器
(define (get-register machine reg-name)
  ((machine 'get-register) reg-name))

;;;获取机器中的寄存器内容
(define (get-register-contents machine register-name)
  (get-contents (get-register machine register-name)))

;;;设置机器中寄存器内容
(define (set-register-contents! machine register-name value)
  (set-contents! (get-register machine register-name) value)
  'done)



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;



;;;对控制器文本汇编
(define (assemble controller-text machine)
  (extract-labels controller-text
                  (lambda (insts labels)                     ;对最终得到指令表，标号表进行处理
                    (update-insts! insts
                                   labels
                                   machine)
                    insts)))


;;;提取标号
(define (extract-labels text receive)
  (if (null? text)
      (receive '() '())                                      ;指令表，标号表的初始化调用，同时返回多个参数的一种形式
      (extract-labels (cdr text)
                      (lambda (insts labels)
                        (let ((next-inst (car text)))
                          (if (symbol? next-inst)
                              (receive insts                 
                                       (cons (make-label-entry
                                              next-inst
                                              insts)
                                             labels))
                              (receive (cons (make-instruction next-inst)
                                             insts)
                                       labels)))))))


;;;修改指令表
;insts 完整的指令表
;labels 完整的标号表
(define (update-insts! insts labels machine)
  (let ((pc (get-register machine 'pc))
        (flag (get-register machine 'flag))
        (stack (machine 'stack))
        (ops (machine 'operations)))
    (for-each (lambda (inst)                                ;为每条命令生成执行过程
                (set-instruction-execution-proc!
                 inst
                 (make-execution-procedure
                  (instruction-text inst)
                  labels
                  machine
                  pc
                  flag
                  stack
                  ops)))
              insts)))


;;;创建指令
(define (make-instruction text)
  (cons text '()))

;;;指令内容
(define (instruction-text inst)
  (car inst))

;;;指令
(define (instruction-execution-proc inst)
  (cdr inst))

;;;设置指令的过程
(define (set-instruction-execution-proc! inst proc)
  (set-cdr! inst proc))

;;;创建标号入口
(define (make-label-entry label-name insts)
  (cons label-name insts))

;;;寻找标号入口
(define (lookup-label labels label-name)
  (let ((val (assoc label-name labels)))
    (if val
        (cdr val)
        (error "Undefined label -- ASSEMBLE"
               label-name))))

;;;创建可执行过程
;inst 指令内容，不是完整的指令对象
(define (make-execution-procedure inst labels machine
                                  pc flag stack ops)
  (cond ((eq? (car inst) 'assign)
         (make-assign inst machine labels ops pc))
        ((eq? (car inst) 'test)
         (make-test inst machine labels ops flag pc))
        ((eq? (car inst) 'branch)
         (make-branch inst machine labels flag pc))
        ((eq? (car inst) 'goto)
         (make-goto inst machine labels pc))
        ((eq? (car inst) 'save)
         (make-save inst machine stack pc))
        ((eq? (car inst) 'restore)
         (make-restore inst machine stack pc))
        ((eq? (car inst) 'perform)
         (make-perform inst machine labels ops pc))
        (else (error "Unknown instruction type -- ASSEMBLE"
                     inst))))



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;pc寄存器的指针移动一步
(define (advance-pc pc)
  (set-contents! pc (cdr (get-contents pc))))



;;;创建赋值过程
(define (make-assign inst machine labels operations pc)
  (let ((target (get-register machine (assign-reg-name inst)))
        (value-exp (assign-value-exp inst)))
    (let ((value-proc (if (operation-exp? value-exp)
                          (make-operation-exp
                           value-exp machine labels operations)
                          (make-primitive-exp
                           (car value-exp) machine labels))))
      (lambda ()
        (set-contents! target (value-proc))   
        (advance-pc pc)))))

(define (assign-reg-name assign-instruction)
  (cadr assign-instruction))

(define (assign-value-exp assign-instruction)
  (cddr assign-instruction))



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;;;创建测试
(define (make-test inst machine labels operations flag pc)
  (let ((condition (test-condition inst)))
    (if (operation-exp? condition)
        (let ((condition-proc (make-operation-exp condition
                                                  machine
                                                  labels
                                                  operations)))
          (lambda ()
            (set-contents! flag (condition-proc))               ;将测试结果存入flag
            (advance-pc pc)))
        (error "Bad TEST instruction -- ASSEMBLE"
               inst))))

(define (test-condition test-instruction)
  (cdr test-instruction))



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;;;创建分支
;branch指定的目标一定是一个标号
(define (make-branch inst machine labels flag pc)
  (let ((dest (branch-dest inst)))
    (if (label-exp? dest)
        (let ((insts (lookup-label labels
                                   (label-exp-label dest))))
          (lambda ()
            (if (get-contents flag)                             ;如果flag内容为真，则执行branch，否则跳过
                (set-contents! pc insts)
                (advance-pc pc))))
        (error "Bad BRANCH instruction -- ASSEMBLE"
               inst))))

(define (branch-dest branch-instruction)
  (cadr branch-instruction))



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;;;创建goto
(define (make-goto inst machine labels pc)
  (let ((dest (goto-dest inst)))
    (cond ((label-exp? dest)
           (let ((insts (lookup-label labels
                                      (label-exp-label dest))))
             (lambda () (set-contents! pc insts))))
          ((register-exp? dest)
           (let ((reg (get-register machine
                                    (register-exp-reg dest))))
             (lambda () (set-contents! pc
                                       (get-contents reg)))))
          (else (error "Bad GOTO instruction -- ASSEMBLE"
                       inst)))))

(define (goto-dest goto-instruction)
  (cadr goto-instruction))




;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;



;;;将指令需要的寄存器内容压入栈中
(define (make-save inst machine stack pc)
  (let ((reg (get-register machine
                           (stack-inst-reg-name inst))))
    (lambda ()
      (push stack (get-contents reg))
      (advance-pc pc))))

;;;将栈顶元素弹出到指令需要的寄存器中
(define (make-restore inst machine stack pc)
  (let ((reg (get-register machine
                           (stack-inst-reg-name inst))))
    (lambda ()
      (set-contents! reg (pop stack))
      (advance-pc pc))))

;;;指令中的寄存器名
(define (stack-inst-reg-name stack-instruction)
  (cadr stack-instruction))


;;;创建执行
(define (make-perform inst machine label operations pc)
  (let ((action (perform-action inst)))
    (if (operation-exp? action)
        (let ((action-proc (make-operation-exp action
                                               machine
                                               label
                                               operations)))
          (lambda ()
            (action-proc)
            (advance-pc pc)))
        (error "Bad PERFORM instrcution -- ASSEMBLE"
               inst))))

(define (perform-action inst)
  (cadr inst))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;;;创建基本表达式
(define (make-primitive-exp exp machine labels)
  (cond ((constant-exp? exp)
         (let ((c (constant-exp-value exp)))
           (lambda () c)))
        ((label-exp? exp)
         (let ((insts (lookup-label labels
                                    (label-exp-label exp))))
           (lambda () insts)))
        ((register-exp? exp)
         (let ((r (get-register machine
                                (register-exp-reg exp))))
           (lambda () (get-contents r))))
        (else (error "Unknown expression type -- ASSEMBLE"
                     exp))))

(define (register-exp? exp) (tagged-list? exp 'reg))
(define (register-exp-reg exp) (cadr exp))

(define (constant-exp? exp) (tagged-list? exp 'const))
(define (constant-exp-value exp) (cadr exp))

(define (label-exp? exp) (tagged-list? exp 'label))
(define (label-exp-label exp) (cadr exp))



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;;;创建操作表达式，所有操作来自机器中
(define (make-operation-exp exp machine labels operations)
  (let ((op (lookup-prim (operation-exp-op exp) operations))
        (aprocs (map (lambda (e) (if (label-exp? e)
                                     (error "can't operate on label -- MAKE-OPERATION-EXP"
                                            e)
                                     (make-primitive-exp e
                                                         machine
                                                         labels)))
                    (operation-exp-operands exp))))
    (lambda ()
      (apply op (map (lambda (p) (p)) aprocs)))))

(define (operation-exp? exp)
  (and (pair? exp) (tagged-list? (car exp) 'op)))

(define (operation-exp-op operation-exp)
  (cadr (car operation-exp)))

(define (operation-exp-operands operation-exp)
  (cdr operation-exp))


(define (lookup-prim symbol operations)
  (let ((val (assoc symbol operations)))
    (if val
        (cadr val)
        (error "Unknown operation -- ASSEMBLE"
               symbol))))











;判断exp是否以tag开头
(define (tagged-list? exp tag)
  (if (pair? exp)
      (eq? (car exp) tag)
      false))







