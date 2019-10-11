(use 'clojure.test)

(deftest add-test
    (is (= 4 (+ 2 2)))
    (is (= 2 (+ 2 0))))

(deftest reverse-test
    (is (= [3 2 1] (reverse [1 2 3]))))

(deftest division-test
    (is (thrown? ArithmeticException (/ 3.0 0))))


(with-test
    (defn my-add [n1 n2] (+ n1 n2))
    (is (= 4 (my-add 2 2)))
    (is (= 2 (my-add 2 0)) "adding zero doesn't change value"))


(deftest multiplication
    (are [n1 n2 result]
        (= (* n1 n2) result) ; a template
        1 1 1,
        1 2 2,
        2 3 6))


(assert (>= 99999 7000))

(defn fixture-name [test-function]
    ; Perform setup here.
    (test-function)
    ; Perform teardown here.
    )

(use-fixtures :once fixture-name)
(use-fixtures :each fixture-name)