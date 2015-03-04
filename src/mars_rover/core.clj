(ns mars-rover.core)

(defn rover [x y direction]
  {:x x :y y :direction direction})

(defn square-world [x y size & obstacles]
  {:wrap-fn (fn [{x-rover :x y-rover :y :as rover}] 
              (cond 
                (and (> y-rover y) (> (- y-rover y) size)) (assoc-in rover [:y] (- y-rover size))
                (and (< y-rover y) (< (- y y-rover) size)) (assoc-in rover [:y] (+ y-rover size))
                (and (> x-rover x) (> (- x-rover x) size)) (assoc-in rover [:x] (- x-rover size))
                (and (< x-rover x) (< (- x x-rover) size)) (assoc-in rover [:x] (+ x-rover size))
                :else rover))
   :obstacles obstacles})

(def infinite-world 
  {:wrap-fn (fn [rover] rover)
   :obstacles []})

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

(defn hit-obstacle? [{x-rover :x y-rover :y} obstacles]
  (= (some #{{:x x-rover :y y-rover}} obstacles) {:x x-rover :y y-rover}))

(defn apply-command [{rover :rover {obstacles :obstacles wrap :wrap-fn} :world :as rover-and-world} command]
  (let [new-rover (wrap (command rover))]
    (if (hit-obstacle? new-rover obstacles)
      rover-and-world
      (assoc rover-and-world 
        :rover new-rover))))

(defn receive [rover messages & {world :world :or {world infinite-world}}]
  (if (hit-obstacle? rover (world :obstacles))
    (throw (IllegalArgumentException. "Initial position is on an obstacle!"))
    (:rover (reduce apply-command 
              {:rover rover :world world}
              (commands messages)))))