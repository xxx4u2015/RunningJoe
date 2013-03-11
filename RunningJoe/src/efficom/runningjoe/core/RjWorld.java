package efficom.runningjoe.core;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;

import efficom.runningjoe.RunningJoe;
import efficom.runningjoe.services.MusicManager.RunningJoeMusic;

public class RjWorld{
	RunningJoe game;
	Joe joe;
	World world;
	OrthographicCamera camera;	
	Vector2 gravity;
	boolean started= false;
	static final float BOX_STEP = 1/20f;  
	static final int BOX_VELOCITY_ITERATIONS = 6;  
	static final int BOX_POSITION_ITERATIONS = 2;  
	static final float WORLD_TO_BOX= 0.01f;  
	static final float BOX_WORLD_TO=100f;
	SpriteBatch spriteBatch;
	private LinkedList<Body> groundBlocs;
	Texture grassTex;
	
	public RjWorld(RunningJoe game){
		this.game = game;	
		
		grassTex = new Texture(Gdx.files.internal("images/grass.png"));
		
		spriteBatch = new SpriteBatch();
		this.groundBlocs = new LinkedList<Body>();
		//grass
		
		gravity = new Vector2(0, -9.8f);
		world = new World(gravity, false);
		camera = new OrthographicCamera(game.GAME_VIEWPORT_WIDTH, game.GAME_VIEWPORT_HEIGHT);
        camera.position.set(camera.viewportWidth * .5f, camera.viewportHeight * .5f, 0f);  
        camera.update();
        
        joe = new Joe(this, new Texture(Gdx.files.internal("images/standingjoe.png")));
        spriteBatch = new SpriteBatch();        
        //this.generateGroud();
	}
	
	public boolean isStarded(){return this.started;}	
	public Joe getJoe(){return this.joe;}	
	public World getWorld(){return this.world;}
	public RunningJoe getGame(){return this.game;}	
	public OrthographicCamera getCamera(){return this.camera;}
	
	public void SetGravity(Vector2 vect){
		this.world.setGravity(vect);
	}
	
	public void render()
	{
		this.generateGroud();
		Gdx.gl.glClearColor( 0.4f, 0.4f, 0.4f, 0f );
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT); 
		
		if(game.DEV_MODE){
			game.getDebugRenderer().render(world, camera.combined);
		}
		
	    if(this.started){
	    	joe.render();
		    world.step(BOX_STEP, BOX_VELOCITY_ITERATIONS, BOX_POSITION_ITERATIONS);
	    	this.camera.translate(this.joe.getSpeed() * BOX_STEP , 0);
		    this.camera.update();
	    }
	    
	    Iterator<Body> bi = world.getBodies();
        
	    while (bi.hasNext()){
	        Body b = bi.next();

	        // Get the bodies user data - in this example, our user 
	        // data is an instance of the Entity class
	        GraphicItemInfos infos = (GraphicItemInfos)b.getUserData();

	        if (infos != null && infos.getTexture() != null) {
	        	Vector2 camOrigin = new Vector2(
	        			this.camera.position.x - this.camera.viewportWidth/2,
	        			this.camera.position.y - this.camera.viewportHeight/2);
	        	
	        	infos.getSprite().setPosition(b.getPosition().x - camOrigin.x, b.getPosition().y - camOrigin.y);
	        	infos.getSprite().setRotation(MathUtils.radiansToDegrees*b.getAngle());
	        	
	        	spriteBatch.begin();   	        	
	        	infos.getSprite().draw(spriteBatch);          
	            spriteBatch.end();
	        	
	        }
	    }
	}
	
	public void RenderDebug(){
		if(game.DEV_MODE)
			game.getDebugRenderer().render(world, camera.combined);
	}	
    
	/**
	 * Method generateGround
	 * 
	 * This Method spawns small Block Objects witch are used to display the ground (Grass picture's width is 512px).
	 * 1 block = 32px.
	 * 
	 * TODO :
	 * - 32 Blocks (in a List) are displayed at a time (32 blocks = 1024px).
	 * - 10 first blocks (to the left) aren't fixed (so they are falling)
	 * - 22 last blocks (to the right) are, so Joe can run on them.
	 * - As soon as the last block's position is the extreme right of the screen :
	 *		- the first block of the List is destroyed
	 *		- another random block is generated and added at the end of the List
	 * 
	 */
    private void generateGroud()
    {   	            
        while(groundBlocs.size() == 0 ||
        	  groundBlocs.getLast().getPosition().x < this.camera.position.x)
        {
        	
        	float posX = 0;
        	//Find the position of the previous bloc
        	if(groundBlocs.size() != 0){
        		Body body = groundBlocs.get(groundBlocs.size()-1);
        		posX = body.getPosition().x + grassTex.getTextureData().getWidth();
        	}        	
        	BodyDef groundBodyDef =new BodyDef();  
            groundBodyDef.position.set(new Vector2(posX, 0));  
            Body groundBody = world.createBody(groundBodyDef);  
            PolygonShape groundBox = new PolygonShape();  
            groundBox.setAsBox(grassTex.getTextureData().getWidth(), 20.0f);  
            groundBody.createFixture(groundBox, 0.0f);
            GraphicItemInfos infosFloor = new GraphicItemInfos("Floor"+groundBlocs.size(), new Sprite(grassTex));
            groundBody.setUserData(infosFloor); 
            this.groundBlocs.addLast(groundBody);
        }
        
        while(groundBlocs.size()>0 && 
        		groundBlocs.getFirst().getPosition().x + grassTex.getTextureData().getWidth() < this.camera.position.x - this.camera.viewportWidth/2)
        {
        	Body body = groundBlocs.getFirst();
        	body.setUserData(null);
        	body = null;
        	groundBlocs.removeFirst();        	        	
        }
        
        if(groundBlocs.size() >= 32)
        {
        	Gdx.app.log( RunningJoe.LOG, "Too much ground blocs: " + groundBlocs.size() );        	
        }

        
        // Create the roof
        /*BodyDef roofBodyDef =new BodyDef();  
        roofBodyDef.position.set(new Vector2(0, (camera.viewportHeight)-10));
        Body roofBody = world.createBody(roofBodyDef);  
        PolygonShape roofBox = new PolygonShape();  
        roofBox.setAsBox(grassTex.getWidth(), 10.0f);  
        roofBody.createFixture(roofBox, 0.0f);
        GraphicItemInfos infosRoof = new GraphicItemInfos("Roof", new Sprite(grassTex));
        roofBody.setUserData(infosRoof);*/
    }
    
    public void Start()
    {
    	this.started = true;
    	game.getMusicManager().play( RunningJoeMusic.THEME );
    }
    
    public void Pause(){
    	game.getMusicManager().stop();
    	this.started = false;
    }
	
}