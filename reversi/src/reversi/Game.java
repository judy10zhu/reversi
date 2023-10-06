package reversi;

import java.util.HashMap;
import java.util.Scanner;

public class Game {
	private char currentPlayer = 'O';
	int numMoves = 0;

	public boolean isTerminal(int[][] state) {

		for (int i = 0; i < state.length; i++) {
			for (int j = 0; j < state[0].length; j++) {
				if (state[i][j] == 0) {
					return false;
				}
			}
		}


		for (int i = 0; i < state.length; i++) {
			for (int j = 0; j < state[0].length; j++) {
				int[] move = {i, j};
				for (int player = 1; player <= 2; player++) {
					if (isValid(state, move, player)) {
						return false;
					}
				}
			}
		}

		return true;
	}


	public boolean isValid(int[][] state, int[] move, int player) {
		int row = move[0];
		int col = move[1];
		int x;
		int y;
		boolean valid = false;

		if (move[0] < 0 || move[1] < 0 || move[0] >= state.length || move[1] >= state[0].length) {
			return false;
		}

		if (state[move[0]][move[1]] != 0) {
			return false;
		}

		int opponent;
		if (player == 1) {
			opponent = 2;
		} else {
			opponent = 1;
		}
		// Check if next to opponent
		for (int i = -1; i <= 1; i++) {
			row = move[0] + i;
			for (int j = -1; j <= 1; j++) {
				col = move[1] + j;
				if (row >= 0 && col >= 0 && row < state.length && col < state[0].length && state[row][col] == opponent) {
					x = row + i;
					y = col + j;
					while (x >= 0 && y >= 0 && x < state.length && y < state[0].length && state[x][y] == opponent) {
						x += i;
						y += j;
					}
					if (x >= 0 && y >= 0 && x < state.length && y < state[0].length && state[x][y] == player) {
						valid = true;
					}
				}
			}
		}

		return valid;
	}

	public void playReversi(int[][] board, int boardSize, int opponent, char symbol) {
		Game game = new Game();
		Random random = new Random();
		Scanner scnr = new Scanner(System.in);
		HashMap<String, Integer> colInput = new HashMap<>();
		colInput.put("a", 1);
		colInput.put("b", 2);
		colInput.put("c", 3);
		colInput.put("d", 4);

		colInput.put("e", 5);
		colInput.put("f", 6);
		colInput.put("g", 7);
		colInput.put("h", 8);

		int player;
		int cPlayer; // computer aka opponent

		if(symbol == 'X') {
			player = 1;
			cPlayer = 2;
		}
		else {
			player = 2;
			cPlayer = 1;
		}

		boolean isValid = false;
		int currentPlayer = 1;

		//different situations
		if (boardSize == 1) {
			// 4X4 Reversi game
			print4x4Board(board);
			System.out.println();

			while (!game.isTerminal(board)) {
				if (currentPlayer == 2) {
					System.out.println("Next to play: LIGHT/O");
				}
				else {
					System.out.println("Next to play: DARK/X");
				}
				System.out.println();

				if(currentPlayer == player) {
					if(game.skipTurn(board, currentPlayer)) {
						currentPlayer = cPlayer;
						if (game.skipTurn(board, currentPlayer)) {
							System.out.println("Both players have no valid moves, game ends.");
							endGame(board);
							break;
						}
						isValid = true;
					}
					while(!isValid && !game.skipTurn(board, currentPlayer)) {

						System.out.println("Enter the row (1 - 4) where you want to place your disk:");
						int row = scnr.nextInt();
						scnr.nextLine();

						System.out.println("Enter the column (a - d) where you want to place your disk:");
						int col = 0;
						String userInput = scnr.nextLine();
						if (colInput.containsKey(userInput)) {
							col = colInput.get(userInput);
							System.out.println("Column number: " + col);
						} else {
							System.out.println("Invalid choice. Please enter a, b, c, or d.");
						}


						int[] move = {row - 1, col - 1};

						if (game.isValid(board, move, player)) {
							game.flipOpponent(board, (row - 1), (col - 1), currentPlayer);
							board[row - 1][col - 1] = player;
							print4x4Board(board);
							System.out.println();
							currentPlayer = cPlayer;
							//        System.out.println(currentPlayer);
							isValid = true;
						} else {
							System.out.println("The place you entered is not valid. Please try again.");
						}
					}
				} 
				else if (currentPlayer == cPlayer){

					boolean isMaximize = true;
					if (opponent == 1 && !game.skipTurn(board, currentPlayer)) {
						// random opponent
						long start = System.currentTimeMillis();
						int[] move = random.randomMove(board);
						long end = System.currentTimeMillis();
						game.flipOpponent(board, move[0], move[1], currentPlayer);
						board[move[0]][move[1]] = cPlayer;
						print4x4Board(board);
						long time = end - start;

						System.out.println("Elapsed time: " + time);
						currentPlayer = player;
						//       System.out.println(currentPlayer);
					} 
					else if (opponent == 2 && !game.skipTurn(board, currentPlayer)) {
						//MiniMax
						long start = System.currentTimeMillis();
						Minimax minimax = new Minimax();
						int[] move = minimax.optimalMove(board, 3, player, isMaximize);
						long end = System.currentTimeMillis();

						game.flipOpponent(board, move[0], move[1], currentPlayer);
						board[move[0]][move[1]] = cPlayer;
						print4x4Board(board);
						long time = end - start;
						System.out.println("Elapsed time: " + time*1.00);
						currentPlayer = player;
					} else if (opponent == 3) {
						//  alpha-beta pruning opponent
						boolean isMinimize = true;
						int alpha = Integer.MIN_VALUE;
						int beta = Integer.MAX_VALUE;
						int depth = 3;
						Abminimax abminimax = new Abminimax();
						long start = System.currentTimeMillis();
						int[] move = abminimax.abOptimal(board, depth - 1, player,alpha, beta, !isMinimize );
						long end = System.currentTimeMillis();

						game.flipOpponent(board, move[0], move[1], currentPlayer);
						board[move[0]][move[1]] = cPlayer;
						print4x4Board(board);
						long time = end - start;
						System.out.println("Elapsed time: " + time*1.00);
						currentPlayer = player;

					} else if (opponent == 4) {
						// H-minimax opponent
						long start = System.currentTimeMillis();
						H_Minimax h_minimax = new H_Minimax();
						int[] move = h_minimax.findOptimalMove(board, 3, player, isMaximize);
						long end = System.currentTimeMillis();

						game.flipOpponent(board, move[0], move[1], currentPlayer);
						board[move[0]][move[1]] = cPlayer;
						print4x4Board(board);
						long time = end - start;
						System.out.println("Elapsed time: " + time*1.00);
						currentPlayer = player;

					} else if (game.skipTurn(board, currentPlayer)) {
						currentPlayer = player;
						if (game.skipTurn(board, currentPlayer)) {
							System.out.println("Both players have no valid moves, game ends.");
							endGame(board);
							break;
						}
						continue;
					}
				}
				isValid = false;
			}
			endGame(board);
		}
		else if (boardSize == 2) {
			// 8X8 Reversi game
			boolean isMaximize = true;
			print8x8Board(board);
			System.out.println();

			while (!game.isTerminal(board)) {
				if (currentPlayer == 2) {
					System.out.println("Next to play: LIGHT/O");
				}
				else {
					System.out.println("Next to play: DARK/X");
				}
				System.out.println();

				if(currentPlayer == player) {
					if(game.skipTurn(board, currentPlayer)) {
						currentPlayer = cPlayer;
						if (game.skipTurn(board, currentPlayer)) {
							System.out.println("Both players have no valid moves, game ends.");
							endGame(board);
							break;
						}
						isValid = true;
					}
					while(!isValid && !game.skipTurn(board, currentPlayer)) {

						System.out.println("Enter the row (1 - 8) where you want to place your disk:");
						int row = scnr.nextInt();
						scnr.nextLine();
						System.out.println("Enter the column (a - h) where you want to place your disk:");
						int col = 0;
						String userInput = scnr.nextLine();
						if (colInput.containsKey(userInput)) {
							col = colInput.get(userInput);
							System.out.println("Column number: " + col);
						} else {
							System.out.println("Invalid choice. Please enter a, b, c, d, e, f, g, or h.");
						}

						int[] move = {row - 1, col - 1};

						if (game.isValid(board, move, player)) {
							game.flipOpponent(board, (row - 1), (col - 1), currentPlayer);
							board[row - 1][col - 1] = player;
							print8x8Board(board);
							System.out.println();
							currentPlayer = cPlayer;
							//        System.out.println(currentPlayer);
							isValid = true;
						} else {
							System.out.println("The place you entered is not valid. Please try again.");
						}
					}
				} 
				else if (currentPlayer == cPlayer){

					if (opponent == 1 && !game.skipTurn(board, currentPlayer)) {
						// random opponent
						long start = System.currentTimeMillis();
						int[] move = random.randomMove(board);
						long end = System.currentTimeMillis();
						game.flipOpponent(board, move[0], move[1], currentPlayer);
						board[move[0]][move[1]] = cPlayer;
						print8x8Board(board);
						long time = end - start;
						System.out.println("Elapsed time: " + time*1.00);
						currentPlayer = player;
						//       System.out.println(currentPlayer);
					} 
					else if (opponent == 2 && !game.skipTurn(board, currentPlayer)) {
						//MiniMax

						Minimax minimax = new Minimax();
						long start = System.currentTimeMillis();
						int[] move = minimax.optimalMove(board, 5, player, isMaximize);
						long end = System.currentTimeMillis();
						game.flipOpponent(board, move[0], move[1], currentPlayer);
						board[move[0]][move[1]] = cPlayer;
						print8x8Board(board);
						long time = end - start;
						System.out.println("Elapsed time: " + time*1.00);
						currentPlayer = player;
					} else if (opponent == 3) {
						//  alpha-beta pruning opponent
						boolean isMinimize = true;
						int alpha = Integer.MIN_VALUE;
						int beta = Integer.MAX_VALUE;
						int depth = 5;
						Abminimax abminimax = new Abminimax();
						long start = System.currentTimeMillis();
						int[] move = abminimax.abOptimal(board, depth - 1, player,alpha, beta, !isMinimize);
						long end = System.currentTimeMillis();

						game.flipOpponent(board, move[0], move[1], currentPlayer);
						board[move[0]][move[1]] = cPlayer;
						print8x8Board(board);
						long time = end - start;
						System.out.println("Elapsed time: " + time*1.00);
						currentPlayer = player;

					} else if (opponent == 4) {
						// H-minimax opponent
						long start = System.currentTimeMillis();
						H_Minimax h_minimax = new H_Minimax();
						int[] move = h_minimax.findOptimalMove(board, 5, player, isMaximize);
						long end = System.currentTimeMillis();

						game.flipOpponent(board, move[0], move[1], currentPlayer);
						board[move[0]][move[1]] = cPlayer;
						print8x8Board(board);
						long time = end - start;
						System.out.println("Elapsed time: " + time*1.00);
						currentPlayer = player;

					} else if (game.skipTurn(board, currentPlayer)) {
						currentPlayer = player;
						if (game.skipTurn(board, currentPlayer)) {
							System.out.println("Both players have no valid moves, game ends.");
							endGame(board);
							break;
						}
						continue;
					}
				}
				isValid = false;
			}
			endGame(board);
		}
	}




	public void flipOpponent(int[][] board, int row, int col, int player) {
		int opponent;
		if (player == 1) {
			opponent = 2;
		} else {
			opponent = 1;
		}

		int[][] disksAround = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
		for (int[] each : disksAround) {
			int newR = row + each[0];
			int newC = col + each[1];

			// Keep searching
			while (newR >= 0 && newR < board.length && newC >= 0 && newC < board[0].length) {
				if (board[newR][newC] != opponent) {
					break;
				}
				newR = newR + each[0];
				newC = newC + each[1];
			}

			if (newR < 0 || newR >= board.length || newC < 0 || newC >= board[0].length || board[newR][newC] == 0) {
				continue;
			}

			// flip the disk
			if (board[newR][newC] == player) {
				newR = newR - each[0];
				newC = newC - each[1];
				while (newR != row || newC != col) {
					board[newR][newC] = player;
					newR = newR - each[0];
					newC = newC - each[1];
				}
			}
		}
	}

	// public void opponentPlay(int opponent, int[][] board4x4Content, int currentPlayer, int cPlayer) {
	//  int player;
	//  if (opponent == 1) {
	//   player = 2;
	//   } else {
	//   player = 1;
	//   }
	//     Random random = new Random();
	//     if (opponent == 1 && !skipTurn(board4x4Content, currentPlayer)) {
	//       // random opponent
	//       int[] move = random.randomMove(board4x4Content);
	//       flipOpponent(board4x4Content, move[0], move[1], currentPlayer);
	//       board4x4Content[move[0]][move[1]] = cPlayer;
	//       print4x4Board(board4x4Content);
	//       currentPlayer = player;
	//     } else if (opponent == 2 && !skipTurn(board4x4Content, currentPlayer)) {
	//       //MiniMax
	//       Minimax minimax = new Minimax();
	//       int[] move = minimax.optimalMove(board4x4Content);
	//       flipOpponent(board4x4Content, move[0], move[1], currentPlayer);
	//       board4x4Content[move[0]][move[1]] = cPlayer;
	//       print4x4Board(board4x4Content);
	//       currentPlayer = player;
	//     } else if (opponent == 3) {
	//       //  alpha-beta pruning opponent
	//
	//     } else if (opponent == 4) {
	//       // H-minimax opponent
	//
	//     } else if (skipTurn(board4x4Content, currentPlayer)) {
	//       currentPlayer = cPlayer;
	//     }
	// }

	public static void endGame(int[][] board) {
		int player1 = 0;
		int player2 = 0;

		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) { 
				if(board[i][j] == 1) {
					player1 += 1; 
				}
				else if (board[i][j] == 2) {
					player2 += 1;
				}
			}
		}
		System.out.println();
		if (player1 > player2) {
			System.out.println("X: " + player1 + ", O: " + player2);
			System.out.println("DARK/X wins");
		}
		else if (player1 == player2) {
			System.out.println("X: " + player1 + ", O: " + player2);
			System.out.println("It's a tie");
		}
		else {
			System.out.println("X: " + player1 + ", O: " + player2);
			System.out.println("LIGHT/O wins");
		}
	}


	public static void print4x4Board(int[][] board) {
		System.out.println("  A B C D");
		for(int i = 0; i < 4; i++) {
			System.out.print(i + 1 + " "); 
			for(int j = 0; j < 4; j++) { 
				if(board[i][j] == 0) {
					System.out.print("  "); 
				}
				else if (board[i][j] == 1) {
					System.out.print("X ");
				}
				else if (board[i][j] == 2) {
					System.out.print("O ");
				}
			}
			System.out.print(i + 1 + " ");
			System.out.println(); 
		}
		System.out.println("  A B C D");
		//  System.out.println(board[0][1]);
	}

	public static void print8x8Board(int[][] board) {
		System.out.println("  A B C D E F G H");
		for(int i = 0; i < 8; i++) {
			System.out.print(i + 1 + " "); 
			for(int j = 0; j < 8; j++) { 
				if(board[i][j] == 0) {
					System.out.print("  "); 
				}
				else if (board[i][j] == 1) {
					System.out.print("X ");
				}
				else if (board[i][j] == 2) {
					System.out.print("O ");
				}
			}
			System.out.print(i + 1 + " ");
			System.out.println(); 
		}
		System.out.println("  A B C D E F G H");
	}





	public static boolean checkBoardFull(int[][] board) {
		int bSize = board.length;
		for(int i = 0; i < bSize; i++) {
			for(int j = 0; j < bSize; j++) {
				if (board[i][j] == 0) {
					return false; // return false (board is not full) if there's an empty space
				}
			}
		}
		return true;
	}


	public boolean skipTurn(int[][] board, int player) {
		// Check valid moves for the current player
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				int[] move = {i,j};
				if (isValid(board, move, player)) {
					return false;
				}
			}
		}

		//no valid->skip
		System.out.println("Player " + player + " has no valid moves, skipping turn.");
		return true;
	}

}