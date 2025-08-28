(ns gomoku-ai.board)

(def board-rows 15)
(def board-cols 15)

(defn empty-board
  "Creates a 15x15 board filled with 0s."
  []
  (vec (repeat board-rows (vec (repeat board-cols 0)))))

(defn place-piece
  "Places a player's piece on the board at a specific [row col].
   Returns the new updated board if the move is valid, otherwise nil."
  [board [row col] player]
  (if (= 0 (get-in board [row col]))
    (assoc-in board [row col] player)
    board))