package view;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import model.database.Database;

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

public class Scores extends BasicGameState { 

	private Database db;
	private LinkedHashMap<String, String> ladder;
	private int levelSelected;
	private GameContainer container;
	private StateBasedGame game;
	
	@Override
	public int getID() {
		return 8;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
		this.container = gc;
		this.game = sbg;
		ladder = new LinkedHashMap<String, String>();
		
		db = new Database();

		ResultSet rs = null;
		try {
			rs = db.getClassement(levelSelected);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			while(rs.next()){
				String name = rs.getString(1);
				String score = rs.getString(2);
				ladder.put(name, score);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			db.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g)
			throws SlickException {

		g.setBackground(Color.blue);
    	g.setColor(Color.white);
    	g.setFont(new TrueTypeFont(new java.awt.Font(java.awt.Font.SANS_SERIF, java.awt.Font.BOLD, 40), true));
    	g.drawString("Classement niveau "+ levelSelected, 200, 50);
    	g.setFont(new TrueTypeFont(new java.awt.Font(java.awt.Font.SANS_SERIF, java.awt.Font.BOLD, 30), true));
    	
    	int x = 300;
    	int y = 200;
    	int rank = 1;
    	
    	for (String name : ladder.keySet()) {
    		if(rank != 10){
    				g.drawString(""+rank, x - 65, y);
	    		 	g.drawString(name, x, y);
	    		 	g.drawString(ladder.get(name), x + 300, y);
	    		 	y = y + 50;
	    		 	rank++;
    		}
    		else
    			break;
    	}
    	
	}

	public void setLevel(int l){
		levelSelected = l;
	}
	
	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
	}
	
	public void keyPressed(int key, char c) {
		if (key == Input.KEY_ENTER || key == Input.KEY_SPACE) {
			try {
				game.getState(7).init(container, game);
			} catch (SlickException e) {
				e.printStackTrace();
			}
			game.enterState(7, new FadeOutTransition(), new FadeInTransition());
		}
		else if(key == Input.KEY_ESCAPE){
			try {
				game.getState(7).init(container, game);
			} catch (SlickException e) {
				e.printStackTrace();
			}
			game.enterState(7, new FadeOutTransition(), new FadeInTransition());
		}
			
	}
}