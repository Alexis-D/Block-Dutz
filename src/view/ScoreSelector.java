package view;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

@SuppressWarnings("deprecation")
public class ScoreSelector extends BasicGameState {
	private StateBasedGame game;
	private GameContainer container;
	private Integer selected = 0;
	private Sound s, bloquer;

	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		this.container = gc;
		this.game = sbg;
		s = new Sound("ressources/sounds/menu1.ogg");
		bloquer = new Sound("ressources/sounds/bloquer.ogg");
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

		for (int y = 0; y < 6; ++y) {

			for (int x = 0; x < 8; ++x) {
				if (y * 8 + x == selected) {
					g.setColor(new Color(255, 0, 0));
				} else
					g.setColor(new Color(255, 234, 0));

				float lg = 88.75f;
				float ht = 88.33f;
				g.fillRect(10 + x * (lg + 10), 10 + y * (ht + 10), lg, ht);

				g.setColor(new Color(0, 0, 0));
				g.drawString("" + (y * 8 + x + 1), 10 + x * (lg + 10), 10 + y
						* (ht + 10));

			}
		}

	}

	public void keyPressed(int key, char c) {

		if (key == Input.KEY_ENTER || key == Input.KEY_SPACE) {
			try {
				((Scores) game.getState(8)).setLevel(selected + 1);
				game.getState(8).init(container, game);
			} catch (SlickException e) {
				e.printStackTrace();
			}
			game.enterState(8);
		} else {
			int d = 0;
			switch (key) {
			case Input.KEY_ESCAPE:
				try {
					game.getState(10).init(container, game);
				} catch (SlickException e) {
					e.printStackTrace();
				}
				game.enterState(10);

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
			if (d != 0) {
    			if (selected + d >= 0 && selected + d < 6 * 8) {
    				selected += d;
    				s.play();
    			}
                else {
                    bloquer.play();
                }
            }
		}
	}

	public int getID() {
		return 7;
	}
}
