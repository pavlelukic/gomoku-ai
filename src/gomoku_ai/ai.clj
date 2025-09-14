(ns gomoku-ai.ai
  (:require [gomoku-ai.board :as board]))

(defn get-valid-moves
  "Scans the game board and returns a list of all empty [row col] coordinates."
  [board]
  (for [row (range board/board-rows)
        col (range board/board-cols)
        :when (= 0 (get-in board [row col]))]
    [row col]))

(defn get-best-move
  "Very simple AI which finds all valid moves and picks one at random."
  [board]
  (rand-nth (get-valid-moves board)))