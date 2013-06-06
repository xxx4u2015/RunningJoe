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

public class RegisterScreen extends AbstractMenuItemScreen {
    private TextButton registerButton, cancelButton;
    private TextField passwordText, playerText, errorText, confirmText, emailText;

    public RegisterScreen()
    {
        super("Register");
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
        Label emailLabel = new Label(
                this.getLanguagesManager().getString("Email"),
                this.labelStyle
        );
        this.getTable().add(emailLabel).uniform().spaceBottom(10);

        // register the button login label
        emailText = new TextField(  "", this.textFieldStyle );
        this.getTable().add(emailText).uniform().spaceBottom(10);
        this.getTable().row();

        // PASSWORD ENTRIES-----------------------------------------------------
        // register the button login label
        Label passwordlabel = new Label(
                this.getLanguagesManager().getString("Password"),
                this.labelStyle
        );
        this.getTable().add(passwordlabel).uniform().spaceBottom(10);

        // register the button login label
        passwordText = new TextField(  "", this.textFieldStyle );
        passwordText.setPasswordCharacter('*');
        passwordText.setPasswordMode(true);
        this.getTable().add(passwordText).uniform().spaceBottom(10);
        this.getTable().row();

        // PASSWORD ENTRIES-----------------------------------------------------
        // register the button login label
        Label confirmLabel = new Label(
                this.getLanguagesManager().getString("Confirm pass"),
                this.labelStyle
        );
        this.getTable().add(confirmLabel).uniform().spaceBottom(10);

        // register the button login label
        confirmText = new TextField(  "", this.textFieldStyle );
        confirmText.setPasswordCharacter('*');
        confirmText.setPasswordMode(true);
        this.getTable().add(confirmText).uniform().spaceBottom(10);
        this.getTable().row();

        // ERROR FIELD----------------------------------------------------------
        errorText = new TextField("", this.textFieldStyle);
        this.getTable().add(errorText).uniform().spaceBottom(10).colspan(2);
        this.getTable().row();

        // Connect button-------------------------------------------------------
        registerButton = new TextButton(
                this.getLanguagesManager().getString("Register") ,
                this.buttonStyle
        );
        super.getTable().add(registerButton).uniform().fill().spaceRight( 10 );
        registerButton.addListener( new InputListener() {
            @Override
            public boolean touchDown(InputEvent event,float x, float y, int pointer, int button )
            {
                Gdx.app.log( RunningJoe.LOG, "Register clicked: " + getName() );
                SoundManager.getInstance().play( RunningJoeSound.CLICK );

                // Password check
                if(!confirmText.getText().equals(passwordText.getText())){
                    errorText.setText("Password don't match.");
                }else{
                    if(playerText.getText().equals("") || emailText.getText().equals("") || passwordText.getText().equals("")){
                        errorText.setText("Some fields are empty.");
                    }else{

                        if(emailText.getText().matches("^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)+$"))
                        {
                            // Register
                            String ret = SoapManager.getInstance().Register(
                                    playerText.getText(),
                                    emailText.getText(),
                                    passwordText.getText());

                            if(ret == null)
                                RunningJoe.getInstance().setScreen( new LoginScreen());
                            else
                                errorText.setText(ret);
                        }else{
                            errorText.setText("Invalid email.");
                        }

                    }
                }

                return true;
            }
        });

        // Ignore
        cancelButton = new TextButton(
                this.getLanguagesManager().getString("Cancel"),
                this.buttonStyle
        );
        super.getTable().add(cancelButton).uniform().fill().spaceBottom( 10 );
        cancelButton.addListener( new InputListener() {
            @Override
            public boolean touchDown(InputEvent event,float x, float y, int pointer, int button )
            {
                Gdx.app.log( RunningJoe.LOG, "Cancel clicked: " + getName() );
                SoundManager.getInstance().play( RunningJoeSound.CLICK );
                RunningJoe.getInstance().setScreen( new LoginScreen( ) );
                return true;
            }
        });


    }
}
