/* 
 * Ginger Li & Judy Zhu
 * CSC 242 - Project 1: Reversi
 */

package reversi;
import java.util.*;


public class CSC242_Project1 {
	public static void main(String[] args) {
		Scanner scnr = new Scanner(System.in);
		Game game = new Game();

		System.out.println("CSC 242 - Project 1: Reversi by Ginger Li & Judy Zhu");

		// ask user to choose board size
		System.out.println("Choose your game:\n"
				+ "1. 4x4 Reversi\n"
				+ "2. 8x8 Reversi \n"
				+ "Your choice: ");
		int boardSize = scnr.nextInt();


		// ask user to choose opponent
		System.out.println("Choose your opponent:\n"
				+ "1. An agent that plays randomly\n"
				+ "2. An agent that uses MINIMAX\n"
				+ "3. An agent that uses MINIMAX with alpha-beta pruning\n"
				+ "4. An agent that uses H-MINIMAX with a fixed depth cutoff and a-b pruning \n"
				+ "Your choice: ");
		int opponent = scnr.nextInt();



		// ask user to choose which symbol they want to use
		System.out.println("Do you want to play DARK (X) or LIGHT (O)? ");
		char symbol = scnr.next().charAt(0);
		System.out.println(); // for formatting

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

		// intialize 2D arrays to store board
		int[][] board4x4Content = new int[4][4];
		int[][] board8x8Content = new int[8][8];
		// set board to starting position
		// 4x4
		board4x4Content[1][1] = 1;
		board4x4Content[2][2] = 1;
		board4x4Content[1][2] = 2;
		board4x4Content[2][1] = 2;
		// 8x8
		board8x8Content[3][3] = 1;
		board8x8Content[4][4] = 1;
		board8x8Content[3][4] = 2;
		board8x8Content[4][3] = 2;

		if(boardSize == 1) {
			game.playReversi(board4x4Content, boardSize, opponent, symbol);
		} else if (boardSize == 2) {
			game.playReversi(board8x8Content, boardSize, opponent, symbol);
		}

	}



}