(def popsicle-map
  (hash-map :red :cherry, :green :apple, :purple :grape))
(def popsicle-map
  {:red :cherry, :green :apple, :purple :grape}) ; same as previous
(def popsicle-map
  (sorted-map :red :cherry, :green :apple, :purple :grape))


(get popsicle-map :green)
(popsicle-map :green)
(:green popsicle-map)

(contains? popsicle-map :green) ; -> true
(keys popsicle-map) ; -> (:red :green :purple)
(vals popsicle-map) ; -> (:cherry :apple :grape)

;创建一个新的map， 同时添加任意对新的name-value pair, 如果某个给定的key已经存在了，那么它的值会被更新
(assoc popsicle-map :green :lime :blue :blueberry) ; -> {:blue :blueberry, :green :lime, :purple :grape, :red :cherry}

;dissoc 创建一个新的map， 同时忽略掉给定的那么些key
(dissoc popsicle-map :green :blue) ; -> {:purple :grape, :red :cherry}

;遍历 popsicle-map 里面的所有元素，把key bind到 color， 把value bind到 flavor。
;name函数返回一个keyword的字符串名字
(doseq [[color flavor] popsicle-map]
  (println (str "The flavor of " (name color)
                " popsicles is " (name flavor) ".")))
;-> The flavor of green popsicles is apple.
;-> The flavor of purple popsicles is grape.
;-> The flavor of red popsicles is cherry.

;select-keys 函数接收一个map对象，以及一个key的集合的参数，它返回这个集合里面key在那个集合里面的一个子map。看例子:
(select-keys popsicle-map [:red :green :blue]) ; -> {:green :apple, :red :cherry}


(def person {
              :name "Mark Volkmann"
              :address {
                         :street "644 Glen Summit"
                         :city "St. Charles"
                         :state "Missouri"
                         :zip 63304
                       }
              :employer {
                          :name "Object Computing, Inc."
                          :address {
                                     :street "12140 Woodcrest Executive Drive, Suite 250"
                                     :city "Creve Coeur"
                                     :state "Missouri"
                                     :zip 63141
                                   }
                        }
            }
)

;嵌套獲取内容
(get-in person [:employer :address :city])
(-> person :employer :address :city) ; explained below
;reduce 函数接收一个需要两个参数的函数, 一个可选的value以及一个集合。
; 它会以value以及集合的第一个元素作为参数来调用给定的函数（如果指定了value的话），
; 要么以集合的第一个元素以及第二个元素为参数来调用给定的函数（如果没有指定value的话)。
; 接着就以这个返回值以及集合里面的下一个元素为参数来调用给定的函数，直到集合里面的元素都被计算了
(reduce get person [:employer :address :city]) ; explained below


;宏 -> 我们也称为 “thread” 宏, 它本质上是调用一系列的函数，前一个函数的返回值作为后一个函数的参数. 比如下面两行代码的作用是一样的:
(f1 (f2 (f3 x)))
(-> x f3 f2 f1)
(-?> x f3 f2 f1) ;若有nil立即返回nil




;assoc-in 函数可以用来修改一个内嵌的key的值。看下面的例子把person的employer->address->city修改成Clayton了。
(assoc-in person [:employer :address :city] "Clayton")

;update-in 函数也是用来更新给定的内嵌的key对应的值，只是这个新值是通过一个给定的函数来计算出来。
;下面的例子里面会把person的employer->address->zip改成旧的zip + “-1234″。
(update-in person [:employer :address :zip] str "-1234") ; using the str function
