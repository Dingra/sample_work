;Keeps taking a user input until 0 is given, at which point it says "Bye"
(define(fake-scheme)
  (if(equal?(read)0)
           "bye"         
           (fake-scheme)))



;counts the number of digits in a number which are equal to 6, 4 or 9
(define (digit-count n);This is a comment
  (if (= n 0);If it is 0, there are no 4s, 6s or 9s
      0
      (if (or (= (remainder n 10) 6) (= (remainder n 10) 4) (= (remainder n 10) 9)) (+ (digit-count (quotient n 10)) 1);If it's a 4, 6 or 9, increment
          (digit-count (quotient n 10)));If not, don't increment
      )
  )

;Drew Ingram
;16680076

;Determines whether or not the string contains the character in question
(define (contains? char str)
  (if(= (string-length str) 0);Base case
     #f;If the length is 0, the character cannot be in the string
     (if (= (string-length str) 1);Base Case
            #f;If it is the only character in the string it must be distinct
            (if(equal? (string-ref str 1) char)
        #t;If the first character the string is the char, true
        (contains? char (substring str 1))))))

;Determines the number of distinct characters in a given string by taking the first character in the string and checking it to the rest to
;see if there is the same character appears in another part of the string.  Once that is done, it removes the first character and checks again
(define(distinct-char str)
  (if(= (string-length str) 0)
     0;If there are no characters in the string, it has no distinct characters
     (if(contains? (string-ref str 0) str);If the character is somewhere else in the string...
                   (distinct-char(substring str 1));It is not distinct so do not increment
                   (+ 1(distinct-char(substring str 1))))));It is distinct so increment




;Author: Drew Ingram
;Student Number: 16680076

;Returns true if the first string is in the first str1-length characters of str2 and false otherwise
(define(substring? str1 str2)
  (if (= (string-length str1) 0)
      #t;Base case
      (if (equal? (string-ref str1 0) (string-ref str2 0))
                  (substring? (substring str1 1 ) (substring str2 1));If they are equal, continue checking
                  #f)));
                  
;string-match -- Checks to see if str2 has str1 as a substring

(define (string-matching str1 str2)
  (if(> (string-length str1) (string-length str2))
        #f;If the first string is longer than the second string, the second string does not contain the first string
        (if(substring? str1 str2)
           #t;If the string str1 is at the end of str2
           (string-matching str1 (substring str2 1)))));If not, call the function again


