package view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import model.game.Action;
import model.game.Box;
import model.game.Level;

import model.game.Level;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
 
public class ScreenLevel extends BasicGame{

	Image back = null;
    float x = 400;
    float y = 300;
    Level l = null;
    Image ground;
	Image player;
	Image box;
    
    /*HashMap<Box, String> boxList;*/
    
	public ScreenLevel() throws SlickException {
		super("Block Dutz");
		/*boxList = new HashMap<Box, String>();
		boxList.add(Box.GROUND, );*/
	}

	@Override
	public void init(GameContainer arg0) throws SlickException {
		back = new Image("ressources/teeworlds-data/mapres/mountains.png");
		ground = new Image("ressources/ground.png");
		player = new Image("ressources/alex.png");
		box = new Image("ressources/box.png");
	}

	

	
	
	@Override
	public void update(GameContainer gc, int arg1) throws SlickException {
		
		Input input = gc.getInput();
		
        if(input.isKeyDown(Input.KEY_UP))
        {
        	try {
				l.action(Action.UP);
			} catch (Exception e) {
			}
        }
 
        if(input.isKeyDown(Input.KEY_DOWN))
        {
        	try {
				l.action(Action.DOWN);
			} catch (Exception e) {
			}
        }
        
        if(input.isKeyDown(Input.KEY_LEFT))
        {
        	try {
				l.action(Action.LEFT);
			} catch (Exception e) {
			}
        }
		
        if(input.isKeyDown(Input.KEY_RIGHT))
        {
        	try {
				l.action(Action.RIGHT);
			} catch (Exception e) {
			}
        }

        if(input.isKeyDown(Input.KEY_ESCAPE))
        {
        	
        }
	}

	@Override
	public void render(GameContainer arg0, Graphics arg1) throws SlickException {
		
	//	back.draw(0, 200);
		generateMap();
	}
 
	
	
	
	public void setLevel(Level l){
		this.l = l;
	}
	
	
	
	private void generateMap() throws SlickException {
		
		ArrayList<ArrayList<Box>> map = l.getMap();

		int x = 0;
		int y = 0;

		for(ArrayList<Box> c: map)
		{
			for(Box m: c){
				
				switch(m) {
				case GROUND : ground.draw(x,y); break;
				case PLAYER : player.draw(x,y); break;
				case BLOCK : box.draw(x,y); break;
				}	
				
				x += ground.getWidth();
			}
			x = 0;
			y += ground.getHeight();
		}
	}


	public static void main(String[] args) throws SlickException, IOException {
		
		 Level l = new Level("maps/Map 01");
		 ScreenLevel g = new ScreenLevel();
		 g.setLevel(l);
	     AppGameContainer app = new AppGameContainer(g);
	     app.setDisplayMode(900, 700, false);
	     app.start();
	}
	
	
}
 
