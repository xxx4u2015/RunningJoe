package efficom.runningjoe.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import efficom.runningjoe.RunningJoe;

public abstract class AbstractMenuItemScreen extends AbstractScreen{	
	private String title;
	
	public AbstractMenuItemScreen(String titleKey )
    {
        super();
        this.title = this.getLanguagesManager().getString(titleKey);
    }
	
	@Override
    public void render(float delta)
    {
    	stage.act( delta );
    	
        // clear the screen with the given RGB color (black)
        Gdx.gl.glClearColor( 0.5f, 0.5f, 0.5f, 1f );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );

        RunningJoe.getInstance().getWorld().render();
        
        // draw the actors
        stage.draw();

        // draw the table debug lines
        if(RunningJoe.DEV_MODE )Table.drawDebug( stage );        
    }

	
	@Override
	public void show()
	{
		super.show();
		RunningJoe.getInstance().getWorld().render();
		
		// retrieve the default table actor
        this.getTable().add( 
        		this.getLanguagesManager().getString(title)).spaceBottom(50).colspan(2);
        this.getTable().row();		
	}
}
