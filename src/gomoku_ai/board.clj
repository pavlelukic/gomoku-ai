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

(defn check-winner
  "Checks the board for a winner in all four directions.
  Returns a map with the winner and the winning line, otherwise returns nil."
  [board]
  (let [lines-with-coords (concat
               ;; Horizontal lines
               (for [r (range board-rows), c (range (- board-cols 4))]
                 (for [i (range 5)] [r (+ c i)]))
               ;; Vertical lines
               (for [r (range (- board-rows 4)), c (range board-cols)]
                 (for [i (range 5)] [(+ r i) c]))
               ;; Diagonal (down-right \)
               (for [r (range (- board-rows 4)), c (range (- board-cols 4))]
                 (for [i (range 5)] [(+ r i) (+ c i)]))
               ;; Diagonal (up-right /)
               (for [r (range 4 board-rows), c (range (- board-cols 4))]
                 (for [i (range 5)] [(- r i) (+ c i)])))]

    (some (fn [line-coords]
            (let [pieces (map #(get-in board %) line-coords)
                  first-piece (first pieces)]
              (when (and (not= 0 first-piece) (every? #(= first-piece %) pieces))
                {:winner first-piece, :line line-coords})))
                lines-with-coords)))