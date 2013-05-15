package efficom.runningjoe.core;

import java.util.Iterator;
import java.util.Vector;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import efficom.runningjoe.RunningJoe;
import efficom.runningjoe.services.AssetsManager;
import efficom.runningjoe.services.MusicManager;
import efficom.runningjoe.services.MusicManager.RunningJoeMusic;

public class RjWorld{
	private Box2DDebugRenderer debugRenderer;
	private Score score;
	private Joe joe;
	private World world;
	private OrthographicCamera camera;
	private boolean started = false;
	private SpriteBatch spriteBatch;
	private RayHandler rayHandler;
	private PointLight ambiance;
    private RjBlockList listBlock;

	private Vector<Background> vecBg;
    private Background foreground;

	public RjWorld(){
		camera = new OrthographicCamera(
				RunningJoe.PixToMeter(RunningJoe.SCREEN_WIDTH)*RunningJoe.BOX2D_WIDTH_SCALE, 
				RunningJoe.PixToMeter(RunningJoe.SCREEN_HEIGHT)*RunningJoe.BOX2D_HEIGHT_SCALE);
		camera.position.set(
				RunningJoe.PixToMeter(RunningJoe.SCREEN_WIDTH)/2*RunningJoe.BOX2D_WIDTH_SCALE, 
				RunningJoe.PixToMeter(RunningJoe.SCREEN_HEIGHT)/2*RunningJoe.BOX2D_HEIGHT_SCALE, 0);

		spriteBatch = new SpriteBatch();
		Vector2 gravity=new Vector2(0.0f, -9.81f); //we have to flip ALL y values- AS3 uses down int he y duirection as increasing, whereas OpenGL uses UP in the y direction as positive
		Boolean sleep = true;
		world = new World(gravity, sleep);

        this.listBlock = new RjBlockList(this);
		
		rayHandler = new RayHandler(world);
		rayHandler.setCombinedMatrix(camera.combined);		
		ambiance = new PointLight(rayHandler, 5, new Color(0.5f,0.5f,0.5f,0.8f), 100, 0, 0);

        TextureRegion region = new TextureRegion( AssetsManager.getInstance().getBackground(0));
        // FirstBackground
        this.foreground = new Background(this, region, 1.5f);

		this.joe = new Joe(this);		
		debugRenderer = new Box2DDebugRenderer();		
		this.score = new Score();		
		this.createBackground();
		
		Gdx.app.log("Running Joe", "GAME STARTED");
	}
	
	public RayHandler getRayHandler()
	{
		return this.rayHandler;		
	}
	
	/**
	 * Create the backgrounds vector
	 */
	public void createBackground()
	{
		this.vecBg = new Vector<Background>();
		
		TextureRegion region = new TextureRegion( AssetsManager.getInstance().getBackground(1));
		TextureRegion region2 = new TextureRegion( AssetsManager.getInstance().getBackground(2));
		// FirstBackground
		this.vecBg.add( new Background(this, region2, 0.8f));
		this.vecBg.add( new Background(this, region, 1f)	);
		
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
		
	public void render()
	{			
		Gdx.gl.glClearColor(.2f, .2f, .2f, 1); //0->1 = 0->255
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		rayHandler.updateAndRender();
		
		
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
		
		if(RunningJoe.DEV_MODE)
			debugRenderer.render(world, camera.combined);
		
		// Draw bodies textures
		spriteBatch.begin();
		Iterator<Background> bgi = this.vecBg.iterator();
		while(bgi.hasNext()){
			Background bg = bgi.next();
			bg.DrawTexture(spriteBatch);
		}
		
		joe.draw(spriteBatch);
		
        this.listBlock.render();
        this.listBlock.draw(spriteBatch);

        foreground.DrawTexture(spriteBatch);
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
			RunningJoe.debugRenderer.render(world, camera.combined);
	}
	
	protected void finalize() throws Throwable
	{
		this.rayHandler.dispose(); //do finalization here
		super.finalize(); //not necessary if extending Object.
	} 
	
	/*
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
	
	*/
}