package view;

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


public class Menu extends BasicGameState {
    private StateBasedGame game;
    private GameContainer container;
    private Integer selected = 1;
    
    public int getID() {
        return 10;
    }

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        this.container = gc;
        this.game = sbg;
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.setBackground(Color.black);
        g.setFont(new TrueTypeFont(new java.awt.Font(java.awt.Font.SANS_SERIF, java.awt.Font.BOLD, 50), true));
        
        for (int i = 0; i < 4; ++i) {
            if (i == this.selected) {
                g.setColor(new Color(255, 255, 255));
            }
            else {
                g.setColor(new Color(0, 0, 255));
            }
            g.fillRect(15, 15 * (i + 1) + 157 * i, 930, 157);
        }
        
        String[] texte = {"Jouer", "Scores", "Quitter"};
        for (int i = 1; i < 4; ++i) {
            if (i == this.selected) {
                g.setColor(new Color(0, 0, 0));
            }
            else {
                g.setColor(new Color(255, 255, 255));
            }
            g.drawString(texte[i - 1], 400, 30 * (i + 1) + 157 * i);
        }
    }

    public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
        
    }

    public void keyPressed(int key, char c) {
        if (key == Input.KEY_ENTER) {
            try {
                game.getState(0).init(container, game);
            } catch (SlickException e) {
                e.printStackTrace();
            }
            game.enterState(0, new FadeOutTransition(), new FadeInTransition());
        }
        else if (key == Input.KEY_UP) {
            if (this.selected > 1) {
                --this.selected;
            }
        }
        else if (key == Input.KEY_DOWN) {
            if (this.selected < 3) {
                ++this.selected;
            }
        }
    }
}
