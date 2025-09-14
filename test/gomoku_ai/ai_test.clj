(ns gomoku-ai.ai-test
  (:require [midje.sweet :refer :all] 
            [gomoku-ai.ai :as ai] 
            [gomoku-ai.board :as board]))

(facts "About finding valid moves"
       (fact "it finds all 255 moves on an empty board"
             (let [empty-board (board/empty-board)]
               (count (ai/get-valid-moves empty-board)) => 225))

       (fact "it finds the correct number of moves on a partially filled board"
             (let [test-board (-> (board/empty-board)
                                  (board/place-piece [1 3] 1)
                                  (board/place-piece [5 6] -1)
                                  (board/place-piece [1 4] 1)
                                  (board/place-piece [5 8] -1))]
               (count (ai/get-valid-moves test-board)) => 221)))

(facts "About the random AI's move selection"
       (fact "get-best-move always returns a valid, empty spot"
             (let [test-board (-> (board/empty-board)
                                  (board/place-piece [1 1] 1)
                                  (board/place-piece [1 2] -1))
                   valid-moves (ai/get-valid-moves test-board)
                   ai-choice (ai/get-best-move test-board)]
                (true? (some #(= % ai-choice) valid-moves)) => true)))


