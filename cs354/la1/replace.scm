(define (copy x)
    (cdr (cons '() x))
  )

(define recurse
    (lambda (source search-for replace-with)
        (cond 
            ((equal? source search-for) (copy replace-with))
            ((not (pair? source)) (copy source))
            (else (cons
                    (recurse (car source) search-for replace-with)
                    (recurse (cdr source) search-for replace-with))
            )
        )
            
    )
)

(define (replace source search-for replace-with)
        (recurse (copy source) search-for replace-with)
)

(replace 1 1 2)

(replace '(a (b c) d)
         '(b c)
         '(x y))

(replace '(a (b c) (d (b c)))
'(b c)
'(x y))

(replace '(a b c)
         '(a b)
         '(x y))

(replace '(a b c)
         '(b c)
         '(x y))
         
(replace '(a b c d e f g) 
		 '(e f g) 
		 '(1 2 3 4))


