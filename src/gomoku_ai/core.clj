(ns gomoku-ai.core
  (:require [gomoku-ai.board :as board]
            [gomoku-ai.ai :as ai]
            [clojure.string :as str])
  (:gen-class))

(defn choose-game-mode []
  (loop []
    (println "\n*** GOMOKU ***")
    (println "\nChoose your game mode: ")
    (println "\n1. Human vs. Human")
    (println "2. Human vs. AI (Easy)")
    (println "3. Human vs. AI (Hard)")
    (println "4. Exit the game")
    (print "\n> ")
    (flush)
    (let [choice (read-line)]
      (case choice
        "1" :human
        "2" :ai-easy
        "3" :ai-hard
        "4" :exit
        (do (println "Invalid choice! Please enter 1, 2, 3 or 4.")
            (recur))))))

(defn print-board
  ([board] (print-board board nil))
  ([board winning-line]
   (let [winning-coords (set winning-line)]
     (print "   ")
     (dotimes [c board/board-cols]
       (print (format "%-2d " (inc c))))
     (println)
     (dotimes [r board/board-rows]
       (print (format "%-2d " (inc r)))
       (doseq [c (range board/board-cols)]
         (let [piece (get-in board [r c])]
           (print
            (if (contains? winning-coords [r c])
              "#  " 
              (case piece 
                0 ".  " 
                1 "X  " 
                -1 "O  "))))) 
       (println)))))
   
(defn prompt [message]
  (println message)
  (flush))

(defn get-player-input [current-player]
  (let [player-char (if (= 1 current-player) "X" "O")]
    (prompt (str "\nPlayer " player-char "'s turn. Enter row and col (e.x., '3 5'):  "))
    (try
      (let [input (str/split (read-line) #"\s+")
            row (Integer/parseInt (first input))
            col (Integer/parseInt (second input))]
        [(- row 1) (- col 1)])
      (catch Exception ex
        (println "Invalid input! Please enter two numbers from 1 to 15, separated by a space. (e.x. '3 5')\n")
        nil))))

(defn game-loop [board current-player game-mode]
  (print-board board) 
  (if-let [result (board/check-winner board)] 
    (do
      (println (str "\nGame Over! Player " (if (= 1 (:winner result)) "X" "O") " wins!"))
      (println "Winning line highlighted:")
      (print-board board (:line result))) 
    (if (empty? (ai/get-valid-moves board))
      (println "\nGame Over! It's a draw.")
      (if (= 1 current-player)
        ;; Player X's Turn (Human)
        (if-let [coords (get-player-input current-player)]
          (let [new-board (board/place-piece board coords current-player)]
            (if (= board new-board)
              (do (println "Invalid move. Try again.") (recur board current-player game-mode))
              (recur new-board -1 game-mode)))
          (recur board current-player game-mode))
        ;; Player O's Turn (Human or AI)
        (if (= game-mode :human)
          ;; Human
          (if-let [coords (get-player-input current-player)]
            (let [new-board (board/place-piece board coords current-player)]
              (if (= board new-board)
                (do (println "Invalid move. Try again.") (recur board current-player game-mode))
                (recur new-board 1 game-mode)))
            (recur board current-player game-mode))
          ;; AI
          (do
            (println "\nAI's turn (Player O)...")
            (Thread/sleep 1000)
            (let [ai-move (ai/get-best-move board game-mode)
                  [row col] ai-move]
              (println (str "AI places piece at row " (inc row) ", col " (inc col) "."))
              (let [new-board (board/place-piece board ai-move -1)]
                (recur new-board 1 game-mode)))))))))
            
(defn -main
  "Starts the Gomoku game after asking for the game mode."
  [& args] 
  (let [game-mode (choose-game-mode)]
    (when (not= game-mode :exit)
      (game-loop (board/empty-board) 1 game-mode))))