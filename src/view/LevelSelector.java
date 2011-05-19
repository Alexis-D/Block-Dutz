package view;

import java.sql.SQLException;

import model.database.Database;
import model.game.Player;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

@SuppressWarnings("deprecation")
public class LevelSelector extends BasicGameState {
	private StateBasedGame game;
	private GameContainer container;
	public static int selected = 0;
	public static int nbLevels = 48;
	private int lastLine = 0;
	private Sound s;

	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		this.container = gc;
		this.game = sbg;
		s = new Sound("ressources/sounds/menu1.ogg");
	}

	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		g.setBackground(Color.black);
		g.setFont(new TrueTypeFont(new java.awt.Font(java.awt.Font.SANS_SERIF,
				java.awt.Font.BOLD, 50), true));
		g.setColor(new Color(255, 215, 0));

		Database db = new Database();
		try {
			db.lastLevel(Player.name);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		boolean lastRow = true;

		for (int y = 0; y < 6; ++y) {
			boolean newRow = true;

			for (int x = 0; x < 8; ++x) {
				if (y * 8 + x == selected) {
					g.setColor(new Color(255, 0, 0));

					try {
						if (db.getScore(Player.name, y * 8 + 1 + x) == Integer.MAX_VALUE) {
							newRow = false;
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} else
					try {
						if (db.getScore(Player.name, y * 8 + 1 + x) != Integer.MAX_VALUE) {
							g.setColor(new Color(0, 255, 0));
						}

						else if (lastRow) {
							newRow = false;
							g.setColor(new Color(255, 255, 255));
						}

						else {
							newRow = false;
							g.setColor(new Color(255, 215, 0));
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}

				float lg = 88.75f;
				float ht = 88.33f;
				g.fillRect(10 + x * (lg + 10), 10 + y * (ht + 10), lg, ht);

				g.setColor(new Color(0, 0, 0));
				g.drawString("" + (y * 8 + x + 1), 10 + x * (lg + 10), 10 + y
						* (ht + 10));
			}

			if (newRow) {
				lastLine = y + 1;
			}

			lastRow = newRow;
		}

		try {
			db.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void keyPressed(int key, char c) {
		if (key == Input.KEY_ENTER || key == Input.KEY_SPACE) {
			try {
				game.getState(1).init(container, game);
				((Level) game.getState(1)).setLevel(selected + 1);
			} catch (SlickException e) {
				e.printStackTrace();
			}
			game.enterState(1, new FadeOutTransition(), new FadeInTransition());
		} else if (key == Input.KEY_ESCAPE) {
			game.enterState(10, new FadeOutTransition(), new FadeInTransition());
		} else {
			int d = 0;
			s.play();
			switch (key) {
			case Input.KEY_K:
			case Input.KEY_UP:
				d = -8;
				break;
			case Input.KEY_J:
			case Input.KEY_DOWN:
				d = 8;
				break;
			case Input.KEY_H:
			case Input.KEY_LEFT:
				d = -1;
				break;
			case Input.KEY_L:
			case Input.KEY_RIGHT:
				d = 1;
				break;
			}
			if (selected + d >= 0 && selected + d < 8 * (lastLine + 1)
					&& selected + d <= 47) {
				selected += d;
			}
		}
	}

	public int getID() {
		return 0;
	}
}