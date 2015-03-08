(ns mars-rover.commands
  (:require [mars-rover.rover :as rover]))

(def commands-by-message
  {"r" rover/rotate-right
   "l" rover/rotate-left
   "f" rover/move-forwards
   "b" rover/move-backwards})

(defn create-from [messages]
  (map #(commands-by-message (str %)) messages))