package efficom.runningjoe;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.GdxNativesLoader;
import efficom.runningjoe.core.RjWorld;
import efficom.runningjoe.services.AssetsManager;
import efficom.runningjoe.services.SoundManager;
import efficom.runningjoe.services.MusicManager;
import efficom.runningjoe.services.ProfileManager;
import efficom.runningjoe.ui.SplashScreen;
import efficom.runningjoe.ui.LoginScreen;

/**
 * The game's main class, called as application events are fired.
 */
public class RunningJoe extends Game
{
    // constant useful for logging
    //public static final String LOG = RunningJoe.class.getSimpleName();
    public static final String LOG = "RunningJoe";
    public static float SCREEN_WIDTH_ORIGINAL=800;
	public static float SCREEN_HEIGHT_ORIGINAL=480;		
	public static float SCREEN_WIDTH=800;
	public static float SCREEN_HEIGHT=480;
	public static float BOX2D_WIDTH_SCALE=1;
	public static float BOX2D_HEIGHT_SCALE=1;
	public static float PIXELS_PER_METER = 30.0f;
    public static RunningJoe instance;

    // whether we are in development mode
    public static final boolean DEV_MODE = false;
    public static Box2DDebugRenderer debugRenderer;

    // a libgdx helper class that logs the current FPS each second
    private FPSLogger fpsLogger;
    private RjWorld world;

 	
    public RunningJoe(){
    	GdxNativesLoader.load();
        instance = this;
    }

    public static RunningJoe getInstance(){return instance;}
    
    public RjWorld getWorld(){return this.world;}
    
    // Game-related methods

    @Override
    public void create()
    {
    	SCREEN_WIDTH= Gdx.graphics.getWidth();
		SCREEN_HEIGHT= Gdx.graphics.getHeight();
		
		//NOTE: with different screen sizes (on android for instance) the world will be setup as different sizes, so the mass of objects won't be the same.
		//you need to create the world the same size, and scale the camera, so that it behaves the same regardless of screen resolution
		BOX2D_WIDTH_SCALE=SCREEN_WIDTH_ORIGINAL/SCREEN_WIDTH;
		BOX2D_HEIGHT_SCALE=SCREEN_HEIGHT_ORIGINAL/SCREEN_HEIGHT;
		
    	debugRenderer = new Box2DDebugRenderer();
        Gdx.app.log( RunningJoe.LOG, "Creating game on " + Gdx.app.getType() );
        
        // create the helper objects
        this.fpsLogger = new FPSLogger();
        
        world = new RjWorld();
    }
    
    /**
     * Convert Pixel to meter
     * @param pixels
     * @return
     */
    public static float PixToMeter(float pixels)
	{
		return pixels / RunningJoe.PIXELS_PER_METER;
	}
		
    /**
     * Convert Meters to pixels
     * @param meters The meters to convert in px
     * @return
     */
	public static float MeterToPix(float meters)
	{
		return meters * RunningJoe.PIXELS_PER_METER;
	}

    @Override
    public void resize(int width, int height )
    {
        super.resize( width, height );
        Gdx.app.log( RunningJoe.LOG, "Resizing game to: " + width + " x " + height );

        // show the splash screen when the game is resized for the first time;
        // this approach avoids calling the screen's resize method repeatedly
        if( getScreen() == null ) {
        	if(RunningJoe.DEV_MODE)
        		setScreen( new LoginScreen() );
        	else
        		setScreen( new SplashScreen() );
        }
    }

    @Override
    public void render()
    {
        super.render();
        // output the current FPS
        if( DEV_MODE ) this.fpsLogger.log();
        
        //world.render();
    }

    @Override
    public void pause()
    {
        super.pause();
        Gdx.app.log( RunningJoe.LOG, "Pausing game" );

        // persist the profile, because we don't know if the player will come
        // back to the game
        ProfileManager.getInstance().persist();
    }

    @Override
    public void resume()
    {
        super.resume();
        Gdx.app.log( RunningJoe.LOG, "Resuming game" );
    }

    @Override
    public void setScreen(Screen screen )
    {
        super.setScreen( screen );
        Gdx.app.log( RunningJoe.LOG, "Setting screen: ");
    }

    @Override
    public void dispose()
    {
        super.dispose();
        
        Gdx.app.log( RunningJoe.LOG, "Disposing game" );

        // dipose some services
        MusicManager.getInstance().dispose();
        SoundManager.getInstance().dispose();
        AssetsManager.getInstance().dispose();
    }
    
    /**
     * Recreate the world
     */
    public void restart()
    {
    	this.world = new RjWorld();
    }

}