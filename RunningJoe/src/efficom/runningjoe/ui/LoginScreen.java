package efficom.runningjoe.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import efficom.runningjoe.RunningJoe;
import efficom.runningjoe.services.RunningJoeSound;
import efficom.runningjoe.services.SoapManager;

public class LoginScreen extends AbstractMenuItemScreen {
	private TextButton connectButton, ignoreButton;
	private TextField passwordText, playerText, errorText;
	
	public LoginScreen(RunningJoe game)
	{
		super(game, "Login");       
	}
	
	@Override
	public void show(){
		super.show();	 
		this.createMenu();
	}
	
	public void createMenu()
	{
		//PLAYER ENTRIES--------------------------------------------------------
		// register the button login label
		Label playerlabel = new Label( 
				this.getLanguagesManager().getString("Playername"), 
				this.labelStyle
				);
		this.getTable().add(playerlabel).size(300, 60).uniform().spaceBottom(10);
	        
		// register the button login label
		playerText = new TextField( "", this.textFieldStyle);
		this.getTable().add(playerText).size(300, 60).uniform().spaceBottom(10);
		this.getTable().row();
	 
		// PASSWORD ENTRIES-----------------------------------------------------
		// register the button login label
		Label passwordlabel = new Label( 
				this.getLanguagesManager().getString("Password"), 
				this.labelStyle
				);
		this.getTable().add(passwordlabel).size(300, 60).uniform().spaceBottom(10);
	        
		// register the button login label
		passwordText = new TextField(  "", this.textFieldStyle );		 
		passwordText.setPasswordCharacter('*');
		passwordText.setPasswordMode(true);
		this.getTable().add(passwordText).size(300, 60).uniform().spaceBottom(10);
		this.getTable().row();
	 
		// ERROR FIELD----------------------------------------------------------
		errorText = new TextField("", this.textFieldStyle);
		this.getTable().add(errorText).size(300, 60).uniform().spaceBottom(10);
		this.getTable().row();
	 
		// Ignore
		ignoreButton = new TextButton( 
				this.getLanguagesManager().getString("Continue"),  
				this.buttonStyle 
				);
		super.getTable().add(ignoreButton).uniform().fill().spaceBottom( 10 );
		ignoreButton.addListener( new InputListener() {
			@Override
			public boolean touchDown(InputEvent event,float x, float y, int pointer, int button )
			{
				Gdx.app.log( RunningJoe.LOG, "Validate clicked: " + getName() );
				game.getSoundManager().play( RunningJoeSound.CLICK );
				game.setScreen( new MainScreen( game ) );
				return true;
			}
		});
	 
		// Connect button-------------------------------------------------------
		connectButton = new TextButton( 
				this.getLanguagesManager().getString("Connect") , 
				this.buttonStyle 
				);
		super.getTable().add(connectButton).uniform().fill().spaceRight( 10 );
		connectButton.addListener( new InputListener() {
			@Override
			public boolean touchDown(InputEvent event,float x, float y, int pointer, int button )
			{
				Gdx.app.log( RunningJoe.LOG, "Cancel clicked: " + getName() );
				game.getSoundManager().play( RunningJoeSound.CLICK );
	       	
				// Login logic--------------------------------------------------
				String ret = SoapManager.getInstance().Login(playerText.getText(),
	       		passwordText.getText());
	       	
				if(ret == null)
					game.setScreen( new MainScreen( game ) );
				else
					errorText.setText(ret);	
	       	
				return true;
			}
		});
	}
}
