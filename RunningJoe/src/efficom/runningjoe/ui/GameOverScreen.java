package efficom.runningjoe.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import efficom.runningjoe.RunningJoe;
import efficom.runningjoe.core.Score;
import efficom.runningjoe.services.RunningJoeSound;
import efficom.runningjoe.services.SoapManager;

public class GameOverScreen extends AbstractMenuItemScreen{
	private TextButton validateButton, cancelButton;
	private Score score;
	
	public GameOverScreen(RunningJoe game, Score score )
	{		
		super( game, "GameOver" );
		this.score = score;
		
		// Try to record the score
		if(SoapManager.getInstance().getIsConnected()){
			SoapManager.getInstance().RecordScore(score.getValue());
		}
	}
	
	@Override
	public void show(){
		super.show();
		this.createMenu();	 
	}
	
	private void createMenu()
	{
		Label lbl = new Label(
				this.getLanguagesManager().getString("YourScoreIs") + " " + (int) score.getValue(), 
				this.labelStyle
				);
		super.getTable().add(lbl).colspan(2).center();
		this.getTable().row();	
		
		cancelButton = new TextButton( 
	       		this.getLanguagesManager().getString("MainMenu") , 
	       		this.buttonStyle 
				);
		
	    super.getTable().add(cancelButton).spaceTop(20);
	    
	    cancelButton.addListener( new InputListener() {
	        @Override
	        public boolean touchDown(InputEvent event,float x, float y, int pointer, int button )
	        {
	        	Gdx.app.log( RunningJoe.LOG, "Cancel clicked: " + getName() );
	           	game.getSoundManager().play( RunningJoeSound.CLICK );
	           	game.restart();
	           	game.setScreen( new MainScreen( game ) );
	           	return true;
	        }
	    });
	    
	    validateButton = new TextButton( 
	       		this.getLanguagesManager().getString("Restart") , 
	       		this.buttonStyle 
				);
		
	    super.getTable().add(validateButton).spaceTop(20);
	    
	    validateButton.addListener( new InputListener() {
	        @Override
	        public boolean touchDown(InputEvent event,float x, float y, int pointer, int button )
	        {
	        	Gdx.app.log( RunningJoe.LOG, "Restart clicked: " + getName() );
	           	game.getSoundManager().play( RunningJoeSound.CLICK );
	           	game.restart();
	           	game.getWorld().Start();
	           	game.setScreen( new MainScreen( game ) );
	           	return true;
	        }
	    });	    
	}
}
