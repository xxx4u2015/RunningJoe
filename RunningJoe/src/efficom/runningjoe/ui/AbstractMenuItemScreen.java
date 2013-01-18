package efficom.runningjoe.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import efficom.runningjoe.RunningJoe;
import efficom.runningjoe.services.RunningJoeSound;

public abstract class AbstractMenuItemScreen extends AbstractScreen{
	private TextButton validateButton, cancelButton;
	private String title;
	
	public AbstractMenuItemScreen(RunningJoe game, String titleKey )
    {
        super( game );
        this.title = this.getLanguagesManager().getString(titleKey);
    }
	
	@Override
	public void show()
	{
		super.show();
		
		// retrieve the default table actor
        this.getTable().add( this.title ).spaceBottom( 50 ).center();
        this.getTable().row();
		
        cancelButton = new TextButton( 
        		this.getLanguagesManager().getString("Cancel") , 
        		this.buttonStyle 
        );
	    super.getTable().add(cancelButton).uniform().fill().spaceRight( 10 );
	    cancelButton.addListener( new InputListener() {
	        @Override
	        public boolean touchDown(InputEvent event,float x, float y, int pointer, int button )
	        {
	        	Gdx.app.log( RunningJoe.LOG, "Cancel clicked: " + getName() );
	           	game.getSoundManager().play( RunningJoeSound.CLICK );
	           	game.setScreen( new MainScreen( game ) );
	           	return true;
	        }
	    });
	        
	    // Validate button
		validateButton = new TextButton( 
				this.getLanguagesManager().getString("Save"),  
				this.buttonStyle 
		);
	    super.getTable().add(validateButton).uniform().fill().spaceBottom( 10 );
	    validateButton.addListener( new InputListener() {
	        @Override
	        public boolean touchDown(InputEvent event,float x, float y, int pointer, int button )
	        {
	         	Gdx.app.log( RunningJoe.LOG, "Validate clicked: " + getName() );
	           	game.getSoundManager().play( RunningJoeSound.CLICK );
	           	game.setScreen( new MainScreen( game ) );
	           	return true;
	        }
	    } );
	}
}
