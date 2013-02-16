package efficom.runningjoe.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import efficom.runningjoe.RunningJoe;

public class RjWorld{
	RunningJoe game;
	Joe joe;
	World world;
	OrthographicCamera camera;	
	Vector2 gravity;
	boolean started= false;
	static final float BOX_STEP=1/60f;  
	static final int BOX_VELOCITY_ITERATIONS=6;  
	static final int BOX_POSITION_ITERATIONS=2;  
	static final float WORLD_TO_BOX=0.01f;  
	static final float BOX_WORLD_TO=100f;
	
	SpriteBatch spriteBatch;	
	Texture grass;
	
	public RjWorld(RunningJoe game){
		this.game = game;	
		
		spriteBatch = new SpriteBatch();
		grass = new Texture(Gdx.files.internal("images/grass.png"));
		//grass
		
		gravity = new Vector2(0, -19.8f);
		world = new World(gravity, false);
		camera = new OrthographicCamera();  
        camera.viewportHeight = 320;  
        camera.viewportWidth = 480;  
        camera.position.set(camera.viewportWidth * .5f, camera.viewportHeight * .5f, 0f);  
        camera.update();
        
        joe = new Joe(this);
        
        this.generateGroud();
	}
	
	public boolean isStarded(){return this.started;}	
	public Joe getJoe(){return this.joe;}	
	public World getWorld(){return this.world;}
	public RunningJoe getGame(){return this.game;}	
	public OrthographicCamera getCamera(){return this.camera;}
	
	public void render()
	{
		Gdx.gl.glClearColor( 0.4f, 0.4f, 0.4f, 0f );
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT); 
		
		 spriteBatch.begin();
	     spriteBatch.draw(grass, 0, 0);
	     spriteBatch.end();
	        
		if(game.DEV_MODE){
			game.getDebugRenderer().render(world, camera.combined);
		}
		
		joe.render();
	    world.step(BOX_STEP, BOX_VELOCITY_ITERATIONS, BOX_POSITION_ITERATIONS);
	}
	
	public void RenderDebug(){
		if(game.DEV_MODE)
			game.getDebugRenderer().render(world, camera.combined); 
		
	}	
    
    private void generateGroud()
    {
    	// Create the floor
    	BodyDef groundBodyDef =new BodyDef();  
        groundBodyDef.position.set(new Vector2(0, 10));  
        Body groundBody = world.createBody(groundBodyDef);  
        PolygonShape groundBox = new PolygonShape();  
        groundBox.setAsBox((camera.viewportWidth) * 2, 15.0f);  
        groundBody.createFixture(groundBox, 0.0f);
        
        // Create the roof
        BodyDef roofBodyDef =new BodyDef();  
        roofBodyDef.position.set(new Vector2(0, (camera.viewportHeight)-10));
        Body roofBody = world.createBody(roofBodyDef);  
        PolygonShape roofBox = new PolygonShape();  
        roofBox.setAsBox((camera.viewportWidth) * 2, 10.0f);  
        roofBody.createFixture(roofBox, 0.0f);
    }
    
    public void Start()
    {
    	this.started = true;
    }
	
}