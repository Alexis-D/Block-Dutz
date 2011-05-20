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
	private Sound winner, bloquer;
	private Sound aide, pas;

	private model.game.Level l = null;
	private Themes themes = new Themes();
	private Theme theme;
	private boolean fini;

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
	    winner = new Sound("ressources/sounds/winner.ogg");
		bloquer = new Sound("ressources/sounds/bloquer.ogg");
		aide = new Sound("ressources/sounds/aide.ogg");
		pas = new Sound("ressources/sounds/bruitPas.ogg");
		this.game = sbg;
		fini = false;
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
					if (!fini) {
					    fini = true;
					    aide.stop();
					    winner.play();
		                Database db = new Database();
		                try {
		                    db.insertScore(Player.name, id, l.getNbMoves());
		                } catch (SQLException e) {
		                    e.printStackTrace();
		                }
					}
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
	    if (fini && !winner.playing()) {
	        keyPressed(Input.KEY_ENTER, '\n');
	    }
	}

	public void setLevel(Integer level) {
		id = level;

		try {
			l = new model.game.Level("maps/" + level);
		} catch (IOException e) {
		    game.enterState(0, new FadeOutTransition(),
                    new FadeInTransition());
		}
	}

	public void keyPressed(int key, char c) {
		Action a = null;
		switch (key) {

		case Input.KEY_ESCAPE:
		    winner.stop();
		    aide.stop();
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
			aide.play();
			break;
		case Input.KEY_F2:
			theme = themes.next();
			break;
		case Input.KEY_ENTER:
			aide.stop();
			if (!fini) {
				setLevel(id);
			}
			else {
				Database db = new Database();
				try {
                    int f = db.nbLevelsFinished(Player.name);
                    if ((LevelSelector.selected + 1) / 8 + 1 <= f / 8 + 1) {
                        LevelSelector.selected = Math.min(LevelSelector.selected + 1, LevelSelector.nbLevels);
                    }
                    game.enterState(0, new FadeOutTransition(),
                            new FadeInTransition());
                    db.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return;
		}
		if (a != null) {
			try {
				l.action(a);
				pas.play();
			} catch (Exception e) {
			    bloquer.play();
			}
		}
	}

	public int getID() {
		return 1;
	}
}