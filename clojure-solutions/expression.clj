; func parser ////////////////////////////////////////////////////////////////////////////////////////////////////////
(load-file "parser.clj")

(defn abstractOp [op] (fn [& args]
                        (fn [some] (apply op (mapv #(% some) args)))))

(defn div
  ([x] (div 1 x))
  ([x y] (/ (double x) (double y)))
  ([x y & args] (reduce div (div x y) args)))

(def add (abstractOp +))
(def subtract (abstractOp -))
(def multiply (abstractOp *))
(def divide (abstractOp div))
(def negate (abstractOp -))
;(def negate (abstractOp #(- %))) // only one argument

(defn constant [v] (constantly v))
; start modification
(defn pi [] Math/PI)
(defn e [] Math/E)
; end modification
(defn variable [v] (fn [num] (num v)))

; start modification ///////////////////////////////////////////////////////////////////////////////////////////////
; 36-37

(defn meanFunc [& args] (/ (apply + args) (count args)))
(def mean (abstractOp meanFunc))
(def varn (abstractOp (fn [& args] (- (apply meanFunc (map #(* % %) args)) (#(* % %) (apply meanFunc args))))))

; other
(def sin (abstractOp #(Math/sin %)))
(def cos (abstractOp #(Math/cos %)))
; end modification ////////////////////////////////////////////////////////////////////////////////////////////////////////

; object parser
(definterface GlobalInterface
  (evaluate [_])
  (toString [_])
  (toStringPostfix []))


(def toString #(.toString %1))
(def evaluate #(.evaluate %1 %2))
(def toStringPostfix #(.toStringPostfix %1))


(deftype ConstantImpl [const]
  GlobalInterface
  (evaluate [_ _] const)
  (toStringPostfix [_] (str const))
  Object
  (toString [_] (str const)))

(def Constant #(ConstantImpl. %1))

(deftype VariableImpl [var]
  GlobalInterface
  (evaluate [_ args] (args (clojure.string/lower-case (first var))))
  (toStringPostfix [_] var)
  Object
  (toString [_] var))

(def Variable #(VariableImpl. %1))

(deftype Operations [op symb glArgs]
  GlobalInterface
  (evaluate [_ args] (apply op (map #(.evaluate % args) glArgs)))
  (toStringPostfix [_] (str "(" (clojure.string/join " " (mapv toStringPostfix glArgs)) " " symb ")"))
  Object
  (toString [_] (str "(" symb " " (clojure.string/join " " (mapv toString glArgs)) ")")))

(def AbstractOperations #(fn [& args] (Operations. %1 %2 args)))


(def Add (AbstractOperations + '+))
(def Multiply (AbstractOperations * '*))
(def Subtract (AbstractOperations - '-))
(def Divide (AbstractOperations div '/))
(def Negate (AbstractOperations - 'negate))
(def Exp (AbstractOperations #(Math/exp %) 'exp))
(def Ln (AbstractOperations #(Math/log %) 'ln))



; parsing ////////////////////////////////////////////////////////////////////////////////////////////////////////

;maps
(def mapOp {'+ add, '- subtract, '* multiply, '/ divide, 'negate negate, 'mean mean, 'varn varn, 'sin sin, 'cos cos})
(def mapObj {'+ Add, '- Subtract, '* Multiply, '/ Divide, 'negate Negate, 'exp Exp, 'ln Ln}) ;; {'+ '- '* '/ 'n 'e 'gateexpln}
;parsers
(defn parseExpression [expr mapping constFO varFO]
  (cond
    (string? expr) (parseExpression (read-string expr) mapping constFO varFO)
    (list? expr) (apply (mapping (first expr)) (map #(parseExpression % mapping constFO varFO) (next expr)))
    (symbol? expr) (varFO (str expr))
    (number? expr) (constFO expr)))

(defn parseFunction [expr] (parseExpression expr mapOp constant variable))
(defn parseObject [expr] (parseExpression expr mapObj Constant Variable))


; comb parser ////////////////////////////////////////////////////////////////////////////////////////////////////////
(def *str #(+str (+plus (+char %))))
(def constComb (+map (comp Constant read-string)
                     (+str (+seqf #(cons %1 %2)
                                  (+opt (+char "-"))
                                  (*str ".0123456789")))))

;; :NOTE: не расширяемо
(def whiteSpase (+ignore (+star (+char " \n\r\t"))))

(def *seqnInSpase #(+seqn 0 whiteSpase %1 whiteSpase))


(def varComb (+map Variable (*str "xyzXYZ")))

(def *ignore #(+ignore (+char %)))
(def *or #(+or % constComb varComb))

(def *spaseOr #(*seqnInSpase (*or %)))


(def operation
  (+seqf
    #(apply %2 %1)
    (*ignore "(")
    (+plus (*spaseOr (delay operation)))
   ;; :NOTE: не пересоздавать строку (reduce ...)
   ;; *ws (*or (*or (*str '+) (*str '-)) (*str '*)) *ws
    (*seqnInSpase (+map #(mapObj (symbol %)) (*str (reduce str (mapv name (keys mapObj))))))
    (*ignore ")")))

(def parseObjectPostfix (+parser (*spaseOr (delay operation))))


