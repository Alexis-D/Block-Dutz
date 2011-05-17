package view;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
 
public class EnterName extends BasicGameState {
 
	private TextField field;
	private StateBasedGame game;
	private GameContainer container;
    
    public int getID() {
        return 3;
    }
    
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
    	this.game = sbg;
    	this.container = gc;
    	field = new TextField(gc, new TrueTypeFont(new java.awt.Font(java.awt.Font.SANS_SERIF, java.awt.Font.BOLD, 40), true)
    				, 100, 200, 600, 100);
    	field.setFocus(false);
    }
 
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
    	g.setBackground(Color.blue);
    	g.setColor(Color.white);
    	g.setFont(new TrueTypeFont(new java.awt.Font(java.awt.Font.SANS_SERIF, java.awt.Font.BOLD, 40), true));
    	g.drawString("Entre ton nom :", 100, 100);
    	field.render(gc, g);
    	field.setBackgroundColor(Color.blue);
    	field.setFocus(true);
    }
    
    public void keyPressed(int key, char c) {
        if (key == Input.KEY_UP) {
            try {
                game.getState(0).init(container, game);
            } catch (SlickException e) {
                e.printStackTrace();
            }
            game.enterState(0, new FadeOutTransition(), new FadeInTransition());
        }
    }
    
	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
		// TODO Auto-generated method stub
	}
}