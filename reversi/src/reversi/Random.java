package reversi;
import java.util.*;

class Random {

	Game game = new Game();
	Minimax minimax = new Minimax();

	public int[] randomMove(int[][] state) {
		int player = minimax.toMove(state);
		ArrayList<int[]> validMoves = new ArrayList<int[]>();
		for (int i = 0; i < state.length; i++) {
			for (int j = 0; j < state[0].length; j++) {
				int[] move = new int[] {i, j};
				if (game.isValid(state, move, player)) {
					validMoves.add(move);
				}
			}
		}
		if (validMoves.size() == 0) {
			return null;
		}

		int randomIndex = (int) (Math.random() * validMoves.size());
		return validMoves.get(randomIndex);
	}
}

