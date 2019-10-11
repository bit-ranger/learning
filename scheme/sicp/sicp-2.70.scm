#lang scheme/load

(load "huffman.scm")

(define weight-pair
  '((A 2)
   (NA 16)
   (BOOM 1)
   (SHA 3)
   (GET 2)
   (YIP 9)
   (JOB 2)
   (WAH 1)))

(define huffman-tree
  (generate-huffman-tree weight-pair))