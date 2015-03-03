(ns mars-rover.core)

(defn rover [x y direction]
  {:x x :y y :direction direction})

(defmulti rotate 
  (fn [the-rover command] 
    [(:direction the-rover) command]))

(defmethod rotate [:north "l"] [{x :x y :y} _]
    (rover x y :west))

(defmethod rotate [:north "r"] [{x :x y :y} _]
    (rover x y :east))

(defn receive [{x :x y :y direction :direction :as the-rover} commands]
  
  (cond (= :north direction) (rotate the-rover commands)
        
        (= :south direction) (if (= commands "l")
                               (rover 0 0 :east)
                               (rover 0 0 :west))
        
        (= :east direction) (if (= commands "l")
                              (rover 0 0 :north)
                              (rover 0 0 :south))
        
        :else (if (= commands "l")
                (rover 0 0 :south)
                (rover 0 0 :north))))

