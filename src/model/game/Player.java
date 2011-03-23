package model.game;

import model.game.box.Box;

public class Player extends Box {
	private int maxBlock = 1;
	private int x;
	private int y;
	private int moves = 0;
	
	public Player(int x, int y) {
		
	}
	
	public int getMoves() {
		return moves;
	}
}
