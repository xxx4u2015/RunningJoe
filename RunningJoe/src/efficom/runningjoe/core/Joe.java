package efficom.runningjoe.core;

import box2dLight.ConeLight;
import box2dLight.PointLight;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import efficom.runningjoe.RunningJoe;

/***
 * Joe the main character of RunningJoe
 * 
 * @author Guillaume BAILLEUL
 */
public class Joe extends AbstractGraphicItem {
	static final char JUMP_COUNT = 2;
	private enum MoveState{JUMPING, RUNNING};
	static final float DENSITY = 0.3f;
	static final float FRICTION = 0.3f;
	static final float RESTITUTION = 0.1f;
	static final float JUMP_FORCE = 10.0f;
	static final float INITIAL_SPEED = 1;
	
	// Class attributes
	private static final float BODY_WIDTH = 60;
	private float speed = INITIAL_SPEED;
	private MoveState moveState = MoveState.RUNNING;
	private char jumpCount = JUMP_COUNT;
	private Vector2 initPos;
	private PointLight joelight;
	
	/***
	 * Class constructor
	 * @param world
	 */
	public Joe(RjWorld world){		
		super(world, "Joe");
		
		this.sprite = new Sprite(new Texture(
				Gdx.files.internal("images/standingjoe.png")));
		
		initPos = new Vector2(
	    		RunningJoe.SCREEN_WIDTH* 0.75f,
	    		RjBlock.BLOCK_HEIGHT*10);
		
		//joelight = new PointLight(world.getRayHandler(), 5, new Color(0.5f,0.5f,0.5f,0.8f), 1, 0, 0);
		//joelight.attachToBody(this.body, 0.0f, 0.0f);
		
		this.createStandingJoe();
	}
	
	/**
	 * Create the standing joe representation
	 */
	private void createStandingJoe() {
	    // Create the body and fixture
	    CreateBody(initPos,0,BodyType.DynamicBody, true);	      
	    LoadFixture("data/joe.json","StandingJoe", DENSITY, FRICTION, RESTITUTION,BODY_WIDTH);
	    
	    // Create the Texture
	    TextureRegion region = new TextureRegion(new Texture(Gdx.files.internal("images/standingjoe.png")));     
	    LoadTexture(region, new Vector2(0,0));
	    
	    infos = new GraphicItemInfos("Joe");
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
    	Vector2 vel = new Vector2(0, JUMP_FORCE);        
        
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
			
			this.speed = this.speed + Gdx.app.getGraphics().getDeltaTime() * 0.01f;
			
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
	
	/**
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
	
	/***
	 * 
	 * @return the speed of joe
	 */
	public float getSpeed()
	{
		return this.speed;
	}
}
