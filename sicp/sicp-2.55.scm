#lang scheme/load

(load "sicp-2.54.scm")

;(car ''abc) 等同于 (car '(quote abc))
;引号表示目标为一个数据，而非求值表达式
;因此整个表达式的功能为获取列表 (quote abc) 的前置元素