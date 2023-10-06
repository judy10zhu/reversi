package reversi;
import java.util.ArrayList;
import java.util.List;

public class Abminimax {
	Minimax minimax = new Minimax();

	private int abMinimax(int[][] state, int depth, int player, int alpha, int beta, boolean isMinimize) {
		if (depth == 0 || minimax.isTerminal(state)) {
			return minimax.utility(state, player);
		}
		int maxValue, minValue,value;
		if (isMinimize) {
			minValue = Integer.MAX_VALUE;
			for (int[] child : minimax.actions(state)) {
				value = abMinimax(minimax.result(state, child), depth - 1, player,  alpha, beta, false);
				minValue = Math.min(minValue, value);
				beta = Math.min(beta, minValue);
				if (beta <= alpha) {
					break;
				}
			}
			return minValue;
		} else {
			maxValue = Integer.MIN_VALUE;
			for (int[] child : minimax.actions(state)) {
				value = abMinimax(minimax.result(state, child), depth - 1, player,  alpha, beta, true);
				maxValue = Math.max(maxValue, value);
				alpha = Math.max(alpha, maxValue);
				if (beta <= alpha) {
					break;
				}
			}
			return maxValue;
		}
	}

	public int[] abOptimal(int[][] state, int depth, int player, int alpha, int beta, boolean isMaximize) {
		int[] bestMove = null;
		int bestValue = isMaximize ? Integer.MIN_VALUE : Integer.MAX_VALUE;

		List<int[]> possibleMoves = abMoves(state, player, depth, alpha, beta, isMaximize);

		for (int[] move : possibleMoves) {
			int[][] newState = minimax.result(state, move);
			int value = abMinimax(newState, depth - 1, player,alpha, beta, !isMaximize);

			if (isMaximize && value > bestValue) {
				bestValue = value;
				bestMove = move;
				alpha = Math.max(alpha, value);
			} else if (!isMaximize && value < bestValue) {
				bestValue = value;
				bestMove = move;
				beta = Math.min(beta, value);
			}
			if (beta <= alpha) {
				break;
			}
		}

		return bestMove;
	}


	public List<int[]> abMoves(int[][] state, int player, int depth, int alpha, int beta, boolean isMaximize) {
		int opponent;
		if (player == 1) {
			opponent = 2;
		} else {
			opponent = 1;
		}

		List<int[]> moves = new ArrayList<>();
		for (int i = 0; i < state.length; i++) {
			for (int j = 0; j < state[0].length; j++) {
				int[] move = new int[] { i, j };
				if (minimax.isValid(state, move, opponent)) {
					int[][] newState = minimax.result(state, move);
					int value = abMinimax(newState, depth - 1, player,alpha, beta, !isMaximize);
					if (isMaximize) {
						if (value > alpha) {
							alpha = value;
						}
					} else {
						if (value < beta) {
							beta = value;
						}
					}
					if (alpha >= beta) {
						break;
					}
					moves.add(move);
				}
			}
		}
		return moves;
	}


}