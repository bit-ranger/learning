;转账事务

(defstruct account-struct :id :owner :balance-ref)

(def account-map-ref (ref (sorted-map)))

(defn open-account
    "开户"
    [owner]
    (dosync
        (let [account-map @account-map-ref
              last-entry (last account-map)
              id (if last-entry
                     (inc (key last-entry))
                     1)
              account (struct account-struct id owner (ref 0))]
            (alter account-map-ref assoc id account)
            account)))

(defn deposit
    "存钱"
    [account amount]
    (dosync
        (Thread/sleep 50)
        (let [owner (account :owner)
              balance-ref (account :balance-ref)
              type (if (pos? amount) "deposit" "withdraw")
              direction (if (pos? amount) "to" "from")
              abs-amount (Math/abs amount)]
            (if (>= (+ @balance-ref amount) 0)
                (do
                    (alter balance-ref + amount)
                    (println (str type "ing") abs-amount direction owner))
                (throw (IllegalArgumentException.
                          (str "insufficient balance for " owner " to withdraw " abs-amount)))))))

(defn withdraw
    "取钱"
    [account amount]
    (deposit account (- amount)))

(defn transfer
    "转账"
    [from-account to-account amount]
    (dosync
        (println "transferring" amount "from " (from-account :owner) "to" (to-account :owner))
        (withdraw from-account amount)
        (deposit to-account amount)))

(defn report-1
    "查询单账户"
    [account]
    (let [balance-ref (account :balance-ref)]
        (println "balance for " (account :owner) "is" @balance-ref)))

(defn report
    "查询多账户"
    [& account-list]
    (dosync
        (doseq [account account-list]
            (report-1 account))))

; Set a default uncaught exception handler
; to handle exceptions not caught in other threads.
(Thread/setDefaultUncaughtExceptionHandler
    (proxy [Thread$UncaughtExceptionHandler] []
        (uncaughtException [thread throwable]
            ; Just print the message in the exception.
            (println (.. throwable getMessage)))))

(let [a1 (open-account "Mark")
      a2 (open-account "Tami")
      thread (Thread. #(transfer a1 a2 50))]
    (try
        (deposit a1 100)
        (deposit a2 200)
        (.start thread)
        (withdraw a1 75)
        (.join thread)
        (report a1 a2)
        (catch IllegalArgumentException e
            (println (.getMessage e) "in main thread"))))





