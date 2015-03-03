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

(defmethod move-forwards :north [{x :x y :y}]
  (rover x (inc y) :north))

(defmethod move-forwards :south [{x :x y :y}]
  (rover x (dec y) :south))

(defmulti move-backwards :direction)

(defmethod move-backwards :north [{x :x y :y}]
  (rover x (dec y) :north))

(def commands-by-signal 
  {"r" rotate-right
   "l" rotate-left
   "f" move-forwards
   "b" move-backwards})

(defn receive [the-rover signals]
  ((commands-by-signal signals) the-rover))
