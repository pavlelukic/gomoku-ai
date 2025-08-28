(ns gomoku-ai.core
  (:require
   [reagent.core :as reagent :refer [atom]]
   [reagent.dom :as rd]
   [gomoku-ai.board :as board]))

(enable-console-print!)

(defonce app-state (r/atom {:board (board/empty-board)
                            :current-player 1
                            :game-over? false}))