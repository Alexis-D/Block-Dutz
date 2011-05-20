import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import view.Game;


public class Main {
    public static void main(String[] arguments) {
        try {
            AppGameContainer app = new AppGameContainer(new Game());
            app.setDisplayMode(800, 600, false);
            app.setShowFPS(false);
            app.setVSync(true);
            app.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
