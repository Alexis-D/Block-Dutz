package view;

import java.sql.SQLException;

import model.database.Database;
import model.game.Player;

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
public class LevelSelector extends BasicGameState {
	private StateBasedGame game;
	private GameContainer container;
	private Integer selected = 0;
	private int lastLine = 0;

	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		this.container = gc;
		this.game = sbg;
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

		for (int y = 0; y < 5; ++y) {
			boolean newRow = true;

			for (int x = 0; x < 7; ++x) {
				if (y * 7 + x == selected) {
					g.setColor(new Color(255, 0, 0));

					try {
						if (db.getScore(Player.name, y * 7 + 1 + x) == Integer.MAX_VALUE) {
							newRow = false;
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} else
					try {
						if (db.getScore(Player.name, y * 7 + 1 + x) != Integer.MAX_VALUE) {
							g.setColor(new Color(0, 255, 0));
						}

						else if (lastRow) {
							newRow = false;
							g.setColor(new Color(0, 0, 255));
						}

						else {
							newRow = false;
							g.setColor(new Color(255, 215, 0));
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}

				g.fillRect(15 + x * 135, 15 + y * 135, 120, 120);

				g.setColor(new Color(0, 0, 0));
				g.drawString("" + (y * 7 + x + 1), 15 + x * 135, 15 + y * 135);
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
			switch (key) {
			case Input.KEY_UP:
				d = -7;
				break;
			case Input.KEY_DOWN:
				d = 7;
				break;
			case Input.KEY_LEFT:
				d = -1;
				break;
			case Input.KEY_RIGHT:
				d = 1;
				break;
			}
			if (selected + d >= 0 && selected + d < 7 * (lastLine + 1)) {
				selected += d;
			}
		}
	}

	public int getID() {
		return 0;
	}
}