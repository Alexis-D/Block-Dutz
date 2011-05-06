package view;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Game extends StateBasedGame{

    public Game() {
        super("Block Dutz");
    }

    public void initStatesList(GameContainer arg0) throws SlickException {
        addState(new LevelSelector());
        addState(new Level());
    }
    
    public static void main(String[] arguments) {
        try {
            AppGameContainer app = new AppGameContainer(new Game());
            app.setDisplayMode(64 * 15, 64 * 11, false);
            app.setShowFPS(false);
            app.setVSync(true);
            app.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
