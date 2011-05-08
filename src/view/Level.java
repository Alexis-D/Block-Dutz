package view;

import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;

import model.game.Action;
import model.game.Box;
import model.game.Theme;
import model.game.Themes;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import org.newdawn.slick.TrueTypeFont;

import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

@SuppressWarnings("deprecation")
public class Level extends BasicGameState {
	private StateBasedGame game;
	private int id;

	private model.game.Level l = null;

	// private Image ground, playerLeft, playerRight, box, door, playerDoor;
	private Themes themes = new Themes();
	private Theme theme;
	
	private enum State {
		RUNNING, FINISHED
	}

	private State state;

	public Level() throws SlickException {
		themes.add(new Theme("ressources/ground.png",
				"ressources/player_left.png", "ressources/player_right.png",
				"ressources/box.png", "ressources/door.png",
				"ressources/player_door.png"));
		themes.add(new Theme("ressources/old/ground.png",
				"ressources/old/playerLeft.png",
				"ressources/old/playerRight.png", "ressources/old/box.png",
				"ressources/old/door.png", "ressources/player_door.png",
				"ressources/old/groundbasic.png"));
		themes.add(new Theme("ressources/old/ground.png",
				"ressources/old/goxLeft.png",
				"ressources/old/goxRight.png", "ressources/old/box.png",
				"ressources/old/door.png", "ressources/player_door.png",
				"ressources/old/groundbasic.png"));
		themes.add(new Theme("ressources/old/ground.png",
				"ressources/old/maxLeft.png",
				"ressources/old/maxRight.png", "ressources/old/box.png",
				"ressources/old/door.png", "ressources/player_door.png",
				"ressources/old/groundbasic.png"));
		theme = themes.next();
	}

	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		this.game = sbg;
		state = State.RUNNING;

	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.setBackground(new Color(255, 232, 196));
		int y = 0;
		ArrayList<Box> lastLine = null;
		int absBox = 0;

		for(ArrayList<Box> c: l.getMap())
		{
			int x = 0;
			absBox = 0;

			for (Box m : c) {
				switch (m) {
				case GROUND:
					if (lastLine != null && lastLine.get(absBox) == Box.GROUND
							&& theme.getGroundBasic() != null)
						theme.getGroundBasic().draw(x, y);
					else
						theme.getGround().draw(x, y);
					break;
				case PLAYER:
					if (l.getPlayer().getDirection() == -1)
						theme.getPlayerLeft().draw(x, y);
					else
						theme.getPlayerRight().draw(x, y);
					break;

				case BLOCK:
					theme.getBox().draw(x, y);
					break;
				case DOOR:
					theme.getDoor().draw(x, y);
					break;
				case PLAYER_ON_DOOR:
					theme.getPlayerDoor().draw(x, y);
					// gc.getDefaultFont().drawString(100, 150, "BRAVO !!",
					// Color.black);

					TrueTypeFont ttf = new TrueTypeFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50), true);
					ttf.drawString(125, 125, "Good Boy!", Color.black);
					state = State.FINISHED;
					break;

				}
				x += theme.getGround().getWidth();
				++absBox;
			}

			lastLine = c;

			y += theme.getGround().getHeight();

		}
	}

	public void update(GameContainer gc, StateBasedGame sbg, int arg2) throws SlickException { }

	public void setLevel(Integer level){
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
		case Input.KEY_F2:
			theme = themes.next();
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