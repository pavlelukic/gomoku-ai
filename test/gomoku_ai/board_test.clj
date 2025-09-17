(ns gomoku-ai.board-test
  (:require [midje.sweet :refer :all]
            [gomoku-ai.board :as board]))

(fact "The empty-board function creates a valid 15x15 board"
      (let [new-board (board/empty-board)]
        (count new-board) => 15
        (count (first new-board)) => 15
        (get-in new-board [7 7]) => 0))

(facts "About placing pieces"
       (let [board (board/empty-board)]
         (fact "a valid move updates the board"
               (let [new-board (board/place-piece board [5 5] 1)]
                 (get-in new-board [5 5]) => 1))
         
         (fact "an invalid move on a taken spot returns the board unchanged"
               (let [board-with-piece (board/place-piece board [5 5] 1)
                     board-after-invalid (board/place-piece board-with-piece [5 5] -1)]
                 board-after-invalid => board-with-piece))))

(facts "The check-winner function correctly identifies a winner"
      (let [board (board/empty-board)
            winning-line [[7 0] [7 1] [7 2] [7 3] [7 4]]
            winning-board (reduce (fn [b coords] (board/place-piece b coords -1))
                                  board
                                  winning-line)]
        (fact "it returns nil for a non-winning board"
              (board/check-winner board) => nil)
        
        (fact "it returns the winner and the correct line for a winning board"
              (let [result (board/check-winner winning-board)]
                (:winner result) => -1
                (:line result) => winning-line))))