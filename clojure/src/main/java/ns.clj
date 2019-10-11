(ns com.rainyalley.learning.ns
  ;(:require 1)
  (:use clojure.math.numeric-tower)
  (:import (java.text NumberFormat)
   (javax.swing JFrame JLabel)))

(alias 'su 'clojure.string)

(println (su/join "$" [1 2 3])) ; -> 1$2$3
(println (gcd 27 72)) ; -> 9
(println (sqrt 5)) ; -> 2.23606797749979
(println (.format (NumberFormat/getInstance) Math/PI)) ; -> 3.142

; See the screenshot that follows this code.
(def frame (doto (JFrame. "Hello")
  (.add (JLabel. "Hello, World!"))
  (.pack)
  (.setDefaultCloseOperation JFrame/EXIT_ON_CLOSE)
  (.setVisible true)))

(println frame)



;intern 里面符号的名字要括起来
;def 是一个 special form, special form 不会evaluate它的参数,
;而 intern 是一个函数， 它会evaluate它的参数
(def foo 1)
(create-ns 'com.ociweb.demo)
(intern 'com.ociweb.demo 'foo 2)
(println (+ foo com.ociweb.demo/foo)) ; -> 3


(println (ns-interns 'clojure.math.numeric-tower))