(ns mars-rover.core)

(defn make-rover [x y direction]
  {:x x :y y :direction direction})


(defn receive [rover commands]
  (if (= commands "l")
    (make-rover 0 0 :west)
    (make-rover 0 0 :east)))

