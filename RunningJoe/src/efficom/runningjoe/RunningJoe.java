package efficom.runningjoe;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.GdxNativesLoader;
import efficom.runningjoe.core.RjWorld;
import efficom.runningjoe.services.SoapManager;
import efficom.runningjoe.services.SoundManager;
import efficom.runningjoe.services.MusicManager;
import efficom.runningjoe.services.PreferencesManager;
import efficom.runningjoe.services.ProfileManager;
import efficom.runningjoe.ui.LoginScreen;
import efficom.runningjoe.ui.MainScreen;
import efficom.runningjoe.ui.OptionsScreen;
import efficom.runningjoe.ui.SplashScreen;

/**
 * The game's main class, called as application events are fired.
 */
public class RunningJoe extends Game
{
    // constant useful for logging
    public static final String LOG = RunningJoe.class.getSimpleName();
    public static final int GAME_VIEWPORT_WIDTH = 800, GAME_VIEWPORT_HEIGHT = 480;

    // whether we are in development mode
    public static final boolean DEV_MODE = true;

    // a libgdx helper class that logs the current FPS each second
    private FPSLogger fpsLogger;
    
    private RjWorld world;
    
 	// services
    private PreferencesManager preferencesManager;
    private ProfileManager profileManager;
    private MusicManager musicManager;
    private SoundManager soundManager;

    public RunningJoe()
    {
    	GdxNativesLoader.load(); 
    }
    
    // Services' getters

    public PreferencesManager getPreferencesManager()
    {
        return preferencesManager;
    }
    
    public RjWorld getWorld(){return this.world;}

    public ProfileManager getProfileManager()
    {
        return profileManager;
    }
    
    public MusicManager getMusicManager()
    {
        return musicManager;
    }

    public SoundManager getSoundManager()
    {
        return soundManager;
    }
    
    Box2DDebugRenderer debugRenderer;
    
    public Box2DDebugRenderer getDebugRenderer()
    {
    	return this.debugRenderer;
    }
    
    // Game-related methods

    @Override
    public void create()
    {
    	debugRenderer = new Box2DDebugRenderer();
        Gdx.app.log( RunningJoe.LOG, "Creating game on " + Gdx.app.getType() );

        // create the preferences manager
        preferencesManager = PreferencesManager.getInstance();

        // create the music manager
        musicManager = MusicManager.getInstance();
        musicManager.setVolume( preferencesManager.getVolume() );
        musicManager.setEnabled( preferencesManager.isMusicEnabled() );

        // create the sound manager
        soundManager = SoundManager.getInstance();
        soundManager.setVolume( preferencesManager.getVolume() );
        soundManager.setEnabled( preferencesManager.isSoundEnabled() );

        // create the profile manager
        profileManager = ProfileManager.getInstance();
        profileManager.retrieveProfile();

        // create the helper objects
        this.fpsLogger = new FPSLogger();
        
        world = new RjWorld(this);
    }

    @Override
    public void resize(int width, int height )
    {
        super.resize( width, height );
        Gdx.app.log( RunningJoe.LOG, "Resizing game to: " + width + " x " + height );

        // show the splash screen when the game is resized for the first time;
        // this approach avoids calling the screen's resize method repeatedly
        if( getScreen() == null ) {
        	setScreen( new LoginScreen( this ) );
        	//setScreen( new MainScreen( this ) );
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
        profileManager.persist();
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
        Gdx.app.log( RunningJoe.LOG, "Setting screen: " + screen.getClass().getSimpleName() );
    }

    @Override
    public void dispose()
    {
        super.dispose();
        
        Gdx.app.log( RunningJoe.LOG, "Disposing game" );

        // dipose some services
        musicManager.dispose();
        soundManager.dispose();
    }
}