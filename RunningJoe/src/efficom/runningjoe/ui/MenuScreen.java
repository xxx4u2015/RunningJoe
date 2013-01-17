package efficom.runningjoe.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import efficom.runningjoe.RunningJoe;
import efficom.runningjoe.services.SoundManager.RunningJoeSound;

public class MenuScreen extends AbstractScreen
{
    public MenuScreen(RunningJoe game )
    {
        super( game );       
    }

    @Override
    public void show()
    {
        super.show();

        // retrieve the default table actor
        //Table table = super.getTable();
        this.getTable().add( "Welcome to Running Joe !" ).spaceBottom( 50 );
        this.getTable().row();

        // register the button "start game"
        TextButton startGameButton = new TextButton( "Start game", this.buttonStyle );
        this.getTable().add(startGameButton).size(300, 60).uniform().spaceBottom(10);
        this.getTable().row();
        startGameButton.addListener( new InputListener() {        	
        	@Override
            public void touchUp( InputEvent event, float x, float y, int pointer, int button )
            {
        		Gdx.app.log( RunningJoe.LOG, "Game start clicked: " + getName() );
                //super.touchUp( event, x, y, pointer, button );
                //game.getSoundManager().play( RjSound.CLICK );
                //game.setScreen( new SplashScreen( game ) );
            }
        	        	
        	/*@Override
            public void click(Actor actor,float x,float y )
            {
                System.out.println("hiii");
                //Assets.load();
               //game.getSoundManager().play( TyrianSound.CLICK );
                //game.setScreen( new GameScreen(game) );
            }*/
        } );/*
        //table.add( startGameButton ).size( 300, 60 ).uniform().spaceBottom( 10 );
        //table.row();*/

        // register the button "options"
        TextButton optionsButton = new TextButton( "Options", this.buttonStyle );
        this.getTable().add(optionsButton).size(300, 60).uniform().spaceBottom(10);
        this.getTable().row();
        /*optionsButton.addListener( new DefaultActorListener() {
            @Override
            public void touchUp(
                I event,
                float x,
                float y,
                int pointer,
                int button )
            {
                super.touchUp( event, x, y, pointer, button );
                game.getSoundManager().play( RjSound.CLICK );
                game.setScreen( new OptionsScreen( game ) );
            }
        } );
        table.add( optionsButton ).uniform().fill().spaceBottom( 10 );
        table.row();*/

        // register the button "high scores"
        TextButton highScoresButton = new TextButton( "High Scores", this.buttonStyle );
        this.getTable().add(highScoresButton).uniform().fill().spaceBottom( 10 );
        this.getTable().row();
        /*highScoresButton.addListener( new DefaultActorListener() {
            @Override
            public void touchUp(
                ActorEvent event,
                float x,
                float y,
                int pointer,
                int button )
            {
                super.touchUp( event, x, y, pointer, button );
                game.getSoundManager().play( RjSound.CLICK );
                game.setScreen( new HighScoresScreen( game ) );
            }
        } );
        //table.add( highScoresButton ).uniform().fill();*/
    }
}
