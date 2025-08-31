(ns gomoku-ai.board 
  (:require
    [gomoku-ai.board :as board]))

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

(defn check-winner
  "Checks the board for a winner.
   Returns 1 or 2 if a player has won, otherwise returns nil."
  [board]
  (let [lines (concat
               ;;Horizontal lines
               (for [r (range board-rows), c (range (- board-cols 4))]
                 (for [i (range 5)] (get-in board [r (+ c i)])))
               ;;Vertical lines
               (for [r (range (- board-rows 4)), c (range board-cols)]
                 (for [i (range 5)](get-in board [(+ r i) c]))))]
    
    (some (fn [line]
            (let [first-piece (first line)]
              (when (and (not= 0 first-piece) (every? #(= first-piece %) line))
                first-piece)))
          lines)))