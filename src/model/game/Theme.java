package model.game;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Theme {
	private Image ground;
	private Image playerLeft;
	private Image playerRight;
	private Image box;
	private Image door;
	private Image playerDoor;
	private Image groundBasic = null;

	public Theme(String ground, String playerLeft, String playerRight,
			String box, String door, String playerDoor) throws SlickException {
		this.ground = new Image(ground);
		this.playerLeft = new Image(playerLeft);
		this.playerRight = new Image(playerRight);
		this.box = new Image(box);
		this.door = new Image(door);
		this.playerDoor = new Image(playerDoor);
	}

	public Theme(String ground, String playerLeft, String playerRight,
			String box, String door, String playerDoor, String groundBasic)
			throws SlickException {
		this(ground, playerLeft, playerRight, box, door, playerDoor);
		this.groundBasic = new Image(groundBasic);
	}

	public Image getGround() {
		return ground;
	}

	public Image getPlayerLeft() {
		return playerLeft;
	}

	public Image getPlayerRight() {
		return playerRight;
	}

	public Image getBox() {
		return box;
	}

	public Image getDoor() {
		return door;
	}

	public Image getPlayerDoor() {
		return playerDoor;
	}

	public Image getGroundBasic() {
		return groundBasic;
	}
}
