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
import efficom.runningjoe.services.SoundManager;

public class GameOverScreen extends AbstractMenuItemScreen{
	private TextButton validateButton, cancelButton;
	private Score score;
	
	public GameOverScreen()
	{
		super("GameOver");
		this.score = RunningJoe.getInstance().getWorld().getScore();
		
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

        validateButton = new TextButton(
                this.getLanguagesManager().getString("Restart") ,
                this.buttonStyle
        );

        super.getTable().add(validateButton).spaceTop(20);
        this.getTable().row();


        validateButton.addListener( new InputListener() {
            @Override
            public boolean touchDown(InputEvent event,float x, float y, int pointer, int button )
            {
                Gdx.app.log( RunningJoe.LOG, "Restart clicked: " + getName() );
                SoundManager.getInstance().play( RunningJoeSound.CLICK );
                RunningJoe.getInstance().setScreen( new MainScreen() );
                RunningJoe.getInstance().restart();
                RunningJoe.getInstance().getWorld().Start();

                return true;
            }
        });

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
	           	SoundManager.getInstance().play( RunningJoeSound.CLICK );
                RunningJoe.getInstance().restart();
	           	RunningJoe.getInstance().setScreen( new MainScreen() );
	           	return true;
	        }
	    });

	}
}
