package efficom.runningjoe.core;

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
import com.badlogic.gdx.physics.box2d.World;
import efficom.runningjoe.RunningJoe;
import efficom.runningjoe.services.AssetsManager;
import efficom.runningjoe.services.MusicManager;
import efficom.runningjoe.services.MusicManager.RunningJoeMusic;
import efficom.runningjoe.ui.GameOverScreen;

/**
 * The RunningJoe World
 *
 * @author Guillaume BAIILEUL
 */
public class RjWorld{
	private Score score;
	private Joe joe;
	private World world;
	private OrthographicCamera camera;
	private boolean started = false;
	private SpriteBatch spriteBatch;
	private RayHandler rayHandler;
    private RjBlockList listBlock;
    private RjObstacleFactory obstacleFactory;
	private Vector<Background> vecBg;
    private Background foreground;
    private Fire fire;

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

        this.obstacleFactory = new RjObstacleFactory();
		
		rayHandler = new RayHandler(world);
		rayHandler.setCombinedMatrix(camera.combined);		
		new PointLight(rayHandler, 5, new Color(0.5f,0.5f,0.5f,0.8f), 100, 0, 0);

        TextureRegion region = new TextureRegion( AssetsManager.getInstance().getBackground(0));
        // FirstBackground
        this.foreground = new Background(this, region, 1.5f);

		this.joe = new Joe(this);
		this.score = new Score();		
		this.createBackground();

        this.fire = new Fire(this);
		
		Gdx.app.log("Running Joe", "GAME STARTED");
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

            if(joe.isOutOfScreen()){
                this.Pause();
                RunningJoe.getInstance().setScreen( new GameOverScreen( ) );
            }

			this.camera.translate(RunningJoe.PixToMeter(this.joe.getSpeed()), 0);
		}
		
		camera.update();
		
		if(RunningJoe.DEV_MODE)
			RunningJoe.debugRenderer.render(world, camera.combined);
		
		// Draw bodies textures
		spriteBatch.begin();
        for (Background bg : this.vecBg) {
            bg.DrawTexture(spriteBatch);
        }

        // Draw Joe in the World
		joe.draw(spriteBatch);

        // Generate ground and draw it in the World
        this.listBlock.render();
        this.listBlock.draw(spriteBatch);

        this.obstacleFactory.generateRjObstacles(this,spriteBatch);

        fire.draw(spriteBatch);

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
	 * @return  The Box2D world
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
}