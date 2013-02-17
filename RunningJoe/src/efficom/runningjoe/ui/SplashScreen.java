package efficom.runningjoe.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.TextureAtlasData.Region;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;

import efficom.runningjoe.RunningJoe;
import efficom.runningjoe.ui.MainScreen;

/**
 * Shows a splash image and moves on to the next screen.
 */
public class SplashScreen extends AbstractScreen
{
    private SpriteBatch spriteBatch;
    private Texture splash;
    Image splashImage;

    public SplashScreen(RunningJoe game){ 
    	super( game );
    	spriteBatch = new SpriteBatch();
    	splashImage = new Image(new Texture(Gdx.files.internal("images/splash.png")));
        splashImage.setFillParent( true );
        
    }

    @Override
    public void show()
    {
        //super.show();
        // here we create the splash image actor; its size is set when the
        // resize() method gets called
        

        // this is needed for the fade-in effect to work correctly; we're just
        // making the image completely transparent
        

        // configure the fade-in/out effect on the splash image
        //splashImage.addAction(sequence( fadeIn( 0.75f ), delay( 0.75f ), fadeOut( 0.75f )));

        // and finally we add the actor to the stage
        //stage.addActor( splashImage );
    }
    
    @Override
    public void render(float delta)
    {
    	
    	super.render(delta);
    	
    	//super.render(delta);
    	//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.begin();
        splashImage.draw(spriteBatch, 0);
        spriteBatch.end();
        
        if(Gdx.input.justTouched())
        	this.game.setScreen(new MainScreen(this.game));
    }
}