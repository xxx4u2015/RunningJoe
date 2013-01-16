package efficom.runningjoe.ui;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import efficom.runningjoe.RunningJoe;

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
        //table.add( "Welcome to Tyrian for Android!" ).spaceBottom( 50 );
        //table.row();

        // register the button "start game"
        TextButton startGameButton = new TextButton( "Start game", this.buttonStyle );
        this.getTable().add(startGameButton);
        /*startGameButton.addListener( new DefaultActorListener() {
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
                game.setScreen( new StartGameScreen( game ) );
            }
        } );
        //table.add( startGameButton ).size( 300, 60 ).uniform().spaceBottom( 10 );
        //table.row();*/

        // register the button "options"
        TextButton optionsButton = new TextButton( "Options", this.buttonStyle );
        this.getTable().add(optionsButton);
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
        this.getTable().add(highScoresButton);
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
