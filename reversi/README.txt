

Testing (program expects exact integer/character to compile correctly):
1) Choosing board: 1 for 4x4; 2 for 8x8
2) Choose opponent by corresponding integers
3) Pick your symbol: O or X (DARK/X always go first)
4) Enter row: an integer from 1 to 4 for 4x4; 1 to 8 for 8x8
5) Enter column: a lower-cased character from a to d for 4x4; a to h for 8x8

Details about the classes:
- Game.java
	- where majority of adversarial search details locate (detailed methods for running the game)
- Random.java
	- takes care of the random opponent 
- Minimax.java
	- takes care of minimax
- Abminimax.java
	- minimax with alpha-beta pruning
- H_Minimax.java
	- heuristic minimax

Sample Output: 
Choose your game:
1. 4x4 Reversi
2. 8x8 Reversi 
Your choice: 
1
Choose your opponent:
1. An agent that plays randomly
2. An agent that uses MINIMAX
3. An agent that uses MINIMAX with alpha-beta pruning
4. An agent that uses H-MINIMAX with a fixed depth cutoff and a-b pruning 
Your choice: 
1
Do you want to play DARK (X) or LIGHT (O)? 
X

  A B C D
1         1 
2   X O   2 
3   O X   3 
4         4 
  A B C D

Next to play: DARK/X

Enter the row (1 - 4) where you want to place your disk:
1
Enter the column (a - d) where you want to place your disk:
c
Column number: 3
  A B C D
1     X   1 
2   X X   2 
3   O X   3 
4         4 
  A B C D

Next to play: LIGHT/O

  A B C D
1     X   1 
2   X X   2 
3   O O O 3 
4         4 
  A B C D
Elapsed time: 3
Next to play: DARK/X
