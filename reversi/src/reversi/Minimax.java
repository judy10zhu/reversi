package reversi;

import java.util.*;

public class Minimax {
	private int player;
	private int maxDepth = 3;

	//   public int[] optimalMove(int[][] state) {
	//     player = toMove(state);
	//     //System.out.println("I'm at optimalMove!" + player);
	//     int[] move = maxValue(state);
	//     System.out.println(move);
	//     return move;
	//   }




	private int minimax(int[][] state, int depth, int player, boolean isMinimize) {
		if (depth == 0 || isTerminal(state)) {
			return utility(state, player);
		}
		int maxValue, minValue, value;
		if (isMinimize) {
			maxValue = Integer.MIN_VALUE;
			for (int[] child : actions(state)) {
				value = minimax(result(state, child), depth - 1, player,false);
				maxValue = Math.max(maxValue, value);
			}
			return maxValue;
		} else {
			minValue = Integer.MAX_VALUE;
			for (int[] child : actions(state)) {
				value = minimax(result(state, child), depth - 1, player, true);
				minValue = Math.min(minValue, value);
			}
			return minValue;
		}
	}
	public int[] optimalMove(int[][] state, int depth, int player, boolean isMaximize) {
		int[] bestMove = null;
		int bestValue = isMaximize ? Integer.MIN_VALUE : Integer.MAX_VALUE;

		List<int[]> possibleMoves = getMoves(state, player);

		for (int[] move : possibleMoves) {
			int[][] newState = result(state, move);
			int value = minimax(newState, depth - 1, player, !isMaximize);

			if (isMaximize && value > bestValue) {
				bestValue = value;
				bestMove = move;
			} else if (!isMaximize && value < bestValue) {
				bestValue = value;
				bestMove = move;
			}
		}

		return bestMove;
	}

	public List<int[]> getMoves(int[][] state, int player) {
		int opponent;
		if (player == 1) {
			opponent = 2;
		}else {
			opponent = 1;
		}
		List<int[]> moves = new ArrayList<>();
		for (int i = 0; i < state.length; i++) {
			for (int j = 0; j < state[0].length; j++) {
				int[] move = new int[]{i, j};
				if (isValid(state, move, opponent)) {
					moves.add(move);

				}


			}
		}
		return moves;
	}





	//call minValue recursively
	private int[] maxValue(int[][] state) {
		//System.out.println("I'm at maxValue!");
		if (isTerminal(state)) {
			int utility = utility(state, player);
			int[] uArray = new int[] {utility, -1, -1};
			return uArray;
		}

		int v = Integer.MIN_VALUE;

		//int [] move contains coordinates of the move, move[0] row and move[1] is col

		//      int[] move = null;
		int[] move = {-1, -1 };
		List<int[]> actions = actions(state);
		//System.out.println(actions);
		for (int i = 0; i < actions.size(); i++) {
			int[] a = actions.get(i);
			// System.out.println("I'm at int[] a :"+a);
			int[][] result = result(state, a); 
			int[] v2_a2 = minValue(result);
			if (v2_a2[0] > v) {
				v = v2_a2[0];
				move = a;
			}
		}
		if (move == null) {
			// if no moves were made, return an array with -1 values
			int[] noMove = new int[] {-1, -1};
			return noMove;
		} else {
			int[] resultArray = new int[] {v, move[0], move[1]};
			return resultArray;
		}

		//      int[] resultArray = new int[] {v, move[0], move[1]};
		//      int[] resultArray = {v, move[0], move[1]};
		//      return resultArray;
	}

	private int[] minValue(int[][] state) {
		//System.out.println("I'm at minValue!");
		if (isTerminal(state)) {
			int utility = utility(state, player);
			int[] uArray = new int[] {utility, -1, -1};
			return uArray;
		}

		int v = Integer.MAX_VALUE;
		int[] move = {-1, -1};
		List<int[]> actions = actions(state);
		for (int i = 0; i < actions.size(); i++) {
			int[] a = actions.get(i);
			int[][] result = result(state, a);
			int[] v2_a2 = maxValue(result);
			if (v2_a2[0] < v) {
				v = v2_a2[0];
				move = a;
			}
		}

		if (move != null) {
			int[] resultArray = {v, move[0], move[1]};
			return resultArray;
		} else {
			return new int[] {v, -1, -1};
		}
	}

	List<int[]> actions(int[][] state) {
		//System.out.println("I'm at actions!");
		// return a list of valid moves
		List<int[]> moves = new ArrayList<>();

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				//         int[] move = new int[] {i, j};
				int[] move = {i, j};
				if (isValid(state, move, player)) {
					//          for (int[] each : moves) {
					//              System.out.println(Arrays.toString(each));
					//          }
					moves.add(move);

				}
			}
		}
		// check if the list is empty
		if (moves.isEmpty()) {
			int[] noMove = new int[] {-1, -1};
			moves.add(noMove);
		}


		return moves;
	}

	int[][] result(int[][] state, int[] a) {
		//  System.out.println("I'm at result!");
		//return the result state from the action
		int[][] newState = new int[state.length][state[0].length];
		for (int i = 0; i < state.length; i++) {
			for (int j = 0; j < state[i].length; j++) {
				newState[i][j] = state[i][j];
				//System.out.println(newState[i][j]);
			}
		}
		int row = a[0];
		int col = a[1];
		//System.out.println(row + col);
		newState[row][col] = player;
		return newState;
	}



	public int toMove(int[][] state) {
		// count the number of disks
		int diskCount = 0;
		for (int i = 0; i < state.length; i++) {
			for (int j = 0; j < state[0].length; j++) {
				if (state[i][j] != 0) {
					diskCount++;
				}
			}
		}

		// who should move next player1(X), player 2(O)
		int turn;
		if (diskCount % 2 != 0) {
			turn = 2;
			return turn;
		} else {
			turn = 1;
			return turn;
		}
	}

	public int utility(int[][] state, int player) {
		//System.out.println("I'm at utility!");
		int darkCount = 0;
		int lightCount = 0;
		for (int i = 0; i < state.length; i++) {
			for (int j = 0; j < state[0].length; j++) {
				if (state[i][j] == 1) {
					darkCount++;
				} else if (state[i][j] == 2) {
					lightCount++;
				}
			}
		}

		int utility = player == 1 ? darkCount - lightCount : lightCount - darkCount;
		return utility;
	}

	public boolean isTerminal(int[][] state) {
		// System.out.println("I'm at isTerminal!");
		// Check if the board is full
		//     for (int i = 0; i < state.length; i++) {
		//       for (int j = 0; j < state[0].length; j++) {
		//         if (state[i][j] == 0) {
		//           return false;
		//         }
		//       }
		//     }

		// Check if there is no valid move can be made
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

	//int [] move contains coordinates of the move, move[0] row and move[1] is col
	public boolean isValid(int[][] state, int[] move, int player) {
		// Check if move is within board boundaries
		if (move[0] < 0 || move[0] >= state.length || move[1] < 0 || move[1] >= state[0].length) {
			return false;
		}

		// Check if the cell is empty
		if (state[move[0]][move[1]] != 0) {
			return false;
		}

		// Check if next to opponent
		int opponent = (player == 1) ? 2 : 1;

		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				int row = move[0] + i;
				int col = move[1] + j;
				if (row >= 0 && row < state.length && col >= 0 && col < state[0].length && state[row][col] == opponent) {
					int x = row + i;
					int y = col + j;
					while (x >= 0 && x < state.length && y >= 0 && y < state[0].length && state[x][y] == opponent) {
						x += i;
						y += j;
					}
					if (x >= 0 && x < state.length && y >= 0 && y < state[0].length && state[x][y] == player) {
						return true;
					}
				}
			}
		}

		return false;
	}


}
