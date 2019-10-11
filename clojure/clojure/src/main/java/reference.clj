(ns com.rainyalley.learning.reference.ns)

;var
;(def name new-value)
; 可以赋新的值 (alter-var-root (var name) update-fn args)
; 自动设置新值 (set! name new-value)
; 在一个binding form 里满设置一个新的、线程本地的值
(def ^:dynamic var-name 998)
(defn change-it []
           (binding [var-name 233]
               (println var-name)
               (set! var-name 999)
               (println var-name)))

(let [thread (Thread. #(change-it))]
    (.start thread)
    (.join thread))

(println var-name)


;ref
;(ref-set ref new-value)
;必须在dosync里面调用 (alter ref update-fn arguments)
;必须在dosync里面调用 (commute ref update-fn arguments)
;必须在 dosync 里面调用
(def ref-name (ref 666))
(dosync
    (let  [oldVal (deref ref-name)]
        (ref-set ref-name (+ oldVal 1))))
(println (deref ref-name))

(dosync
    (alter ref-name inc)                                    ;alter 函数是用来操作那些必须以特定顺序进行的修改
    (commute ref-name inc)                                  ;commute 函数则是要来操作那些修改顺序不是很重要 -- 可以同时进行的修改。
    )
(println (deref ref-name))








;atom
;(reset! atom new-value)
;(compare-and-set! atom current-value new-value)
;(swap! atom update-fn arguments)
;(atom name 233)

;agent
;(send agent update-fn arguments) (send-off agent update-fn arguments)
;(agent name 007)