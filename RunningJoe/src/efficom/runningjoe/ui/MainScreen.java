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
	private RjWorld world;
	private boolean menuDawn = false;
	Label lblScore;
	
    public MainScreen(RunningJoe game )
    {
        super( game ); 
        world = game.getWorld();  
        
        lblScore = new Label(""+(int)this.world.getScore().getValue(), 
				this.labelStyle
				);
    }
    
    public RjWorld getWorld(){
    	return this.world;
    }
    
    @Override
    public void render(float delta)
    {
    	this.world.render();
    	
    	// If the game has started handle the pressed keys
    	if(world.isStarded() && !menuDawn){
	    	if(Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.justTouched())
	        	this.world.getJoe().Jump();
	    	
	    	// Allow moving in dev mode
	    	if(RunningJoe.DEV_MODE){
		    	// Move to the left
		    	if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
		    		this.world.getJoe().Move(false, 1.0f);
		    	
		    	// Move to the right
		    	if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
		    		this.world.getJoe().Move(true, 1.0f);
		    	
		    	//if(Gdx.input.m)
	    	}
	        
	        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE) || Gdx.input.isKeyPressed(Input.Keys.MENU))
	        	this.world.Pause();
    	}
        
    	this.world.render();  
    	super.render(delta); 
    	
    	if(this.world.isStarded()){
    		this.getTable().clear();
    		this.menuDawn = false;
    		
    		try{
	    		this.lblScore.setText(
	    				this.getLanguagesManager().getString("Score") + " : " + 
	    				(int)this.getWorld().getScore().getValue()
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
        
        this.world.render();
        
        if(!this.world.isStarded()){    
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
	        
	        if(this.world.getScore().getValue() > 0){
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
		                world.Start();
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
	                world = new RjWorld();	                
	                world.Start();
	                return true;
	            }
	        });
	        
	        this.getTable().add(startGameButton).colspan(2).uniform().spaceBottom(10);
	        this.getTable().row();
	        
	        // register the button "options"
	        TextButton optionsButton = new TextButton( 
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
	                game.setScreen( new OptionsScreen( game ) );
	            	return true;
	            }
	        } );
	
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
	                game.setScreen( new HighScoresScreen( game ) );
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
