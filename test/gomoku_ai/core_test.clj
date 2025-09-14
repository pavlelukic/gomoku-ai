(ns gomoku-ai.core-test
  (:require [midje.sweet :refer :all] 
            [gomoku-ai.core :as core]))

(facts "About getting player input"
       (fact "it correctly parses valid input"
             (core/get-player-input 1) => [7 7]
             (provided
              (core/prompt anything) => nil 
              (read-line) => "8 8"))
       (fact "it handles non-numeric input gracefully"
             (core/get-player-input 1) => nil
             (provided
              (core/prompt anything) => nil 
              (read-line) => "hello world"))
       
       (fact "it handles incomplete input gracefully"
             (core/get-player-input 1) => nil
             (provided
              (core/prompt anything) => nil 
              (read-line) => "5")))