(defn linear-vector? [vctr] (and (vector? vctr) (every? number? vctr)))

(defn arity-equal? [& matrix] (or (every? number? matrix)
                                  (and (every? vector? matrix)
                                       (apply == (mapv count matrix))
                                       (every? true? (apply mapv arity-equal? matrix)))))

(defn matrix? [mtrx] (and (vector? mtrx) (every? linear-vector? mtrx) (apply arity-equal? mtrx)))

(defn scalar? [a] (number? a))

(defn vector-ops [op] (fn [& vectors]
                        {:pre [(apply arity-equal? vectors)]
                         :post [(arity-equal? % (first vectors))]}
                        (apply mapv op vectors)))

(def v+ (vector-ops +))
(def v- (vector-ops -))
(def v* (vector-ops *))
(def vd (vector-ops /))

(defn scalar [& vectors] {:pre [(apply arity-equal? vectors)]
                          :post [(scalar? %)]}
  (apply + (apply v* vectors)))

(defn vect
  [& vectors]
  {:pre [(apply arity-equal? vectors) (== (count (first vectors)) 3)]
   :post [(== (count %) 3)]}
  (reduce (fn [a b]
            [(- (* (a 1) (b 2)) (* (a 2) (b 1)))
             (- (* (a 2) (b 0)) (* (a 0) (b 2)))
             (- (* (a 0) (b 1)) (* (a 1) (b 0)))])
          vectors))

(defn v*s [vctr & sclrs]
  {:pre [(linear-vector? vctr) (every? scalar? sclrs)]
   :post [(arity-equal? % vctr)]}
  (mapv (partial * (apply * sclrs)) vctr))

(defn transpose [mtrx]
  {:pre [(matrix? mtrx)]
   :post [(matrix? mtrx)]}
  (apply mapv vector mtrx))            ;

(defn matrix-ops [op] (fn [& matrix]
                        {:pre [(every? matrix? matrix) (apply arity-equal? matrix)]
                         :post [(matrix? %) (arity-equal? % (first matrix))]}
                        (apply mapv (vector-ops op) matrix)))

(def m+ (matrix-ops +))
(def m- (matrix-ops -))
(def m* (matrix-ops *))
(def md (matrix-ops /))

(defn m*s [matrix & sclrs]
  {:pre [(matrix? matrix) (every? scalar? sclrs)]
   :post [(matrix? %) (arity-equal? % matrix)]}
  (let [mul-sclrs (apply * sclrs)] (mapv (fn [vctr] (v*s vctr mul-sclrs)) matrix)))

(defn m*v [matrix & vectors]
  {:pre [(every? linear-vector? vectors)
         (matrix? matrix) (apply arity-equal? (first matrix) vectors)]
   :post [(linear-vector? %) (== (count %) (count matrix))]}
  (mapv (partial scalar (apply v* vectors)) matrix))

(defn m*m [& matrix]
  {:pre [(every? matrix? matrix) (arity-equal? (mapv first matrix))]
   :post [(matrix? %)]}
  (reduce (fn [a b] (mapv (partial m*v (transpose b)) a)) matrix))

(defn tensor? [t] (or (every? number? t)
                      (and (every? linear-vector? t)
                           (apply == (mapv count t))
                           (every? tensor? (apply mapv vector t)))))

(defn tensors? [& ts] (every? tensor? ts))



(defn tensor-ops [op] (fn oper [& tensors]
                        {:pre [(every? tensor? tensors) (apply arity-equal? tensors)]
                         :post [(tensor? %)]}
                        (if (number? (first tensors))
                          (apply op tensors)
                          (apply mapv oper tensors))));

(def hb+ (tensor-ops +))
(def hb- (tensor-ops -))
(def hb* (tensor-ops *))
(def hbd (tensor-ops /))
