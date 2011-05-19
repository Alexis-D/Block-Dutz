package view;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import model.database.Database;
import model.game.Action;
import model.game.Box;
import model.game.Player;
import model.game.Theme;
import model.game.Themes;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class Level extends BasicGameState {
	private StateBasedGame game;
	private int id;
	private Sound s1, s2;
	private Sound h1, b1;

	private model.game.Level l = null;

	// private Image ground, playerLeft, playerRight, box, door, playerDoor;
	private Themes themes = new Themes();
	private Theme theme;

	private enum State {
		RUNNING, FINISHED
	}

	private State state;

	public Level() throws SlickException {
		themes.add(new Theme("ressources/old/ground.png",
				"ressources/old/player_left.png", "ressources/old/player_right.png",
				"ressources/old/box.png", "ressources/old/nid.png",
				"ressources/old/player_door.png", "ressources/old/groundbasic.png"));
		
		themes.add(new Theme("ressources/ground.png",
				"ressources/player_left.png", "ressources/player_right.png",
				"ressources/box.png", "ressources/door.png",
				"ressources/player_door.png"));
		theme = themes.next();
	}

	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		s1 = new Sound("ressources/sounds/winner.ogg");
		s2 = new Sound("ressources/sounds/bloquer.ogg");
		h1 = new Sound("ressources/sounds/aide.ogg");
		b1 = new Sound("ressources/sounds/bruitPas.ogg");
		this.game = sbg;
		state = State.RUNNING;
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		g.setBackground(new Color(255, 232, 196));
		int y = 0;
		ArrayList<Box> lastLine = null;
		int absBox = 0;

		for (ArrayList<Box> c : l.getMap()) {
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

					/*
					 * TrueTypeFont ttf = new TrueTypeFont(new Font(
					 * Font.SANS_SERIF, Font.PLAIN, 50), true);
					 * ttf.drawString(125, 125, "Good Boy!", Color.black);
					 */
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

	public void update(GameContainer gc, StateBasedGame sbg, int arg2)
			throws SlickException {
		switch (state) {
		case FINISHED:
			if (!s1.playing())
				s1.play();
			break;
		}
	}

	public void setLevel(Integer level) {
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
			s1.stop();
			game.enterState(0, new FadeOutTransition(), new FadeInTransition());
			break;
		case Input.KEY_SPACE:
			a = Action.TOGGLE;
			break;
		case Input.KEY_H:
		case Input.KEY_LEFT:
			a = Action.LEFT;
			break;
		case Input.KEY_L:
		case Input.KEY_RIGHT:
			a = Action.RIGHT;
			break;
		case Input.KEY_F1:
			h1.play();
			break;
		case Input.KEY_F2:
			theme = themes.next();
			break;
		case Input.KEY_ENTER:
			if (state == State.RUNNING) {
				setLevel(id);
			}

			else {
				Database db = new Database();
				try {
					db.insertScore(Player.name, id, l.getNbMoves());
				} catch (SQLException e) {
					e.printStackTrace();
				}
                try {
                    int f = db.nbLevelsFinished(Player.name);
                    if (LevelSelector.selected + 1 < (f / 8 + 1)) {
                        LevelSelector.selected += LevelSelector.selected == LevelSelector.nbLevels ? 0
                                : 1;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
				game.enterState(0, new FadeOutTransition(),
						new FadeInTransition());
                try {
                    db.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
			}
			return;
		}
		if (a != null) {
			try {
				l.action(a);
				b1.play();
			} catch (Exception e) {
				s2.play();
			}
		}
	}

	public int getID() {
		return 1;
	}
}