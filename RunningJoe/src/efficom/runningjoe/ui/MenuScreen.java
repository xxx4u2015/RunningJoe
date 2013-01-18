package efficom.runningjoe.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import efficom.runningjoe.RunningJoe;
import efficom.runningjoe.services.RunningJoeSound;

public class MenuScreen extends AbstractScreen
{
    public MenuScreen(RunningJoe game )
    {
        super( game );       
    }

    @Override
    public void show()    {
        super.show();

        // retrieve the default table actor
        this.getTable().add( "Running Joe !" ).spaceBottom( 50 );
        this.getTable().row();
        
        // register the button "start game"
        TextButton startGameButton = new TextButton( "Start game", this.buttonStyle );        
        startGameButton.addListener( new InputListener() {         	    		
        	@Override
            public boolean touchDown( InputEvent event, float x, float y, int pointer, int button )
            {
        		Gdx.app.log( RunningJoe.LOG, "Game start clicked: " + getName() );
        		game.getSoundManager().play( RunningJoeSound.CLICK );
                super.touchUp( event, x, y, pointer, button );                
                return true;
            }
        } );
        
        this.getTable().add(startGameButton).size(300, 60).uniform().spaceBottom(10);
        this.getTable().row();
        
        // register the button "options"
        TextButton optionsButton = new TextButton( "Options", this.buttonStyle );
        super.getTable().add(optionsButton).size(300, 60).uniform().spaceBottom(10);
        super.getTable().row();
        optionsButton.addListener( new InputListener() {
            @Override
            public boolean touchDown(InputEvent event,float x, float y, int pointer, int button )
            {
            	Gdx.app.log( RunningJoe.LOG, "Game options clicked: " + getName() );
                super.touchUp( event, x, y, pointer, button );
                game.getSoundManager().play( RunningJoeSound.CLICK );
                game.setScreen( new OptionsScreen( game ) );
            	return true;
            }
        } );

        // register the button "high scores"
        TextButton highScoresButton = new TextButton( "High Scores", this.buttonStyle );
        super.getTable().add(highScoresButton).uniform().fill().spaceBottom( 10 );
        super.getTable().row();
        highScoresButton.addListener( new InputListener() {
            @Override
            public boolean touchDown(InputEvent event,float x, float y, int pointer, int button )
            {
            	Gdx.app.log( RunningJoe.LOG, "High scores clicked: " + getName() );
            	game.getSoundManager().play( RunningJoeSound.CLICK );
                super.touchUp( event, x, y, pointer, button );            	
                game.setScreen( new HighScoresScreen( game ) );
            	return true;
            }
        } );
        
        TextButton exitButton = new TextButton( "Exit", this.buttonStyle );
        super.getTable().add(exitButton).uniform().fill().spaceBottom( 10 );
        super.getTable().row();
        exitButton.addListener( new InputListener() {
            @Override
            public boolean touchDown(InputEvent event,float x, float y, int pointer, int button )
            {
            	Gdx.app.log( RunningJoe.LOG, "Exit clicked: " + getName() );
            	game.getSoundManager().play( RunningJoeSound.CLICK );
            	Gdx.app.exit();
            	return true;
            }
        } );
    }
}
