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
import efficom.runningjoe.services.SoundManager;

public class LoginScreen extends AbstractMenuItemScreen {
	private TextButton connectButton, ignoreButton, registerButton, forgottenButton;
	private TextField passwordText, playerText, errorText;
	
	public LoginScreen()
	{
		super("Login");
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
		this.getTable().add(playerlabel).uniform().spaceBottom(10);
	        
		// register the button login label
		playerText = new TextField( "", this.textFieldStyle);
		this.getTable().add(playerText).uniform().spaceBottom(10);
		this.getTable().row();
	 
		// PASSWORD ENTRIES-----------------------------------------------------
		// register the button login label
		Label passwordlabel = new Label( 
				this.getLanguagesManager().getString("Password"), 
				this.labelStyle
				);
		this.getTable().add(passwordlabel);
	        
		// register the button login label
		passwordText = new TextField(  "", this.textFieldStyle );		 
		passwordText.setPasswordCharacter('*');
		passwordText.setPasswordMode(true);
		this.getTable().add(passwordText);
		this.getTable().row();
	 
		// ERROR FIELD----------------------------------------------------------
		errorText = new TextField("", this.transtextFieldStyle);
		this.getTable().add(errorText).uniform().spaceBottom(10).colspan(2).fill();
		this.getTable().row();
	 
		// Ignore
		ignoreButton = new TextButton( 
				this.getLanguagesManager().getString("Anonymous"),
				this.buttonStyle 
				);
		super.getTable().add(ignoreButton).uniform().fill().spaceBottom( 10 );
		ignoreButton.addListener( new InputListener() {
			@Override
			public boolean touchDown(InputEvent event,float x, float y, int pointer, int button )
			{
				Gdx.app.log( RunningJoe.LOG, "Validate clicked: " + getName() );
				SoundManager.getInstance().play( RunningJoeSound.CLICK );
				RunningJoe.getInstance().setScreen( new MainScreen( ) );
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
				SoundManager.getInstance().play( RunningJoeSound.CLICK );
	       	
				// Login logic--------------------------------------------------
				String ret = SoapManager.getInstance().Login(playerText.getText(),
	       		passwordText.getText());
	       	
				if(ret == null)
					RunningJoe.getInstance().setScreen( new MainScreen( ) );
				else
					errorText.setText(ret);	
	       	
				return true;
			}
		});

        // ROW ---------------------------------------------------------------------------------------------------------
        this.getTable().row();

        // Register button ---------------------------------------------------------------------------------------------
        /*registerButton = new TextButton(
                this.getLanguagesManager().getString("Register"),
                this.buttonStyle
        );
        super.getTable().add(registerButton).uniform().fill().spaceBottom( 10 );
        registerButton.addListener( new InputListener() {
            @Override
            public boolean touchDown(InputEvent event,float x, float y, int pointer, int button )
            {
                Gdx.app.log( RunningJoe.LOG, "Register clicked: " + getName() );
                SoundManager.getInstance().play( RunningJoeSound.CLICK );
                RunningJoe.getInstance().setScreen( new RegisterScreen( ) );
                return true;
            }
        });*/

        // Forgotten button ---------------------------------------------------------------------------------------------
        /*forgottenButton = new TextButton(
                this.getLanguagesManager().getString("Forgotten"),
                this.buttonStyle
        );
        super.getTable().add(forgottenButton).uniform().fill().spaceBottom( 10 );
        forgottenButton.addListener( new InputListener() {
            @Override
            public boolean touchDown(InputEvent event,float x, float y, int pointer, int button )
            {
                Gdx.app.log( RunningJoe.LOG, "Forgottend clicked: " + getName() );
                SoundManager.getInstance().play( RunningJoeSound.CLICK );
                RunningJoe.getInstance().setScreen( new ForgottenScreen( ) );
                return true;
            }
        });*/
	}
}
