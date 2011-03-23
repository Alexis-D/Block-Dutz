package model.game;

import model.game.box.Box;

public class Level {
	private Box[][] map;
	private Player p;
	private boolean finished = false;
	private String name;
	
	public Level(String path) {
		
	}
	
	public void action(Action a) throws Exception {
		
	}
	
	public boolean isFinished() {
		return finished;
	}
	
	public int getNbMoves() {
		return p.getMoves();
	}
	
	public String getName() {
		return name;
	}
}
