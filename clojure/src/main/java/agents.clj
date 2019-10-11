(def my-agent (agent 998))

(defn action
    [s]
    (println "do it as" s))

(send my-agent action)
(send-off my-agent action)

;(await my-agent)
(await-for 1000 my-agent)

(println "errors :" (agent-errors my-agent))
;(clear-agent-errors my-agent)

(shutdown-agents)
