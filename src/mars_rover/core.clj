(ns mars-rover.core)

(defn rover [x y direction]
  {:x x :y y :direction direction})


(defn receive [{x :x y :y direction :direction} commands]
  
  (cond (= :north direction) (if (= commands "l")
                               (rover 0 0 :west)
                               (rover 0 0 :east))
        
        (= :south direction) (if (= commands "l")
                               (rover 0 0 :east)
                               (rover 0 0 :west))
        
        (= :east direction) (if (= commands "l")
                              (rover 0 0 :north)
                              (rover 0 0 :south))
        
        :else (if (= commands "l")
                (rover 0 0 :south)
                (rover 0 0 :north))))

