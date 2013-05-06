package efficom.runningjoe.ui;

import efficom.runningjoe.RunningJoe;
import efficom.runningjoe.services.RunningJoeSound;
import efficom.runningjoe.services.SoundManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * The option screen and the logic
 * @author Guillaume BAILLEUL
 */
public class OptionsScreen extends AbstractMenuItemScreen{
	TextButton cancelButton, validateButton;
	SelectBox langOption;
	
	public OptionsScreen(RunningJoe game)
	{
	 super( game, "Options" );       
	}
	
	@Override
	public void show(){
		super.show();
		
		// Language option -----------------------------------------------------
		/*langOption = new SelectBox(
				new String[]{"fr-FR", "en-UK"}, 
				this.selectBoxStyle);*/
		//super.getTable().add(langOption).uniform().fill().spaceRight( 10 );
		
		// Cancel button -------------------------------------------------------
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
				SoundManager.getInstance().play( RunningJoeSound.CLICK );
		        game.setScreen( new MainScreen( game ) );
		        return true;
		    }
		 });
		     
		// Validate button -----------------------------------------------------
		validateButton = new TextButton( 
			this.getLanguagesManager().getString("Save"),  
			this.buttonStyle 
		);
		
		super.getTable().add(validateButton).uniform().fill().spaceBottom( 10 );
		validateButton.addListener( new InputListener() {
		    @Override
		    public boolean touchDown(InputEvent event,float x, float y, int pointer, int button )
		    {
		    	// Save the parameters
		    	
		    	// Return to the main screen
		     	Gdx.app.log( RunningJoe.LOG, "Validate clicked: " + getName() );
		     	SoundManager.getInstance().play( RunningJoeSound.CLICK );
		       	game.setScreen( new MainScreen( game ) );
		       	return true;
		    }
		});
	}
}
