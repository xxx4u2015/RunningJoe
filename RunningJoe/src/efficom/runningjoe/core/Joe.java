package efficom.runningjoe.core;

import aurelienribon.bodyeditor.BodyEditorLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import efficom.runningjoe.RunningJoe;

public class Joe extends AbstractGraphicItem {
	private enum MoveState{JUMPING, RUNNING};
	
	static final char JUMP_COUNT = 2;
	
	
	private static final float BODY_WIDTH = 40;
	private int speed = 1;
	private MoveState moveState = MoveState.RUNNING;
	private char jumpCount = JUMP_COUNT;
	
	public Joe(RjWorld world){		
		super(world, "Joe");
		this.sprite = new Sprite(new Texture(
				Gdx.files.internal("images/standingjoe.png")));
		new Texture(Gdx.files.internal("images/standingjoe.png"));
		this.createStandingJoe();
	}
	
	/*
	 * Create the standing joe representation
	 */
	private void createStandingJoe() {
		infos = new GraphicItemInfos("Joe",this.sprite);
		
		// Create a loader for the file saved from the editor.
		FileHandle fh = Gdx.files.internal("data/joe.json");
	    BodyEditorLoader loader = new BodyEditorLoader(fh);
	 
	    // Create the body
	    BodyDef bd = new BodyDef();	    
	    bd.position.set(
	    		RunningJoe.PixToMeter(RunningJoe.GAME_VIEWPORT_WIDTH* 0.75f),
	    		RunningJoe.PixToMeter(RjBlock.BLOCK_HEIGHT*10));
	    bd.type = BodyType.DynamicBody;
	    bd.fixedRotation = true;
	    	    	 
	    // Create a FixtureDef
	    FixtureDef fd = new FixtureDef();
	    fd.density = 0.3f;
	    fd.friction = 0.5f;
	    fd.restitution = 0.1f;
	    
	    // Add the body to the world
	    this.body = this.world.getWorld().createBody(bd);
	 
	    // Create the body fixture automatically by using the loader.
	    loader.attachFixture(body, "StandingJoe", fd, RunningJoe.PixToMeter(BODY_WIDTH));	    
	    	    
	    this.body.setUserData(infos);
	    
	}	
	
	/*
	 * 
	 */
	@SuppressWarnings("unused")
	private void createRunningJoe()
	{
	}
	
	/*
	 * 
	 */
	@SuppressWarnings("unused")
	private void createJumpingJoe()
	{
		
	}
	
	/*
	 * 
	 */
	public void Jump()
    {
		
		Vector2 pos = this.body.getPosition();
    	Vector2 vel = new Vector2(0, 1);        
        
        if(this.jumpCount > 0) {
        	this.jumpCount--;
            System.out.println("jump before: " + body.getLinearVelocity());
    		body.applyLinearImpulse(vel, pos);			
    		System.out.println("jump, " + body.getLinearVelocity());        	
        }
        
    }
	
	/**
	 * Move	from left or right
	 * @param True to run in this way -> False if this way <-
	 * @param coef
	 */
	public void Move(boolean toright, float coef)
	{		
		if(moveState != MoveState.JUMPING){
			Vector2 vel = this.body.getLinearVelocity();
	        vel.x = toright ? speed * coef: -speed * coef;
	        body.setLinearVelocity(vel);	
		}
	}
	
	/*
	 * 	
	 */
	public void render()
	{
		if(this.world.isStarded()){
			/* Define the movement state and compare it to the previous, if 
			 * they are differents change frixion fixture
			 */		
			MoveState oldState = this.moveState;
			this.moveState = this.hasContact() ? MoveState.RUNNING : MoveState.JUMPING;
			
			if( oldState != this.moveState){
				// if came back to the ground
				if(this.moveState == MoveState.RUNNING){
					for(Fixture fix : body.getFixtureList())
		        		fix.setFriction(1.0f);				
					
					// Reset the number of jump
					this.jumpCount = JUMP_COUNT;
				// if start jumping
				}else{
					for(Fixture fix : body.getFixtureList())
		        		fix.setFriction(0.0f);
				}
				
				Gdx.app.log(RunningJoe.LOG, "Move has change from " + oldState + " to " + this.moveState);
			}
			
			// Do the run
			this.run();
		}
	}
	
	/*
	 * Make running 
	 */
	private void run()
	{
		if(moveState == MoveState.RUNNING){
			Vector2 vel = this.body.getLinearVelocity();
	        vel.x = speed;//upwards - don't change x velocity
	        body.setLinearVelocity(vel); 
		}
	}
	
	public int getSpeed()
	{
		return this.speed;
	}
}
