package efficom.runningjoe.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import efficom.runningjoe.RunningJoe;
import efficom.runningjoe.core.RjWorld;
import efficom.runningjoe.services.RunningJoeSound;
import efficom.runningjoe.services.SoundManager;

public class MainScreen extends AbstractScreen
{
	private boolean menuDawn = false;
	Label lblScore;
	
    public MainScreen()
    {
        super();
        
        lblScore = new Label(""+(int)RunningJoe.getInstance().getWorld().getScore().getValue(),
				this.labelStyle
				);
    }

    @Override
    public void render(float delta)
    {
        RunningJoe.getInstance().getWorld().render();
    	
    	// If the game has started handle the pressed keys
    	if(RunningJoe.getInstance().getWorld().isStarded() && !menuDawn){
	    	if(Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.justTouched())
	        	RunningJoe.getInstance().getWorld().getJoe().Jump();

	    	
		    // Move to the left
		    if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
                RunningJoe.getInstance().getWorld().getJoe().Move(false, 5f);

            if(Gdx.input.getAccelerometerY() < -2f)
                RunningJoe.getInstance().getWorld().getJoe().Move(false, 5f);
		    	
		    // Move to the right
		    if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
                RunningJoe.getInstance().getWorld().getJoe().Move(true, 5.0f);

            if(Gdx.input.getAccelerometerY() > 2f)
                RunningJoe.getInstance().getWorld().getJoe().Move(true, 5.0f);

	        
	        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE) || Gdx.input.isKeyPressed(Input.Keys.MENU))
                RunningJoe.getInstance().getWorld().Pause();
    	}
        
    	RunningJoe.getInstance().getWorld().render();
    	super.render(delta); 
    	
    	if(RunningJoe.getInstance().getWorld().isStarded()){
    		this.getTable().clear();
    		this.menuDawn = false;
    		
    		try{
	    		this.lblScore.setText(
	    				this.getLanguagesManager().getString("Score") + " : " + 
	    				(int)RunningJoe.getInstance().getWorld().getScore().getValue()
	    			);
	    		this.getTable().left().top();    		
		    	this.getTable().add(this.lblScore);
    		}catch(Exception e){
    			
    		}
    	}else{    		
    		this.createMenu();
    	}    	
    }     
    
    @Override
    public void show(){
        super.show();

        RunningJoe.getInstance().getWorld().render();
        
        if(!RunningJoe.getInstance().getWorld().isStarded()){
        	this.createMenu();
        }else{
        		
        }
    }
    
    public void createMenu()
    {
    	if(!this.menuDawn){
    		this.getTable().clear();
    		this.getTable().center();
    		
	    	// retrieve the default table actor
	        this.getTable().add( "Running Joe !" ).spaceBottom( 50 );
	        this.getTable().row();
	        
	        if(RunningJoe.getInstance().getWorld().getScore().getValue() > 0){
		        // register the button "start game"
		        TextButton resumeGameButton = new TextButton( 
		        		this.getLanguagesManager().getString("Resume"), 
		        		this.buttonStyle 
		        );        
		        resumeGameButton.addListener( new InputListener() {         	    		
		        	@Override
		            public boolean touchDown( InputEvent event, float x, float y, int pointer, int button )
		            {
		        		Gdx.app.log( RunningJoe.LOG, "Game resume clicked: " + getName() );
		        		SoundManager.getInstance().play( RunningJoeSound.CLICK );
		                super.touchUp( event, x, y, pointer, button );
                        RunningJoe.getInstance().getWorld().Start();
		                return true;
		            }
		        });
		        
		        this.getTable().add(resumeGameButton).colspan(2).uniform().spaceBottom(10);
		        this.getTable().row();
		    }
	        
	        
	        // register the button "start game"
	        TextButton startGameButton = new TextButton( 
	        		this.getLanguagesManager().getString("Play"), 
	        		this.buttonStyle 
	        );

	        startGameButton.addListener( new InputListener() {         	    		
	        	@Override
	            public boolean touchDown( InputEvent event, float x, float y, int pointer, int button )
	            {
	        		Gdx.app.log( RunningJoe.LOG, "Game start clicked: " + getName() );
	        		SoundManager.getInstance().play( RunningJoeSound.CLICK );
	                super.touchUp( event, x, y, pointer, button );
                    RunningJoe.getInstance().restart();
                    RunningJoe.getInstance().getWorld().Start();
	                return true;
	            }
	        });
	        
	        this.getTable().add(startGameButton).colspan(2).uniform().spaceBottom(10);
	        this.getTable().row();
	        
	        // register the button "options"
	        /*TextButton optionsButton = new TextButton( 
	        		this.getLanguagesManager().getString("Options"), 
	        		this.buttonStyle 
	        );
	        super.getTable().add(optionsButton).colspan(2).uniform().spaceBottom(10);
	        super.getTable().row();
	        optionsButton.addListener( new InputListener() {
	            @Override
	            public boolean touchDown(InputEvent event,float x, float y, int pointer, int button )
	            {
	            	Gdx.app.log( RunningJoe.LOG, "Game options clicked: " + getName() );
	                super.touchUp( event, x, y, pointer, button );
	                SoundManager.getInstance().play( RunningJoeSound.CLICK );
	                RunningJoe.getInstance().setScreen( new OptionsScreen() );
	            	return true;
	            }
	        } );*/
	
	        // register the button "high scores"
	        TextButton highScoresButton = new TextButton( 
	        		this.getLanguagesManager().getString("HighScores"), 
	        		this.buttonStyle 
	        );
	        super.getTable().add(highScoresButton).uniform().fill().spaceBottom( 10 ).colspan(2);
	        super.getTable().row();
	        highScoresButton.addListener( new InputListener() {
	            @Override
	            public boolean touchDown(InputEvent event,float x, float y, int pointer, int button )
	            {
	            	Gdx.app.log( RunningJoe.LOG, "High scores clicked: " + getName() );
	            	SoundManager.getInstance().play( RunningJoeSound.CLICK );
	                super.touchUp( event, x, y, pointer, button );            	
	                RunningJoe.getInstance().setScreen( new HighScoresScreen() );
	            	return true;
	            }
	        } );
	        
	        TextButton exitButton = new TextButton( 
	        		this.getLanguagesManager().getString("Exit"), 
	        		this.buttonStyle 
	        );
	        super.getTable().add(exitButton).uniform().fill().spaceBottom( 10 ).colspan(2);
	        super.getTable().row();
	        exitButton.addListener( new InputListener() {
	            @Override
	            public boolean touchDown(InputEvent event,float x, float y, int pointer, int button )
	            {
	            	Gdx.app.log( RunningJoe.LOG, "Exit clicked: " + getName() );
	            	SoundManager.getInstance().play( RunningJoeSound.CLICK );
	            	Gdx.app.exit();
	            	return true;
	            }
	        } );   
	        
	        this.menuDawn = true;
    	}
    }
}
