(defn factorial-1 [number]
  "computes the factorial of a positive integer
   in a way that doesn't consume stack space"
  (loop [n number factorial 1]
    (if (zero? n)
      factorial
      (recur (dec n) (* factorial n)))))

(println (time (factorial-1 5))) ; -> "Elapsed time: 0.071 msecs"\n120



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(defn factorial-2 [number] (reduce * (range 2 (inc number))))

(println (time (factorial-2 5))) ; -> "Elapsed time: 0.335 msecs"\n120