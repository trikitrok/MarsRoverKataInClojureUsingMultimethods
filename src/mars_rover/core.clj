(ns mars-rover.core)

(defn receive [rover commands]
  (if (= commands "l")
    {:x 0 :y 0 :direction :west}
    {:x 0 :y 0 :direction :east}))


