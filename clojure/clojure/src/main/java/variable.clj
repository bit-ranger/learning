(def ^:dynamic v 1) ;v 是一个全局绑定

(defn f1 []
  (println "f1:v= " v)) ; 全局绑定

(defn f2 []
  (println "f2:before let v = " v); 全局綁定
  (let [v 2] ;本地綁定
    (println "f2 in let , v = " v)
    (f1) ;文本作用域，沒有改變全局變量
    )
  (println "f2:after let v = " v) ;全局綁定
)

(defn f3 []
  (println "f3:before biding v = " v) ;全局綁定
  (binding [v 3]
    (println "f3:in binding, v = " v) ;臨時的全局綁定
    (f1) ;文本作用域，改變了全局變量
  )
  (println "f3:after binding v = " v); 全局變量又恢復了
)

(defn f4 []
  (def v 4)) ;改變了全局變量

(f2)
(f3)
(f4)

(println "after calling f4, v = " v)

