#lang planet neil/sicp



;;;1.4
;如果b大于0，则返回+，否则返回-，此处的+与-既是值又是函数，实际上，在lisp中函数即值。
(define (a-plus-abs-b a b)
  ((if (> b 0) + -) a b))