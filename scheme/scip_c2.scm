(define (cons x y)
  (define (dispatch m)
	(cond ((= m 0) x)
		  ((= m 1) y)
		  (else (error "m should be 0 or 1"))))
  dispatch)

(define (car z) (z 0))
(define (cdr z) (z 1))

(define x (cons 1 2))
(car x)
(cdr x)

;; abstraction barier
