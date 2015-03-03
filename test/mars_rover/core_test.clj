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
        (rover 0 0 :north)
        "r") => (rover 0 0 :east))
    
    (fact 
      "it turns left"
      (receive 
        (rover 0 0 :north) 
        "l") => (rover 0 0 :west)))
  
  (facts
    "when facing south"
    
    (fact 
      "it turns right"
      (receive
        (rover 0 0 :south)
        "r") => (rover 0 0 :west))
    
    (fact 
      "it turns left"
      (receive
        (rover 0 0 :south)
        "l") => (rover 0 0 :east)))
  
  (facts
    "when facing east"
    
    (fact 
      "it turns right"
      (receive
        (rover 0 0 :east)
        "r") => (rover 0 0 :south))
    
    (fact 
      "it turns left"
      (receive
        (rover 0 0 :east)
        "l") => (rover 0 0 :north))))
