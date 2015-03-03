(ns mars-rover.core-test
  (:use midje.sweet)
  (:use [mars-rover.core]))


(facts 
  "about mars rover"
  
  (facts 
    "when facing north"
    
    (fact 
      "it turns right"
      (receive 
        {:x 0 :y 0 :direction :north} 
        "r") => {:x 0 :y 0 :direction :east})))
