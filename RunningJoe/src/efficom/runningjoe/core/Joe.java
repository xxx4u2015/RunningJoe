package efficom.runningjoe.core;

import box2dLight.PointLight;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import efficom.runningjoe.RunningJoe;
import com.badlogic.gdx.graphics.g2d.Animation;

/***
 * Joe the main character of RunningJoe
 * 
 * @author Guillaume BAILLEUL
 */
public class Joe extends AbstractGraphicItem {
	static final char JUMP_COUNT = 2;
	private enum MoveState{JUMPING, RUNNING};
	static final float DENSITY = 0.5f;
	static final float FRICTION = 0.5f;
	static final float RESTITUTION = 0.0f;
	static final float JUMP_FORCE = 3.0f;
	static final float INITIAL_SPEED = 1;
    static final int FRAME_WIDTH = 64;
    static final int FRAME_HEIGHT = 128;
    private static final float BODY_WIDTH = 35;
    private static final int FRAME_COLS = 8;         // #1
    private static final int FRAME_ROWS = 2;

    // Class attributes
	private float speed = INITIAL_SPEED;
	private MoveState moveState = MoveState.RUNNING;
	private char jumpCount = JUMP_COUNT;
	private Vector2 initPos;
	private PointLight joelight;
    private TextureRegion[] animationFrames;
    private Animation animation;
    float stateTime;

    /***
	 * Class constructor
	 * @param world
	 */
	public Joe(RjWorld world){		
		super(world, "Joe", GraphicItemType.JOE);
		
		this.sprite = new Sprite(new Texture(
				Gdx.files.internal("images/standingjoe.png")));
		
		initPos = new Vector2(
	    		RunningJoe.SCREEN_WIDTH* 0.75f,
	    		RjBlock.BLOCK_HEIGHT*10);

		//this.createStandingJoe();
        this.createBody();
        this.createRunningJoe();
	}
	
	/**
	 * Create the standing joe representation
	 */
	private void createStandingJoe() {
	    
	    // Create the Texture
	    TextureRegion region = new TextureRegion(new Texture(Gdx.files.internal("images/standingjoe.png")));
	    LoadTexture(region, new Vector2(0,0));
	}

    private void createBody()
    {
        // Create the body and fixture
        CreateBody(initPos,0,BodyType.DynamicBody, true);
        LoadFixture("data/joe.json", "StandingJoe",DENSITY, FRICTION, RESTITUTION,BODY_WIDTH);
    }

	/**
	 * Create the standing joe representation
	 */
	private void createRunningJoe()
	{
        // Create the Texture
        this.animationFrames = createFrames(
                new Texture(Gdx.files.internal("images/runningjoe.png")),
                FRAME_ROWS,
                FRAME_COLS,
                FRAME_WIDTH,
                FRAME_HEIGHT);

        LoadTexture(this.animationFrames[0], new Vector2(0,0));
	}

    /**
     * Load the textures into an array and create a animation object
     * @param texture   The texture to load
     * @param rows  The number of rows in the texture file
     * @param cols  The number of columns in the texture file
     * @param width The width of a frame in pixels
     * @param height The height of a frame in pixels
     * @return The array of frames
     */
    private TextureRegion[] createFrames(Texture texture, int rows, int cols, int width, int height)
    {
        TextureRegion[][] regions = TextureRegion.split(texture, width, height);
        TextureRegion[] frames = new TextureRegion[cols * rows];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                frames[index++] = regions[i][j];
            }
        }

        animation = new Animation(0.1f, frames);
        stateTime = 0.0f;

        return frames;
    }
	
	/*
	 * 
	 */
	@SuppressWarnings("unused")
	private void createJumpingJoe()
	{
        // Create the Texture
        this.animationFrames = createFrames(
                new Texture(Gdx.files.internal("images/jumpingjoe.png")),
                FRAME_ROWS,
                FRAME_COLS,
                FRAME_WIDTH,
                FRAME_HEIGHT);

        LoadTexture(this.animationFrames[0], new Vector2(0,0));

    }
	
	/*
	 * 
	 */
	public void Jump()
    {
		
		Vector2 pos = this.body.getPosition();
    	Vector2 vel = new Vector2(0, JUMP_FORCE);        
        
        if(this.moveState != MoveState.JUMPING) {
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
			this.moveState = this.hasGroundContact() ? MoveState.RUNNING : MoveState.JUMPING;

            stateTime += Gdx.graphics.getDeltaTime();
            TextureRegion region = animation.getKeyFrame(stateTime, true);
            tr.SetTextureRegion(region);

			this.speed = this.speed + Gdx.app.getGraphics().getDeltaTime() * 0.01f;
			
			if( oldState != this.moveState){
				// if came back to the ground
				if(this.moveState == MoveState.RUNNING){
					//for(Fixture fix : body.getFixtureList())
		        	//	fix.setFriction(1.0f);

                    this.createRunningJoe();
					
					// Reset the number of jump
					this.jumpCount = JUMP_COUNT;
				// if start jumping
				}else{
                    createJumpingJoe();
					//for(Fixture fix : body.getFixtureList())
		        	//	fix.setFriction(0.0f);
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
