package efficom.runningjoe.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import efficom.runningjoe.RunningJoe;

public abstract class AbstractMenuItemScreen extends AbstractScreen{	
	private String title;
	
	public AbstractMenuItemScreen(RunningJoe game, String titleKey )
    {
        super( game );
        this.title = this.getLanguagesManager().getString(titleKey);
    }
	
	@Override
    public void render(float delta)
    {
    	stage.act( delta );
    	
        // clear the screen with the given RGB color (black)
        Gdx.gl.glClearColor( 0.5f, 0.5f, 0.5f, 1f );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );
        
        this.game.getWorld().render();
        
        // draw the actors
        stage.draw();

        // draw the table debug lines
        if(RunningJoe.DEV_MODE )Table.drawDebug( stage );
        
    }

	
	@Override
	public void show()
	{
		super.show();
		this.game.getWorld().render();
		
		// retrieve the default table actor
        this.getTable().add( this.title ).spaceBottom( 20 ).center();
        this.getTable().row();
		
        /*cancelButton = new TextButton( 
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
	    } );*/
	}
}
