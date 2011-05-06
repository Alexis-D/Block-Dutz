package view;

import java.util.ArrayList;

public class Themes {
	private ArrayList<Theme> themes = new ArrayList<Theme>();
	int index = 0;
	
	public void add(Theme t) {
		themes.add(t);
	}
	
	public Theme next() {
		//fail si themes.size() == 0
		if(index < themes.size()) {
			return themes.get(index++);
		}
		
		else {
			index = 1;
			return themes.get(0);
		}
	}
}
