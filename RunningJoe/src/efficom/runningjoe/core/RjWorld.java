package efficom.runningjoe.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import efficom.runningjoe.RunningJoe;

public class RjWorld{
	RunningJoe game;
	Joe joe;
	World world;
	OrthographicCamera camera;	
	Vector2 gravity;
	static final float BOX_STEP=1/60f;  
	static final int BOX_VELOCITY_ITERATIONS=6;  
	static final int BOX_POSITION_ITERATIONS=2;  
	static final float WORLD_TO_BOX=0.01f;  
	static final float BOX_WORLD_TO=100f;
	
	public RjWorld(RunningJoe game){
		this.game = game;		
		
		gravity = new Vector2(0, -9.8f);
		world = new World(gravity, false);
		camera = new OrthographicCamera();  
        camera.viewportHeight = 320;  
        camera.viewportWidth = 480;  
        camera.position.set(camera.viewportWidth * .5f, camera.viewportHeight * .5f, 0f);  
        camera.update();
        
        joe = new Joe(this);
        
        this.Start();
	}
	
	public World getWorld()
	{
		return this.world;
	}
	
	public void render()
	{
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);  
		if(game.DEV_MODE){
			game.getDebugRenderer().render(world, camera.combined);
		}
		
		//joe.render();
	    world.step(BOX_STEP, BOX_VELOCITY_ITERATIONS, BOX_POSITION_ITERATIONS);
	}
	
	public void RenderDebug(){
		if(game.DEV_MODE)
			game.getDebugRenderer().render(world, camera.combined); 
		
	}
	
	public RunningJoe getGame()
	{
		return this.game;
	}
	
	public void Start()
    {
    	this.generateGroud();
    }
	
	public OrthographicCamera getCamera(){
		return this.camera;
	}
    
    private void generateGroud()
    {
    	BodyDef groundBodyDef =new BodyDef();  
        groundBodyDef.position.set(new Vector2(0, 10));  
        Body groundBody = world.createBody(groundBodyDef);  
        PolygonShape groundBox = new PolygonShape();  
        groundBox.setAsBox((camera.viewportWidth) * 2, 10.0f);  
        groundBody.createFixture(groundBox, 0.0f);
    }
	
}