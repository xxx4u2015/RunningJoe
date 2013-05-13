package efficom.runningjoe.core;

import java.util.Iterator;
import java.util.LinkedList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import efficom.runningjoe.RunningJoe;
import efficom.runningjoe.services.MusicManager;
import efficom.runningjoe.services.MusicManager.RunningJoeMusic;

public class RjWorld{
	//for drawing
	Vector3 touchPoint;
	private Box2DDebugRenderer debugRenderer;
	//reset variables
	Rectangle rect_reset;
	private Body thebrick;	
	
	private RunningJoe game;
	private Score score;
	private Joe joe;
	private World world;
	private OrthographicCamera camera;
	private boolean started = false;
	private SpriteBatch spriteBatch;
	
	/**
	 * List of @linkRjBlock objects witch represents the ground  
	 */
	private LinkedList<RjBlock> groundBlocks;

	/**
	 * @param game Main Game
	 */
	public RjWorld() {
		
		// setup the camera. In Box2D we operate on a
		// meter scale, pixels won't do it. So we use
		// an orthographic camera with a viewport of
		// 48 meters in width and 32 meters in height.
		// We also position the camera so that it
		// looks at (0,16) (that's where the middle of the
		// screen will be located).
		camera = new OrthographicCamera(
				RunningJoe.PixToMeter(RunningJoe.SCREEN_WIDTH)*RunningJoe.BOX2D_WIDTH_SCALE, 
				RunningJoe.PixToMeter(RunningJoe.SCREEN_HEIGHT)*RunningJoe.BOX2D_HEIGHT_SCALE);
		camera.position.set(
				RunningJoe.PixToMeter(RunningJoe.SCREEN_WIDTH)/2*RunningJoe.BOX2D_WIDTH_SCALE, 
				RunningJoe.PixToMeter(RunningJoe.SCREEN_HEIGHT)/2*RunningJoe.BOX2D_HEIGHT_SCALE, 0);
		
		this.groundBlocks = new LinkedList<RjBlock>();
					
		touchPoint = new Vector3(0,0,0);	
		spriteBatch = new SpriteBatch();

		Vector2 gravity=new Vector2(0.0f, -9.81f); //we have to flip ALL y values- AS3 uses down int he y duirection as increasing, whereas OpenGL uses UP in the y direction as positive
		Boolean sleep = true;
		world = new World(gravity, sleep);
		
		this.joe = new Joe(this);
		
		debugRenderer = new Box2DDebugRenderer();
		
		this.score = new Score();
		
					
		//Setup();
					
		//font = new BitmapFont(Gdx.files.internal("resources/fonts/arial-15.fnt"), false);
		//font.setScale(1/RunningJoe.BOX2D_HEIGHT_SCALE);
					
		rect_reset = new Rectangle(0, RunningJoe.SCREEN_HEIGHT-30/RunningJoe.BOX2D_HEIGHT_SCALE, 100/RunningJoe.BOX2D_WIDTH_SCALE, 30/RunningJoe.BOX2D_HEIGHT_SCALE);
					
		Gdx.app.log("MyGdxGame", "GAME STARTED");
	}
	
	public boolean isStarded() {
		return this.started;
	}
	
	public Score getScore(){return this.score;}

	public Joe getJoe() {
		return this.joe;
	}
	
	public OrthographicCamera getCamera()
	{
		return this.camera;
	}

	
	public RunningJoe getGame() {
		return this.game;
	}
	
	public void Setup()
	{
		Brick(275,435,30,30,"breakable");
		Brick(365,435,30,30,"breakable");
		Brick(320,405,120,30,"breakable");
		Brick(320,375,60,30,"unbreakable");
			
//		Brick(305,345,90,30,"breakable");//for some reason LibGDX box2d doesn't like this block, and starts moving oddly, toppling the tower on its own
		Brick(305,345,90,30,"breakable");
		
		Brick(320,300,120,60,"unbreakable");
			
		//---------------------------------
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(
				RunningJoe.PixToMeter(50),
				RunningJoe.PixToMeter(RunningJoe.SCREEN_HEIGHT_ORIGINAL-50));
		bodyDef.type = BodyType.DynamicBody;
			
		
		PolygonShape polygonShape=new PolygonShape();
		polygonShape.setAsBox(RunningJoe.PixToMeter(20/2),RunningJoe.PixToMeter(20/2));
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape=polygonShape;
		fixtureDef.density=2;
		fixtureDef.restitution=0.0f;
		fixtureDef.friction=1f;
		thebrick=world.createBody(bodyDef);
		thebrick.setFixedRotation(true);
			
		//thebrick.setUserData(s);
			
		thebrick.createFixture(fixtureDef);
		
	}
		
	private void Brick(float pX, float pY, float w, float h, String s) 
	{
			
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(RunningJoe.PixToMeter(pX),RunningJoe.PixToMeter(RunningJoe.SCREEN_HEIGHT_ORIGINAL-pY));
		bodyDef.type = BodyType.DynamicBody;
			
			
		PolygonShape polygonShape=new PolygonShape();
		polygonShape.setAsBox(RunningJoe.PixToMeter(w/2),RunningJoe.PixToMeter(h/2));
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape=polygonShape;
		fixtureDef.density=2;
		fixtureDef.restitution=0.4f;
		fixtureDef.friction=0.5f;
		Body thebrick=world.createBody(bodyDef);
			
		thebrick.setUserData(s);
			
		thebrick.createFixture(fixtureDef);
	}
		
	public void render()
	{			
		Gdx.gl.glClearColor(.2f, .2f, .2f, 1); //0->1 = 0->255
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
				
		this.generateGroud();
		
		if (this.started) {
			float deltaTime= Gdx.app.getGraphics().getDeltaTime();			
			
			if(deltaTime>.1f)
				deltaTime=.1f;
			
			world.step(deltaTime, 10, 10);
			// Increase the score 
			score.addValue((int)this.joe.getSpeed() *10 * Gdx.app.getGraphics().getDeltaTime());
			
			joe.render();			
			this.camera.translate(RunningJoe.PixToMeter(this.joe.getSpeed()), 0);
		}
		
		camera.update();
		
		debugRenderer.render(world, camera.combined);
		
		// Draw bodies textures
		spriteBatch.begin();
		joe.DrawTexture(spriteBatch);
		
		Iterator<RjBlock> bi = groundBlocks.iterator();
		while(bi.hasNext()){
			RjBlock block = bi.next();
			block.DrawTexture(spriteBatch);
		}
		spriteBatch.end();
	}
	
	/**
	 * Start the game
	 */
	public void Start() {
		this.started = true;
		MusicManager.getInstance().play(RunningJoeMusic.THEME);
	}
	
	/**
	 * Pause the game
	 */
	public void Pause() {
		MusicManager.getInstance().stop();
		this.started = false;
	}
	
	/**
	 * Get the world instance
	 * @return
	 */
	public World getWorld() {
		return this.world;
	}
	
	public void RenderDebug() {
		if (RunningJoe.DEV_MODE)
			game.getDebugRenderer().render(world, camera.combined);
	}
	
	/**
	 * Method generateGround
	 * 
	 * This Method spawns small Block Objects witch are used to display the
	 * ground (Grass picture's width is 512px). 1 block = 32px*32px.
	 * 
	 * @todo
	 * - 10 first blocks (to the left) aren't fixed (so they are falling)
	 * - 22 last blocks (to the right) are, so Joe can run on them.
	 * - As soon as the last block's position is the extreme right of the screen :
	 *		- the first block of the List is destroyed
	 *		- another random block is generated and added at the end of the List
	 * 
	 */
	private void generateGroud() {		
		float camPos = this.camera.viewportWidth / 2 + this.camera.position.x;		
		float lastBlockX = this.groundBlocks.size() == 0 ? 0 : this.groundBlocks.getLast().getPosition().x;
		
		while (this.groundBlocks.size() == 0 || lastBlockX < camPos ){
			float posX = 0;
			// Find the position of the previous bloc
			if (this.groundBlocks.size() != 0) {
				RjBlock body = this.groundBlocks.get(this.groundBlocks.size() - 1);
				posX = RunningJoe.MeterToPix(body.getPosition().x)	+ RjBlock.BLOCK_WIDTH * 2;
			}
			
			RjBlock groundBody = new RjBlock(this, "Floor "+ posX);			
			groundBody.generateRandomBlock(posX);
			Gdx.app.log(RunningJoe.LOG, "Created block: " + posX);
			
			this.groundBlocks.addLast(groundBody);
			lastBlockX = this.groundBlocks.getLast().getPosition().x;

		}
		
		this.fallingGround();
		
		if (this.groundBlocks.size() > 32)
			Gdx.app.log(RunningJoe.LOG, "Too much ground blocks: " + this.groundBlocks.size());
	}
	
	public RjBlock getLastBlock(){
		try {
			return this.groundBlocks.getLast();
		}
		catch(Exception e){
			return null;
		}
	}

	/**
	 * LET FALL FIRST BLOCKS
	 * 
	 * @return void
	 */
	public void fallingGround() {
		// DELETE OUT OF RANGE BLOCKS
		if (this.groundBlocks.size() > 0) {
			Iterator<RjBlock> i = this.groundBlocks.iterator();
			while (i.hasNext()) {
				RjBlock bloc = i.next();
				if (bloc.getPosition().x < (this.camera.position.x - (this.camera.viewportWidth / 1.5))) {
					// System.out.println("Destroy");
					this.world.destroyBody(bloc.body);
					i.remove();
				} else if (bloc.getPosition().x < (this.camera.position.x - (this.camera.viewportWidth / 4))) {
					// bloc.s
					bloc.body.setType(BodyType.DynamicBody);
					// System.out.println("fall");
				}

			}
		}
	}
}