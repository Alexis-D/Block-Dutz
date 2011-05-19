package view;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.state.transition.Transition;

	public class ScoreSelector extends BasicGameState {
		private StateBasedGame game;
		private GameContainer container;
		private Integer selected = 0;

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

	for (int y = 0; y < 6; ++y) {

		for (int x = 0; x < 8; ++x) {
			if (y * 8 + x == selected) {
				g.setColor(new Color(255, 0, 0));
			}
			else
				g.setColor(new Color(255, 234, 0));
					
                float lg = 88.75f;
                float ht = 88.33f;
                g.fillRect(10 + x * (lg + 10), 10 + y * (ht + 10), lg, ht);

                g.setColor(new Color(0, 0, 0));
                g.drawString("" + (y * 8 + x + 1), 10 + x * (lg + 10), 10 + y * (ht + 10));
			

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
				game.enterState(8, new FadeOutTransition(), new FadeInTransition());
			} else {
				int d = 0;
				switch (key) {
					case Input.KEY_ESCAPE:
						try {
							game.getState(10).init(container, game);
						} catch (SlickException e) {
							e.printStackTrace();
						}
						game.enterState(10, new FadeOutTransition(), new FadeInTransition());
				
	    			case Input.KEY_UP:
	    				d = -8;
	    				break;
	    			case Input.KEY_DOWN:
	    				d = 8;
	    				break;
	    			case Input.KEY_LEFT:
	    				d = -1;
	    				break;
	    			case Input.KEY_RIGHT:
	    				d = 1;
	    				break;		
				}
				if (selected + d >= 0 && selected + d < 7 * 5) {
					selected += d;
				}
			}
		}

		public int getID() {
			return 7;
		}
}

