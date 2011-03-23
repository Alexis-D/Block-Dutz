package model.game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import model.game.box.Block;
import model.game.box.Box;
import model.game.box.Door;
import model.game.box.Ground;

public class Level {
	private ArrayList<ArrayList<Box>> map = new ArrayList<ArrayList<Box>>();
	private Player p;
	private boolean finished = false;
	private String name;
	
	/**
	 * Initiate a level.
	 * @param pathname URI to the level map file.
	 * @throws IOException
	 */
	public Level(String pathname) throws IOException {
		File in = new File(pathname);
		BufferedReader br = new BufferedReader(new FileReader(in));
		String line;
		int j = 0;
		
		while((line = br.readLine()) != null) {
			ArrayList<Box> row = new ArrayList<Box>();
			map.add(row);
			++j;
			
			for(int i = 0; i < line.length(); ++i) {
				char c = line.charAt(i);
				
				switch(c) {
				case '+':
					row.add(new Ground());
					break;
					
				case '*':
					row.add(new Block());
					break;
					
				case '!':
					row.add(new Door());
					break;
					
				case '@':
					row.add(new Player(j, i));
					break;
					
				case ' ':
					row.add(null);
					break;
					
				default:
					throw new IOException("Invalid character in the map file.");
				}
			}
		}
		
		br.close();
	}
	
	public void action(Action a) throws Exception {
		
	}
	
	public boolean isFinished() {
		return finished;
	}
	
	public int getNbMoves() {
		return p.getMoves();
	}
	
	public String getName() {
		return name;
	}
}
