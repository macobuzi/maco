;; common
(define (abs x)
	(if (< x 0)
		(- x)
		x))

(define (square x)
  (* x x))

(define (even? x)
  (= (remainder x 2) 0))

(define (divides? a b)
  (= (remainder a b) 0))

(define (average x y)
  (/ (+ x y) 2))

(define (cube x)
  (* x x x))

;; procedure as black-box abstraction

(define (sqrt x)
  (define (sqrt-iter guess)
	(if (good-enough? guess)
		guess
		(sqrt-iter (improve guess))))

  (define (improve guess)
	(average guess (/ x guess)))

  (define (good-enough? guess)
	(< (abs (- (square guess) x)) 0.001))
  
  (sqrt-iter 1.0))

;; linear recursive and tree recursive
(define (factorial n)
  (if (= n 1)
	  1
	  (* n (factorial (- n 1)))))

(define (factorial2 n)
  (define (fact-iter product counter)
	(if (> counter n)
		product
		(fact-iter (* counter product) (+ counter 1))))
  (fact-iter 1 1))

(define (fib n)
  (cond ((= n 0) 0)
		((= n 1) 1)
		(else (+ (fib (- n 1)) (fib (- n 2))))))

(define (fib2 n)
  (define (fib-iter a b counter)
	(if (= counter 0)
		b
		(fib-iter (+ a b) a (- counter 1))))
  (fib-iter 1 0 n))

(define (count-change amount)
  (define (cc amount kinds-of-coins)
	(cond ((= amount 0) 1)
		  ((< amount 0) 0)
		  ((= kinds-of-coins 0) 0)
		  (else (+ (cc amount (- kinds-of-coins 1))
				   (cc (- amount (first-denomination kinds-of-coins)) kinds-of-coins)))))
  (define (first-denomination kinds-of-coins)
	(cond ((= kinds-of-coins 1) 1)
		  ((= kinds-of-coins 2) 5)
		  ((= kinds-of-coins 3) 10)
		  ((= kinds-of-coins 4) 25)
		  ((= kinds-of-coins 5) 50)))
  (cc amount 5))

(define (sol1-11 n)
  (if (< n 3)
	  n
	  (+ (+ (sol1-11 (- n 1)) (sol1-11 (- n 2))) (sol1-11 (- n 3)))))

(define (pascal row col)
  (cond ((= col 1) 1)
		((= col row) 1)
		(else (+ (pascal (- row 1) (- col 1)) (pascal (- row 1) col)))))

(define (gcd a b)
  (if (= b 0)
	  a
	  (gcd b (remainder a b))))

(define (fast-expt a n)
  (cond ((= n 0) 1)
		((even? n) (square (fast-expt a (/ n 2))))
		(else (* a (fast-expt a (- n 1))))))

(define (smallest-divisor n)
  (define (find-divisor-iter n divisor)
	(cond ((> (square divisor) n) n)
	      ((divides? n divisor) divisor)
		  (else (find-divisor-iter n (+ divisor 1)))))
  (find-divisor-iter n 2))

(define (prime? n)
  (= (smallest-divisor n) n))

(define (time-prime-test n)
  (newline)
  (display n)
  (define (report-prime elapse-time)
	(display " *** ")
	(display elapse-time))
  (define (start-prime-test n start-time)
	(if (prime? n)
		(report-prime (- (runtime) start-time))))
  (start-prime-test n (runtime)))

(define (search-for-primes min max)
  (define (search-iter n max)
	(time-prime-test n)
	(define nn (+ n 2))
	(if (< nn max)
		(search-iter nn max)))
  (search-iter (+ min 1) max))

;; higher order procedure

(define (sum term a next b)
  (if (> a b)
	  0
	  (+ (term a)
		 (sum term (next a) next b))))

(define (inc n) (+ n 1))
(define (sum-cube a b)
  (sum cube a inc b))

;;(sum-cube 1 10)

(define (identity x) x)
(define (sum-integer a b)
  (sum identity a inc b))
(define (pi-sum a b)
  (define (pi-term x) (/ 1.0 (* x (+ x 2))))
  (define (pi-next x) (+ x 4))
  (sum pi-term a pi-next b))

;;(sum-integer 1 10)
;;(* 8 (pi-sum 1 1000))

(define (integral f a b dx)
  (define (i-term x) (f (+ x (/ dx 2.0))))
  (define (i-inc x) (+ x dx))
  (* (sum i-term a i-inc b) dx))

;;(integral cube 0 1 0.01)
;;(integral cube 0 1 0.001)

(define (search f close-enough? neg-point pos-point)
  (let ((mid-point (average neg-point pos-point)))
	(if (close-enough? neg-point pos-point)
		mid-point
		(let ((test-value (f mid-point)))
		  (cond ((= test-value 0) mid-point)
		        ((> test-value 0) (search f close-enough? neg-point mid-point))
				((< test-value 0) (search f close-enough? mid-point pos-point)))))))

(define (half-interval-method f close-enough? a b)
  (let ((a-value (f a))
		(b-value (f b)))
	(cond ((and (< a-value 0) (> b-value 0))
		   (search f close-enough? a b))
		  ((and (> a-value 0) (< b-value 0))
		   (search f close-enough? b a))
		  (else (error "Value are not opposite side" a b)))))

(define (good-gap a b) (< (abs (- a b)) 0.001))

(half-interval-method
 (lambda (x) (- (* x x x) (* 2 x) 3)) good-gap 1.0 2.0)

(define (fixed-point f g first-guess)
  (define (try-iter f g x)
	 (let ((val (f x)))
	   (if (g val x)
		   x
		   (try-iter f g val))))
   (try-iter f g first-guess))

;;(fixed-point cos good-gap 1.0)
;;(fixed-point (lambda (x) (+ (sin x) (cos x))) good-gap 1.0)
(define (sqrt2 x)
  (fixed-point (lambda (y) (average y (/ x y))) good-gap 1.0))

(define (average-damp f)
  (lambda (x) (average x (f x))))

((average-damp square) 10)

(define (sqrt3 x)
  (fixed-point (average-damp (lambda (y) (/ x y))) good-gap 1.0))
(define (cube-root x)
  (fixed-point (average-damp (lambda (y) (/ x (square y)))) good-gap 1.0))
(cube-root 27)
