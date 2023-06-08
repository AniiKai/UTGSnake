public class Snake {
	// if this snake piece is the snake head.
	public boolean head = false;
	// len variable specifies how many turns this snake piece will exist
	// a longer len means it will stay on the board for longer 
	// this will end up making the snake longer.
	public int len;
	// what to print out if this snake piece is a tail
	// will be either a horizontal or vertical bar
	public String tail;
	// if this snake piece is an apple
	public boolean isApple = false;
	// constructor
	// bool isHead : whether the snake piece being created is a snake head or apple
	// int len : input from Game to set the len variable of this snake piece 
	public Snake(boolean isHead, int len) {
		this.len = len;
		// if true, set head to true
		if (isHead) head = true;
		else { // if false, set isApple to true
			isApple = true;
			// set len to -1, currently useless but makes sure the apple will never disappear
			// if len ever counts down
			this.len = -1;
		}
	}
	// update current snake piece from a head to a tail (whenever the head moves this gets called on its old pos)
	// String dir : appearance of the tail, either a horizontal or vertical bar like said above
	public void move(String dir) {
		head = false;
		tail = dir;
	}
}
