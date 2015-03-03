(ns mars-rover.core)

(defn rover [x y direction]
  {:x x :y y :direction direction})


(defn receive [{x :x y :y direction :direction} commands]
  
  (if (= :north direction)
    
    (if (= commands "l")
      (rover 0 0 :west)
      (rover 0 0 :east))
    
    (if (= commands "l")
      (rover 0 0 :east)
      (rover 0 0 :west))))

