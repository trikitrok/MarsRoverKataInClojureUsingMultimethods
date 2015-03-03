(ns mars-rover.core)

(defn rover [x y direction]
  {:x x :y y :direction direction})


(defn receive [the-rover commands]
  
  (if (= :north (the-rover :direction))
    
    (if (= commands "l")
      (rover 0 0 :west)
      (rover 0 0 :east))
    (rover 0 0 :west)))

