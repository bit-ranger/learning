(import '(java.util Calendar GregorianCalendar)
        '(javax.swing JFrame JLabel))


(. java.util.Calendar APRIL)
(. Calendar APRIL)
java.util.Calendar/APRIL
Calendar/APRIL

(println (. Math pow 2 4))
(println (Math/pow 2 4))


(let [calendar (new GregorianCalendar 2008 Calendar/APRIL 16)]
        (println calendar))

(let [calendar (GregorianCalendar. 2008 Calendar/APRIL 15)] ;new
        (println calendar)
        (. calendar add Calendar/MONTH 2)
        (println (. calendar get Calendar/MONTH ))
        (.add calendar Calendar/MONTH 2)
        (println (.get calendar Calendar/MONTH))
        (println (. (. calendar getTimeZone) getDisplayName))
        (println (.. calendar getTimeZone getDisplayName)) ;链式调用

        (doto calendar
                (.set Calendar/YEAR 1981)
                (.set Calendar/MONTH Calendar/AUGUST)
                (.set Calendar/DATE 1))

        (let [formatter (java.text.DateFormat/getDateInstance)]
                (println (.format formatter (.getTime calendar)))
        )
)

(println (map #(.substring %1 %2)
                 ["Moe" "Larry" "Curly"] [1 2 3])) ; -> (oe rry ly)

(println (map (memfn substring beginIndex)
                 ["Moe" "Larry" "Curly"] [1 2 3])) ; -> same


(defn delayed-print  [ms text]
        (Thread/sleep ms)
        (println text))
(.start (Thread. #(delayed-print 1000 ", world!")))
(print "hello")