package model.game;

public class Player {
	private int maxBlock = 1;
	private int x;
	private int y;
	private int moves = 0;
	private int direction = -1;
	
	public Player(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getMaxBlock() {
		return maxBlock;
	}

	public void setMaxBlock(int maxBlock) {
		this.maxBlock = maxBlock;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public void setMoves(int moves) {
		this.moves = moves;
	}
	
	public int getMoves() {
		return moves;
	}
}
