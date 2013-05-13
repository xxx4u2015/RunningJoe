package efficom.runningjoe.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import efficom.runningjoe.RunningJoe;

/**
 * Shows a splash image and moves on to the next screen.
 */
public class SplashScreen extends AbstractScreen
{
    Image splashImage;

    public SplashScreen(RunningJoe game){ 
    	super( game );
    	splashImage = new Image(new Texture(Gdx.files.internal("images/splash.png")));
        splashImage.setFillParent( false );
    }

    @Override
    public void show()
    {
        super.show();
        // this is needed for the fade-in effect to work correctly; we're just
        // making the image completely transparent
        splashImage.getColor().a = 0f;

        // configure the fade-in/out effect on the splash image
        splashImage.addAction( sequence( fadeIn( 0.75f ), delay( 1.75f ), fadeOut( 0.75f ),
            new Action() {
                @Override
                public boolean act(
                    float delta )
                {
                    // the last action will move to the next screen
                    game.setScreen( new LoginScreen( game ) );
                    return true;
                }
            } ) );

        // and finally we add the actor to the stage
        stage.addActor( splashImage );
        // here we create the splash image actor; its size is set when the
        // resize() method gets called
        

        // this is needed for the fade-in effect to work correctly; we're just
        // making the image completely transparent
        

        // configure the fade-in/out effect on the splash image
        splashImage.addAction(sequence( fadeIn( 0.75f ), delay( 0.75f ), fadeOut( 0.75f )));

        // and finally we add the actor to the stage
        stage.addActor( splashImage );
    }
    
    /*@Override
    public void render(float delta)
    {
    	
    	super.render(delta);
        
        if(Gdx.input.justTouched())
        	this.game.setScreen(new LoginScreen(this.game));
    }*/
}