import java.util.Scanner;
public class Game {
	// snake board
	Snake[][] board;
	// input
	Scanner in;
	// determines whether the game is over.
	boolean over = false;
	// length of the snake
	int length = 2;
	// previous player input, either h for horizontal input or v for vertical input
	// controls the direction of the snakes tail
	char prev = 'h';

	// position of snake head
	int headX = 0;
	int headY = 0;
	
	// countdown for when apples spawn.
	int appleTimerMax = 8;
	int appleTimer = appleTimerMax;
	
	// speed at which the snake moves per input
	int speed = 1;

	public Game() {
		// create scanner
		in = new Scanner(System.in);
		// create game board, series of snake objects as 2D array
		board = new Snake[16][16];
		// set the 0,0 point to be the head of the snake
		board[0][0] = new Snake(true, length);
		// game loop
		while(!over) {
			// decrease apple timer
			appleTimer--;
			// game functions
			if (appleTimer <= 0) spawnApples();
			print();
			// call inputs after printing the game so they arent delayed.
			char m = handle();
			if (m == 'q') over = true; // quit the game if q is pressed
			else {
				for (int i=0; i<speed; i++) { // every input gives the game 'speed' updates
					move(m);
				}
			}
			// increase speed depending on tail length
			setSpeed();
		}
	}
	
	void print() {
		// print out the game board
	
		// clear the screen
		System.out.println("\033[H\033[2J");
		System.out.println("Use 'w' 'a' 's' 'd' to move.\n Press 'q' to quit the game.\n");
		for (int i=0; i<board.length; i++) {
			for (int j=0; j<board[i].length; j++) {
				if (board[i][j] != null) {
					if (board[i][j].head) {
						System.out.print("O ");
					}
					else if (board[i][j].isApple) {
						System.out.print("X ");
					} else {
						// if the current snake piece is not the head or an apple
						System.out.print(board[i][j].tail+" ");
					}
				} else {
					// print out a dot if the current snake piece doesnt exist.
					System.out.print("⋅ ");
				}		
			}
			System.out.println();
		}
		// tell the user their current length and speed
		System.out.println("Speed: "+speed);
		System.out.println("Length: "+length);
	}
	void spawnApples() {
		// randomize apple spawn position to be some number between 0 and 15 inclusive for x and y
		int appleX = (int)(Math.random()*board.length);
		int appleY = (int)(Math.random()*board.length);
		// if the chosen position is already an apple, repeat.
		if (board[appleY][appleX] != null && board[appleY][appleX].isApple) {
			spawnApples();
			return;
		}
		// if that position is not null. Increase the snake length
		// else if that position is null. Create a new apple
		if (board[appleY][appleX] != null) length++;
		else board[appleY][appleX] = new Snake(false, length);
		// reset apple timer 
		appleTimer = appleTimerMax; 
	}
	// function to decrease the snake tail pieces len variable
	void stepLen() {
		for (int i=0; i<board.length; i++) {
			for (int j=0; j<board.length; j++) {
				// if board[i][j] is a tail piece
				if (board[i][j] != null && !board[i][j].head && !board[i][j].isApple) {
					// decrement it's internal length variable
					board[i][j].len--;
					// if / when that internal variable reaches 0, delete the snake piece.
					if (board[i][j].len <= 0) board[i][j] = null;
				}
			}
		}
	}

	void move(char m) {
		stepLen();
		// set current x y to previous x y
		int py = headY;
		int px = headX;
		// move up down left right depending on keys pressed
		if (m == 'w') {
			headY -= 1;
			prev = 'v';
		}
		if (m == 'a') {
			headX -= 1;
			prev = 'h';
		}
		if (m == 's') {
			headY += 1;
			prev = 'v';
		}
		if (m == 'd') {
			headX += 1;
			prev = 'h';
		}
		// set the current position of the snake head to now be a tail.
		// set tail direction depending on if you moved vertically or horizontally
		if (prev == 'v') {
			board[py][px].move("|");
		} else {
			board[py][px].move("―");
		}
		// if you move out of bounds, end the game
		if (headX < 0 || headX >= board.length || headY < 0 || headY >= board.length) {
			over = true;
			return;
		}
		else if (board[headY][headX] != null) {
			// if you touch an apple, increase length
			if (board[headY][headX].isApple) {
				length++;
			} else { // if you touch yourself, end the game
				over = true;
				return;
			}
		}
		// move the snake head to the new position.
		board[headY][headX] = new Snake(true, length);
	}

	char handle() {
		// user input
		char input = in.next().charAt(0);
		// if the input isnt w a s d or q, get user to re-input command
		if (input != 'w' && input != 'a' && input != 's' && input != 'd' && input != 'q') {
			System.out.println("Invalid Input!");
			return handle();
		}
		// return user specified input
		return input;
	}
	
	void setSpeed() {
		// update the speed at which the snake moves depending on the size of the snake
		if (length == 3) speed = 2;
		if (length == 5) speed = 3;
		if (length == 8) speed = 4;
		if (length == 13) speed = 5;
		if (length >= 18) speed = 6;
	}
}
