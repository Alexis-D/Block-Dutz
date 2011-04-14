package model.game;

public enum Box {
	BLOCK(true), GROUND(true), DOOR(false), PLAYER(true), EMPTY(false), PLAYER_ON_DOOR(true);
	
	private boolean blocking;
	private Box(boolean b) {
	    blocking = b;
	}

	public boolean isBlocking() {
	    return blocking;
	}
}
