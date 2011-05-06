package view;

import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;

import model.game.Action;
import model.game.Box;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class Level extends BasicGameState {
	private StateBasedGame game;
	private GameContainer container;
	private int id;

	private model.game.Level l = null;
	private Image ground, playerLeft, playerRight, box, door, playerDoor;

	private enum State {
		RUNNING, FINISHED
	}

	private State state;

	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		this.container = gc;
		this.game = sbg;
		ground = new Image("ressources/ground.png");
<<<<<<< HEAD
		playerLeft = new Image("ressources/goxLeft.png");
		playerRight = new Image("ressources/goxRight.png");
=======
		playerLeft = new Image("ressources/player_left.png");
		playerRight = new Image("ressources/player_right.png");
>>>>>>> cc245c4080d03d62a1f607f71a8f3bbf41af3cec
		box = new Image("ressources/box.png");
		door = new Image("ressources/door.png");
		playerDoor = new Image("ressources/player_door.png");
		state = State.RUNNING;
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		g.setBackground(new Color(255, 232, 196));
		int y = 0;

		int absBox = 0;

		for (ArrayList<Box> c : l.getMap()) {
			int x = 0;
			absBox = 0;
			for (Box m : c) {
				switch (m) {
				case GROUND:
					/*
					 * if(lastLine != null && lastLine.get(absBox) ==
					 * Box.GROUND) groundBasic.draw(x,y); else
					 */
					ground.draw(x, y);
					break;
				case PLAYER:
					if (l.getPlayer().getDirection() == -1)
						playerLeft.draw(x, y);
					else
						playerRight.draw(x, y);
					break;

				case BLOCK:
					box.draw(x, y);
					break;
				case DOOR:
					door.draw(x, y);
					break;
				case PLAYER_ON_DOOR:
					playerDoor.draw(x, y);
					//gc.getDefaultFont().drawString(100, 150, "BRAVO !!",
					//		Color.black);

					TrueTypeFont ttf = new TrueTypeFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50), true);
					ttf.drawString(125, 125, "Good Boy!", Color.black);
					state = State.FINISHED;
					break;
				}
				x += ground.getWidth();
				++absBox;
			}

			y += ground.getHeight();
		}
	}

<<<<<<< HEAD
	public void update(GameContainer gc, StateBasedGame sbg, int arg2) throws SlickException { }

	public void setLevel(Integer level){
=======
	public void update(GameContainer gc, StateBasedGame sbg, int arg2)
			throws SlickException {
	}

	public void setLevel(Integer level) {
>>>>>>> cc245c4080d03d62a1f607f71a8f3bbf41af3cec
		id = level;

		try {
			l = new model.game.Level("maps/" + level);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void keyPressed(int key, char c) {
		Action a = null;
		switch (key) {
		case Input.KEY_ESCAPE:
			game.enterState(0, new FadeOutTransition(), new FadeInTransition());
			break;
		case Input.KEY_SPACE:
			a = Action.TOGGLE;
			break;
		case Input.KEY_LEFT:
			a = Action.LEFT;
			break;
		case Input.KEY_RIGHT:
			a = Action.RIGHT;
			break;
		case Input.KEY_ENTER:
			if (state == State.RUNNING) {
				setLevel(id);
			}

			else {
				game.enterState(0, new FadeOutTransition(),
						new FadeInTransition());
			}
			return;
		}
		if (a != null) {
			try {
				l.action(a);
			} catch (Exception e) {
			}
		}
	}

	public int getID() {
		return 1;
	}
}