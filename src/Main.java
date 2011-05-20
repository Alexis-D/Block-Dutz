import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import view.Game;


public class Main {
    public static void main(String[] arguments) {
            AppGameContainer app =  null;
            try {
                app = new AppGameContainer(new Game());
                app.setDisplayMode(800, 600, false);
            } catch (SlickException e1) {
                e1.printStackTrace();
            }
            try {
                app.setFullscreen(false);
            } catch (SlickException e) {
            }
            app.setShowFPS(false);
            app.setVSync(true);
            try {
                app.start();
            } catch (SlickException e) {
                e.printStackTrace();
            }
    }
}
