# Gomoku AI

A classic Gomoku (Five-in-a-Row) game implemented in Clojure, playable from the command line. This project features multiple game modes, including a challenging AI opponent with a heuristic-based strategy. This was developed as a master's project using test-driven development and AI fundamentals in a functional programming paradigm.

## Features

- **Classic Gomoku Rules**: The first player to make an unbroken line of five of their pieces (horizontally, vertically, diagonally (both ways)) is the winner.
- **Interactive Console AI**: A clean, simple and easy to use command line interface that can run in any standard Terminal (Linux, Windows, MacOS...)
- **Multi-player game modes**:
  - **Human vs. Human**: A classic turn based local two-player mode.
  - **Human vs. AI (Easy)**: Play against an AI with very simple game logic.
  - **Human vs. AI (Hard)**: Play against a much smarter AI that uses heuristic evaluation to make strategic game moves.
- **Clear AI Feedback**: To make the game more intuitive and easier to follow, the AI announces the coordinates for each of it's turns.
- **Win/Loss/Draw Detection**: The game correctly identifies when a player has won the game or declares a Draw when there are no more valid moves to be played
- **Winning Line Highlight**: When a game is won, the final board is reprinted with a clearly stated winning line, in case someone missed it.

* **Comprehensive Test Suite**: The project was built by following the rules of test-driven devolpment, so it naturally has a lot of tests. 29 to be exact, for various functions within the project.

---

## Tech Stack

- **Programming Language**: Clojure
- **Build Tool**: Leiningen
- **Testing Framework**: Midje
- **Development Environments**: VS Code / Calva

---

## Getting Started

To get the project up and running on your local machine, follow these instructions.

### Prerequisites

- A Java Development Kit (JDK), version 11 or higher (I used 17)

* The [Leiningen](https://leiningen.org) build tool for Clojure.

### Installation and Running

1. **Clone the repo:**

   ```bash
    git clone [https://github.com/pavlelukic/gomoku-ai.git](https://github.com/pavlelukic/gomoku-ai.git)
    cd gomoku-ai
   ```

2. **Run the Application:**

   This command will download all necessary dependencies and start the game.

   ```bash
   lein run
   ```

   You will be greeted by the main menu where you can choose your game mode or exit.

### Running the Midje Tests

To run the complete suite of Midje unit tests, run the following command from the project's root directory:

```bash
lein midje
```

## How to Play

1. Start the Gomoku game by running the `lein run` command in your terminal. (Make sure that you are in the root directory of the project)
2. Choose a game mode from the main menu. If you want to win, choose `2`, but if you want a real challenge try number `3`. You can also play with a friend by choosing option `1`.
3. An empty 15x15 board will be displayed in the command line window. The players are represented by `X` (Player 1, always human) and `O` (Also human, or AI). It's better than Tic-Tac-Toe I swear.
4. When its your turn, enter the coordinates to your move in the form of two numbers separated by a space. For example you can play `8 10` and that will place your piece in the 8th row and 10th column. As mentioned before, the board is numbered from 1 to 15 for both rows and columns.
5. The game continues until a player places 5 uninterrupted pieces in a row (horizontal, vertical, diagonal: right-down (\) and right-up (/)). The game can also be interrupted if there are no more winning possibilites for both players, resulting in a draw.

## License

Pavle Lukić, Fakultet Organizacionih Nauka, 2025

Feel free to use and modify this software!
