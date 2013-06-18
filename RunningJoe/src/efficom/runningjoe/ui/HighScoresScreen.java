package efficom.runningjoe.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import efficom.runningjoe.RunningJoe;
import efficom.runningjoe.services.RunningJoeSound;
import efficom.runningjoe.services.SoapManager;
import efficom.runningjoe.services.SoundManager;
import efficom.runningjoe.ws.userapi.ArrayOfString;

import java.util.ArrayList;

public class HighScoresScreen extends AbstractMenuItemScreen{
	private TextButton cancelButton;
	
	public HighScoresScreen( )
	{
	 super( "HighScores" );
	}
	
	@Override
	public void show(){
		super.show();
		this.createScores();
	 
	}
	
	private void createScores()
	{
		ArrayList<String> scores = SoapManager.getInstance().GetHighScoresStrings();
		if(scores!=null){
			for(String item: scores){
				Label lbl = new Label(item, this.labelStyle);	
				super.getTable().add(lbl).uniform().fill().spaceRight( 10 ).colspan(2);
				this.getTable().row();				
			}			
		}
		
		cancelButton = new TextButton( 
	       		this.getLanguagesManager().getString("Return") , 
	       		this.buttonStyle 
				);
	    super.getTable().add(cancelButton).uniform().fill().spaceRight( 10 ).spaceTop(20).colspan(2);
	    cancelButton.addListener( new InputListener() {
	        @Override
	        public boolean touchDown(InputEvent event,float x, float y, int pointer, int button )
	        {
	        	Gdx.app.log( RunningJoe.LOG, "Cancel clicked: " + getName() );
	        	SoundManager.getInstance().play( RunningJoeSound.CLICK );
	           	RunningJoe.getInstance().setScreen( new MainScreen() );
	           	return true;
	        }
	    });
	}
}
