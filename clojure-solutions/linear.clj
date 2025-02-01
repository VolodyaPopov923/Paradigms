(defn vectorMatrixOp [f]
  (fn [& args] (apply mapv f args)))

(def v+ (vectorMatrixOp +))
(def v- (vectorMatrixOp -))
(def v* (vectorMatrixOp *))
(def vd (vectorMatrixOp /))

(defn v*s [v & args]
  (mapv (fn [ell] (apply * ell args)) v))

(defn vect [& vectors]
  (reduce (fn [a v]
            (vector
              (- (* (nth a 1) (nth v 2)) (* (nth a 2) (nth v 1)))
              (- (* (nth a 2) (nth v 0)) (* (nth a 0) (nth v 2)))
              (- (* (nth a 0) (nth v 1)) (* (nth a 1) (nth v 0)))))
          vectors))

(defn scalar [& vs] (apply + (apply v* vs)))

(def m+ (vectorMatrixOp v+))
(def m- (vectorMatrixOp v-))
(def m* (vectorMatrixOp v*))
(def md (vectorMatrixOp vd))

(defn transpose [m] (apply mapv vector m))

(defn m*s [m & args] (mapv (fn [row] (apply v*s row args)) m))

(defn m*v [m & args] (mapv (fn [row] (apply scalar row args)) m))

(defn m*m [& args] (reduce (fn [m1 m2] (mapv #(m*v (transpose m2) %) m1)) args))

(def c+ (vectorMatrixOp m+))
(def c- (vectorMatrixOp m-))
(def c* (vectorMatrixOp m*))
(def cd (vectorMatrixOp md))

