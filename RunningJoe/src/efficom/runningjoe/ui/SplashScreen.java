package efficom.runningjoe.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import efficom.runningjoe.RunningJoe;
import efficom.runningjoe.ui.MenuScreen;

/**
 * Shows a splash image and moves on to the next screen.
 */
public class SplashScreen extends AbstractScreen
{
    private SpriteBatch spriteBatch;
    private Texture splash;

    public SplashScreen(RunningJoe game)
    {
        super( game );
    }

    @Override
    public void show()
    {
        super.show();
        
        spriteBatch = new SpriteBatch();
        splash = new Texture(Gdx.files.internal("images/splash.png"));

        // start playing the menu music
        //game.getMusicManager().play( RunningJoeMusic.MENU );
    }
    
    @Override
    public void render(float delta)
    {
    	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.begin();
        spriteBatch.draw(splash, 0, 0);
        spriteBatch.end();
        
        if(Gdx.input.justTouched())
                this.game.setScreen(new MenuScreen(this.game));
    }
}