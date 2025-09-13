(ns gomoku-ai.core
  (:require [gomoku-ai.board :as board]
            [clojure.string :as str])
  (:gen-class))

(defn print-board [board]

  (print "   ") 
  (dotimes [c board/board-cols]
    (print (format "%-2d " (inc c))))
  (println)
  
  (dotimes [r board/board-rows]
    (print (format "%-2d " (inc r)))
    (doseq [c (range board/board-cols)]
      (let [piece (get-in board [r c])]
        (print
         (case piece
           0 ".  "
           1 "X  "
           -1 "O  "))))
    (println)))

(defn get-player-input [current-player]
  (let [player-char (if (= 1 current-player) "X" "O")]
    (println (str "\nPlayer " player-char "'s turn. Enter row and col (e.x. '3 5'): "))
    (flush)
    (try
      (let [input (str/split (read-line) #"\s+")
            row (Integer/parseInt (first input))
            col (Integer/parseInt (second input))]
        [(- row 1) (- col 1)])
      (catch Exception ex
        (println "Invalid input! Please enter two numbers from 1 to 15, separated by a space. (e.x. '3 5')")
        nil))))

(defn game-loop [board current-player]
  (print-board board)
  (let [winner (board/check-winner board)]
    (if winner
      (println (str "\nGame Over! Player " (if (= 1 winner) "X" "O") " wins!"))
      (if-let [coords (get-player-input current-player)]
        (let [new-board (board/place-piece board coords current-player)]
          (if (= board new-board)
            (do (println "Invalid move (spot is taken or out of bounds). Try again!")
                (recur board current-player))
            (recur new-board (* -1 current-player))))
        (recur board current-player)))))

(defn -main
  "Starts the Gomoku game."
  [& args] 
  (game-loop (board/empty-board) 1))