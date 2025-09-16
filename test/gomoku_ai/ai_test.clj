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

(facts "About scoring a single line"
       (fact "it correctly scores offensive opporitunities for the AI"
             (ai/score-line [-1 -1 -1 -1 0]) => 10000
             (ai/score-line [0 -1 -1 -1 0]) => 100
             (ai/score-line [0 -1 -1 0 0]) => 10)
       
       (fact "it correctly scores defensive threats from the player"
             (ai/score-line [1 1 1 1 0]) => -50000
             (ai/score-line [0 1 1 1 0]) => -500
             (ai/score-line [0 1 1 0 0]) => -50)
       
       (fact "it gives no score to blocked or neutral lines"
             (ai/score-line [1 1 1 1 -1]) => 0
             (ai/score-line [1 -1 1 -1 0]) => 0
             (ai/score-line [0 0 0 0 0]) => 0))

(fact "evaluate-board correctly sums the scores of all lines"
      (let [test-board (-> (board/empty-board)
                           (board/place-piece [7 7] -1)
                           (board/place-piece [7 8] -1))]
        (ai/evaluate-board test-board) => 40))

(facts "About the smarter AI's move selection"
       (fact "1. The AI makes a winning move"
             (let [board-setup (-> (board/empty-board)
                                   (board/place-piece [7 1] -1)
                                   (board/place-piece [7 2] -1)
                                   (board/place-piece [7 3] -1)
                                   (board/place-piece [7 4] -1))]
               (ai/get-best-move board-setup) => [7 0]))
       (fact "2. The AI blocks the players winning move"
             (let [board-setup (-> (board/empty-board)
                                   (board/place-piece [1 5] 1)
                                   (board/place-piece [2 5] 1)
                                   (board/place-piece [3 5] 1)
                                   (board/place-piece [4 5] 1))]
               (ai/get-best-move board-setup) => [0 5]))
       (fact "3. The AI makes a random move if no opporitunity or threat exists."
             (let [board-setup (-> (board/empty-board)
                                   (board/place-piece [1 1] 1)
                                   (board/place-piece [5 5] -1)
                                   (board/place-piece [4 5] 1))
                   valid-moves (ai/get-valid-moves board-setup)
                   ai-choice (ai/get-best-move board-setup)]
               (true? (some #(= % ai-choice) valid-moves)) => true)))


