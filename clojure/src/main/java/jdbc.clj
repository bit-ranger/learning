(ns com.rainyalley.learning.jdbc
    (:use clojure.java.jdbc))

(defn print-rows [rows]
    (dorun (for [row rows]
           (println (row :id) (row :name) (row :password) (row :enabled)))))

( let [db-host "localhost"
        db-port 3306
        db-name "architecture"]
   ; The classname below must be in the classpath.
   (def db {:classname   "com.mysql.jdbc.Driver"
            :subprotocol "mysql"
            :subname     (str "//" db-host ":" db-port "/" db-name)
            ; Any additional map entries are passed to the driver
            ; as driver-specific properties.
            :user        "root"
            :password    ""})
   (with-db-connection [con db]
                       (let [rows (query con ["select * from user where id != ?" 421])]
                           (print-rows rows))))


