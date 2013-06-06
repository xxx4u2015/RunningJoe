package efficom.runningjoe.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import efficom.runningjoe.RunningJoe;
import efficom.runningjoe.services.RunningJoeSound;
import efficom.runningjoe.services.SoundManager;

public class ForgottenScreen extends AbstractMenuItemScreen {
    private TextButton sendButton, cancelButton;
    private TextField errorText, emailText;

    public ForgottenScreen()
    {
        super("Forgotten");
    }

    @Override
    public void show(){
        super.show();
        this.createMenu();
    }

    public void createMenu()
    {

        // register the button login label
        Label emailLabel = new Label(
                this.getLanguagesManager().getString("Email"),
                this.labelStyle
        );
        this.getTable().add(emailLabel).uniform().spaceBottom(10);

        // register the button login label
        emailText = new TextField(  "", this.textFieldStyle );
        emailText.setPasswordCharacter('*');
        emailText.setPasswordMode(true);
        this.getTable().add(emailText).uniform().spaceBottom(10);
        this.getTable().row();


        // ERROR FIELD----------------------------------------------------------
        errorText = new TextField("", this.textFieldStyle);
        this.getTable().add(errorText).uniform().spaceBottom(10).colspan(2);
        this.getTable().row();

        // Connect button-------------------------------------------------------
        sendButton = new TextButton(
                this.getLanguagesManager().getString("Send") ,
                this.buttonStyle
        );
        super.getTable().add(sendButton).uniform().fill().spaceRight( 10 );
        sendButton.addListener( new InputListener() {
            @Override
            public boolean touchDown(InputEvent event,float x, float y, int pointer, int button )
            {
                Gdx.app.log( RunningJoe.LOG, "Send clicked: " + getName() );
                SoundManager.getInstance().play( RunningJoeSound.CLICK );

               /* if(ret == null)
                    RunningJoe.getInstance().setScreen( new MainScreen( ) );
                else
                    errorText.setText(ret);   */

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
                RunningJoe.getInstance().setScreen( new LoginScreen());
                return true;
            }
        });


    }
}
