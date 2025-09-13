(ns gomoku-ai.core
  (:require [gomoku-ai.board :as board])
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

(defn -main
  "Creates an empty board, prints it once, and exits."
  [& args]
  (println "--- Testing print-board ---")
  (print-board (board/empty-board)))