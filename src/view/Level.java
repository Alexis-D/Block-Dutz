package view;

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
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
 
public class Level extends BasicGameState {
    private StateBasedGame game;
    private GameContainer container;
    
    private model.game.Level l = null;
    private Image back, ground, groundBasic, playerLeft,playerRight, box, door;

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        this.container = gc;
        this.game = sbg;
        back = new Image("ressources/teeworlds-data/mapres/mountains.png");
        ground = new Image("ressources/ground.png");
        groundBasic = new Image("ressources/groundbasic.png");
        playerLeft = new Image("ressources/playerLeft.png");
        playerRight = new Image("ressources/playerRight.png");
        box = new Image("ressources/box.png");
        door = new Image("ressources/door.png");
    }
	
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {

       int y = 0;
       ArrayList<Box> lastLine = null;
       int absBox = 0;

        for(ArrayList<Box> c: l.getMap())
        {
            int x = 0;
            absBox = 0;
            for(Box m: c){
                switch(m) {
                    case GROUND : 
                    			if(lastLine != null && lastLine.get(absBox) == Box.GROUND)
                    				groundBasic.draw(x,y);
                    			else
                    				ground.draw(x,y);
                    			break;	
                    case PLAYER : 
                    			if(l.getPlayer().getDirection() == -1)
                    				playerLeft.draw(x,y);
                    			else
                    				playerRight.draw(x,y); 
                    			break;
                    			
                    case BLOCK : box.draw(x,y); break;
                    case DOOR : door.draw(x,y); break;
                    case PLAYER_ON_DOOR : game.enterState(0); break;
                }
                x += ground.getWidth();
                ++absBox;
            }
            
            lastLine = c;
            
            y += ground.getHeight();
        }
        //g.setBackground(new Color(50, 50, 200));
        g.drawString("" + this.l.getNbMoves(), 50, 50);
    }
 
    
    
    public void update(GameContainer gc, StateBasedGame sbg, int arg2) throws SlickException { }
	
	public void setLevel(Integer level){
	    try {
            l = new model.game.Level("maps/" + level);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void keyPressed(int key, char c) {
	    Action a = null;
	    switch (key) {
	        case Input.KEY_ESCAPE : game.enterState(0); break;
	        case Input.KEY_SPACE : a = Action.TOGGLE; break;
	        case Input.KEY_LEFT : a = Action.LEFT; break;
	        case Input.KEY_RIGHT : a = Action.RIGHT; break;
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