(ns mars-rover.core)

(defn rover [x y direction]
  {:x x :y y :direction direction})

(defmulti rotate-left :direction)

(defmethod rotate-left :north [{x :x y :y}]
  (rover x y :west))

(defmethod rotate-left :south [{x :x y :y}]
  (rover x y :east))

(defmethod rotate-left :east [{x :x y :y}]
  (rover x y :north))

(defmethod rotate-left :west [{x :x y :y}]
  (rover x y :south))

(defmulti rotate-right :direction)

(defmethod rotate-right :north [{x :x y :y}]
  (rover x y :east))

(defmethod rotate-right :south [{x :x y :y}]
  (rover x y :west))

(defmethod rotate-right :east [{x :x y :y}]
  (rover x y :south))

(defmethod rotate-right :west [{x :x y :y}]
  (rover x y :north))

(defmulti move-forwards :direction)

(defmethod move-forwards :north [{x :x y :y direction :direction}]
  (rover x (inc y) direction))

(defmethod move-forwards :south [{x :x y :y direction :direction}]
  (rover x (dec y) direction))

(defmethod move-forwards :east [{x :x y :y direction :direction}]
  (rover (inc x) y direction))

(defmulti move-backwards :direction)

(defmethod move-backwards :north [{x :x y :y direction :direction}]
  (rover x (dec y) direction))

(defmethod move-backwards :south [{x :x y :y direction :direction}]
  (rover x (inc y) direction))

(defmethod move-backwards :east [{x :x y :y direction :direction}]
  (rover (dec x) y direction))

(def commands-by-signal 
  {"r" rotate-right
   "l" rotate-left
   "f" move-forwards
   "b" move-backwards})

(defn receive [the-rover signals]
  ((commands-by-signal signals) the-rover))
