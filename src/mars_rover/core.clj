(ns mars-rover.core)

(defn rover [x y direction]
  {:x x :y y :direction direction})

(defn square-world [x y size]
  {:dimensions {:x x :y y :size size}
   :inside? (fn [{x-rover :x y-rover :y}] 
              (and
                (<= (Math/abs (- x-rover x)) size)
                (<= (Math/abs (- y-rover y)) size)))})

(def infinite-world 
  {:inside? (fn [{x-rover :x y-rover :y}] true)
  :dimensions {}})

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

(defmethod move-forwards :west [{x :x y :y direction :direction}]
  (rover (dec x) y direction))

(defmulti move-backwards :direction)

(defmethod move-backwards :north [{x :x y :y direction :direction}]
  (rover x (dec y) direction))

(defmethod move-backwards :south [{x :x y :y direction :direction}]
  (rover x (inc y) direction))

(defmethod move-backwards :east [{x :x y :y direction :direction}]
  (rover (dec x) y direction))

(defmethod move-backwards :west [{x :x y :y direction :direction}]
  (rover (inc x) y direction))

(def commands-by-signal 
  {"r" rotate-right
   "l" rotate-left
   "f" move-forwards
   "b" move-backwards})

(defn commands [messages]
  (map #(commands-by-signal (str %))
       messages))

(defn apply-command [{rover :rover {dimensions :dimensions inside-world? :inside?} :world :as rover-and-world} command]
  (let [new-rover (command rover)]
    (if (inside-world? new-rover)
      (assoc rover-and-world :rover new-rover)
      rover-and-world)))

(defn receive [rover messages & {world :world :or {world infinite-world}}]
  (:rover 
    (reduce apply-command 
            {:rover rover :world world}
            (commands messages))))