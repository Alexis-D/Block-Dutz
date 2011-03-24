package model.game;

public class Main {
	public static void main(String[] args) throws Exception {
		Level l = new Level("maps/Map 01");
		System.out.println(l);
		l.action(Action.LEFT); l.action(Action.LEFT); l.action(Action.LEFT); l.action(Action.UP);
		l.action(Action.LEFT); l.action(Action.LEFT); l.action(Action.LEFT);
		l.action(Action.LEFT); l.action(Action.LEFT); l.action(Action.LEFT);
		l.action(Action.LEFT); l.action(Action.LEFT); 
		System.out.println(l);
		l.action(Action.DOWN);
		l.action(Action.LEFT); l.action(Action.LEFT);
		System.out.println(l); l.action(Action.LEFT); 
		System.out.println(l);
	}
}
