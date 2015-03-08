(ns mars-rover.core
  (:require [mars-rover.commands :as commands]
            [mars-rover.worlds :refer (infinite-world hit-obstacle?)]))

(defn- make-apply-command [world]
  (fn apply-command [rover command]
    (let [new-rover ((world :wrap-fn) (command rover))]
      (if (hit-obstacle? new-rover (world :obstacles))
        rover
        new-rover))))

(defn- validate-initial-position [rover {obstacles :obstacles}]
  (when (hit-obstacle? rover obstacles)
    (throw (IllegalArgumentException. 
             "Initial position is on an obstacle!"))))

(defn receive [rover messages & {world :world :or {world infinite-world}}]
  (let [apply-command (make-apply-command world)]
    (validate-initial-position rover world)
    (reduce apply-command 
            rover 
            (commands/create-from messages))))