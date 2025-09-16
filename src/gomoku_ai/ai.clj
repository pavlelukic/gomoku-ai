(ns gomoku-ai.ai
  (:require [gomoku-ai.board :as board]))

(defn get-valid-moves
  "Scans the game board and returns a list of all empty [row col] coordinates."
  [board]
  (for [row (range board/board-rows)
        col (range board/board-cols)
        :when (= 0 (get-in board [row col]))]
    [row col]))

(def ^:private all-lines
  "A pre-calculated list of all possible 5-in-a-row line coordinates"
  (memoize
   (fn []
     (concat
      ;; Horizontal lines
      (for [r (range board/board-rows), c (range (- board/board-cols 4))]
        (for [i (range 5)] [r (+ c i)]))
      ;; Vertical lines
      (for [r (range (- board/board-rows 4)), c (range board/board-cols)]
        (for [i (range 5)] [(+ r i) c]))
      ;; Diagonal (down-right \)
      (for [r (range (- board/board-rows 4)), c (range (- board/board-cols 4))]
        (for [i (range 5)] [(+ r i) (+ c i)]))
      ;; Diagonal (up-right /)
      (for [r (range 4 board/board-rows), c (range (- board/board-cols 4))]
        (for [i (range 5)] [(- r i) (+ c i)]))))))

(defn- score-line
  "Calculates a score for a single line of 5 pieces."
  [line]
  (let [counts (frequencies line)
        ai-pieces (get counts -1 0)
        human-pieces (get counts 1 0)]
    (cond
      ;; The AI wins or is close to winning
      (and (= ai-pieces 4) (= human-pieces 0)) 10000
      (and (= ai-pieces 3) (= human-pieces 0)) 100
      (and (= ai-pieces 2) (= human-pieces 0)) 10
      ;; The Player wins or is close to winning
      (and (= human-pieces 4) (= ai-pieces 0)) -50000
      (and (= human-pieces 3) (= ai-pieces 0)) -500
      (and (= human-pieces 2) (= ai-pieces 0)) -50
      :else 0)))


(defn find-winning-move
  "Checks if there's a line with four pieces for a given player and one empty spot.
   If found, returns the coordinates of the empty spot to make the winning move.
   (Also works for blocking the other players winning move)"
  [board player]
  (let [lines-with-coords (concat
                           ;; Horizontal lines
                           (for [r (range board/board-rows), c (range (- board/board-cols 4))]
                             (for [i (range 5)] {:piece (get-in board [r (+ c i)]) :coords [r (+ c i)]}))
                           ;; Vertical lines
                           (for [r (range (- board/board-rows 4)), c (range board/board-cols)]
                             (for [i (range 5)] {:piece (get-in board [(+ r i) c]) :coords [(+ r i) c]}))
                           ;; Diagonal (down-right \)
                           (for [r (range (- board/board-rows 4)), c (range (- board/board-cols 4))]
                             (for [i (range 5)] {:piece (get-in board [(+ r i) (+ c i)]) :coords [(+ r i) (+ c i)]}))
                           ;; Diagonal (up-right /)
                           (for [r (range 4 board/board-rows), c (range (- board/board-cols 4))]
                             (for [i (range 5)] {:piece (get-in board [(- r i) (+ c i)]) :coords [(- r i) (+ c i)]})))]
    (some (fn [line]
            (let [pieces (map :piece line)
                  counts (frequencies pieces)]
              (when (and (= (get counts player 0) 4)
                         (= (get counts 0 0) 1))
                (:coords (first (filter #(= 0 (:piece %)) line))))))
          lines-with-coords)))

(defn get-best-move
  "The AI's main decision function."
  [board]
  (or
   ;;1. Find winning move for AI if possible
   (find-winning-move board -1)
   ;;2. Block players winning move if possible
   (find-winning-move board 1)
   ;;3. Otherwise, make a random move
   (rand-nth (get-valid-moves board))))