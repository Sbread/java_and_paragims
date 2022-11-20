(load-file "proto.clj")
(load-file "parser.clj")

(defn constant [const] (constantly const))
(defn variable [name] (fn [vars] (get vars name)))

(defn operation [op] (fn [& operands] (fn [vars] (apply op (map #(% vars) operands)))))

(def negate (operation -))
(def add (operation +))
(def subtract (operation -))
(def multiply (operation *))
(defn divide-op ([operand] (/ 1 (double operand))) ([operand & ops] (/ operand (double (apply * ops)))))
(def divide (operation divide-op))
(defn sumexp-op [& exps] (apply + (mapv #(Math/exp %) exps)))
(def sumexp (operation sumexp-op))
(defn softmax-op [& exps] (divide-op (Math/exp (first exps)) (apply sumexp-op exps)))
(def softmax (operation softmax-op))

(def op-tag {'negate negate '+ add '- subtract '* multiply '/ divide 'sumexp sumexp 'softmax softmax})

(defn parse-proto
  [variable constant op-tag-map expr]
  (letfn
    [(parseSymbol
       [operand]
       (cond
         (symbol? operand) (variable (name operand))
         (number? operand) (constant operand)
         :else (parse operand)))
     (parse [curr] (if (list? curr) (apply (op-tag-map (first curr)) (mapv parseSymbol (rest curr)))
                                    (parseSymbol curr)))]
    (parse (read-string expr))))

(def parseFunction (partial parse-proto variable constant op-tag))

;--------------------------------parseObject-------------------------------------------------------------

(def evaluate (method :evaluate))
(def toString (method :toString))
(def diff (method :diff))

(declare ZERO)
(declare ONE)

(def name_ (field :name))
(def op_ (field :op))
(def tag_ (field :tag))
(def operands_ (field :operands))
(def derivative_ (field :derivative))

; :NOTE: (field :name)
(def var-const-proto
  {:evaluate (fn [this vars] (proto-get vars (name_ this) (name_ this)))
   :toString (fn [this] (str (name_ this)))
   :diff (fn [this var] (if (= (name_ this) var) ONE ZERO))})

(def Constant (constructor (fn [this value] (assoc this :name value)) var-const-proto))
(def Variable (constructor (fn [this value] (assoc this :name value)) var-const-proto))

(def ZERO (Constant 0))
(def ONE (Constant 1))

(def operation-proto
  {:evaluate (fn [this vars] (apply (op_ this) (mapv #(evaluate % vars) (operands_ this))))
   :toString
   (fn
     [this] (str "(" (tag_ this) " " (clojure.string/join " " (mapv toString (operands_ this))) ")"))
   :diff
   (fn
     [this var]
     ; :NOTE: vector
     (apply (derivative_ this) (mapv (fn [arg] [arg (diff arg var)]) (operands_ this))))})

(defn operation-constructor
  [op tag derivative]
  (let [op-proto {:prototype operation-proto :op op :tag tag :derivative derivative}
        associate (fn [this & ops] (assoc this :operands ops))]
    (constructor associate op-proto)))

(declare Add)
(declare Subtract)
(declare Multiply)
(declare Divide)
(declare Sumexp)

; :NOTE: Перенести

; :NOTE: Квадрат?
;(defn Multiply-derivative
;  [& ops] ((reduce (fn [a b] [(Multiply (a 0) (b 0)) (Add (Multiply (a 1) (b 0)) (Multiply (a 0) (b 1)))]) ops) 1))

(defn Multiply-derivative [op & ops]
  (if (== (count ops) 0)
    (op 1)
    (Add
      (apply Multiply (op 1) (mapv #(% 0) ops))
      (Multiply (op 0) (apply Multiply-derivative ops)))))


(defn Divide-derivative
  [operand & ops]
  (let [div2-derivative
        (fn [a b] (Divide (Subtract (Multiply (a 1) (b 0)) (Multiply (a 0) (b 1)))
                          (Multiply (b 0) (b 0))))
        denominator (apply Multiply (mapv #(% 0) ops))]
    (if (== (count ops) 0)
      (div2-derivative [ONE ZERO] operand)
      (div2-derivative operand [denominator (apply Multiply-derivative ops)]))))

(defn Sumexp-derivative [& ops] (apply Add (mapv #(Multiply (Sumexp (% 0)) (% 1)) ops)))

(def Negate (operation-constructor - "negate" (fn [operand] (Negate (operand 1)))))
(def Add (operation-constructor + "+" (fn [& ops] (apply Add (mapv #(% 1) ops)))))
(def Subtract (operation-constructor - "-" (fn [& ops] (apply Subtract (mapv #(% 1) ops)))))
(def Multiply (operation-constructor * "*" Multiply-derivative))
(def Divide (operation-constructor divide-op "/" Divide-derivative))
(def Sumexp (operation-constructor sumexp-op "sumexp" Sumexp-derivative))
(def Softmax (operation-constructor softmax-op "softmax"
                                    (fn [& ops] (Divide-derivative
                                                  [(Sumexp ((first ops) 0)) (Sumexp-derivative (first ops))]
                                                  [(apply Sumexp (mapv #(% 0) ops)) (apply Sumexp-derivative ops)]))))

(def op-obj-tag {'negate Negate '+ Add '- Subtract '* Multiply '/ Divide 'sumexp Sumexp 'softmax Softmax})

; :NOTE: CP
(def parseObject (partial parse-proto Variable Constant op-obj-tag))
