;http://article.yeeyan.org/view/67953/111796

;Modules
#lang slideshow
(require slideshow/flash)
;DrRacket会自动下载random.plt库的1.0版并导入random.rkt模块。
;(require (planet schematics/random:1:0/random))
;(random-gaussian)

;一些模块依赖于其他模块，但不是必须属于某个特定的容器或代码包。例如，你可以把前面的所有定义都放进一个叫quick.rkt的文件里，然后加入这么一行,这样就将该文件做成了模块，其他程序可以通过相对路径导入该库。
(provide rainbow square)



;Go
(circle 10)

"Let's go"

;Definitions
(define c (circle 10))

(define r (rectangle 10 20))

;数字表示空格数
(hc-append 5 c r)

(define (square n)
  (filled-rectangle n n))

;Local Binding
(define (four p)
  (define two-p (hc-append p p))
  (vc-append two-p two-p))

(define (checker p1 p2)
  (let ([p12 (hc-append p1 p2)][p21 (hc-append p2 p1)])
    (vc-append p12 p21)))


(define (checkboard p)
  (let* ([rp (colorize p "red")]
         [bp (colorize p "black")]
         [c  (checker rp bp)]
         [c4 (four c)])
    (four c4)))

;Functions are Values
(define (series mk)
  (hc-append 4 (mk 5) (mk 10) (mk 20)))

(define series_lam
  (lambda (mk)
    (hc-append 4 (mk 5) (mk 10) (mk 20))))

;Lexical Scope
(define (rgb-series mk)
  (vc-append (series (lambda (sz) (colorize (mk sz) "red")))
             (series (lambda (sz) (colorize (mk sz) "green")))
             (series (lambda (sz) (colorize (mk sz) "black")))))

;Lists
(list "red" "green" "blue")
(list (circle 10) (square 10))

;map函数接受一个列表和一个函数，然后把这个函数作用到列表的每个元素上，并返回一个包含这些新函数值的列表。
(define (rainbow p)
  (map (lambda (color)
         (colorize p color))
       (list "red" "orange" "yellow" "green" "blue" "purple")))

;apply也是一个操作列表常用的函数。和map一样，它也是取一个函数和一个列表作为参数，但apply会把传给它的函数一次作用在所有参数上，而不是每次处理一个单独的元素。apply函数对那些接受任意数目参数的函数特别有用，比如vc-append
(define (rainbow-v p)
  (apply vc-append (rainbow p)))

;无法执行，参数类型不匹配，vc-append接收可变数量的图片，而此处的参数却是一个列表
;(vc-append (rainbow (square 5)))


;Macros
(require slideshow/code)
;语法扩展
(define-syntax pict+code
  (syntax-rules ()
    [(pict+code expr)
     (hc-append 10 expr (code expr))]))

(pict+code (circle 10))



;Objects
(require racket/class racket/gui/base)
(define HelloJB (new frame%
                     [label "Hello JB"]
                     [width 300]
                     [height 300]
                     [alignment '(center center)]))
(send HelloJB show #t)

(define (add-drawing p)
  (let ([drawer (make-pict-drawer p)])
    (new canvas%
         [parent HelloJB]
         [style '(border)]
         [paint-callback (lambda (self dc)
                           (drawer dc 0 0))]
         )))
(add-drawing (code (Hello JB)))
(add-drawing (colorize (filled-flash 100 100) "black"))