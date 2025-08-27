(ns gomoku-ai.board)

(def board-rows 15)
(def board-cols 15)

(defn empty-board
  "Creates a 15x15 board filled with 0s."
  []
  (vec (repeat board-rows (vec (repeat board-cols 0)))))