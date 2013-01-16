package efficom.runningjoe.services;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Disposable;
import efficom.runningjoe.RunningJoe;

/**
 * A service that manages the sound effects.
 */
public class SoundManager implements  Disposable
{    
	/**
	 * The available sound files.
	 */
	public enum RunningJoeSound
	{
	    CLICK( "sound/click.wav" );

	    private final String fileName;

	    private RunningJoeSound( String fileName )
	    {
	        this.fileName = fileName;
	    }

	    public String getFileName()
	    {
	        return fileName;
	    }
	}

    /**
     * The volume to be set on the sound.
     */
    private float volume = 1f;

    /**
     * Whether the sound is enabled.
     */
    private boolean enabled = true;

    

    /**
     * Creates the sound manager.
     */
    public SoundManager()
    {
    }

    /**
     * Plays the specified sound.
     */
    public void play(RunningJoeSound sound )
    {
        // check if the sound is enabled
        if( ! enabled ) return;

       

        // play the sound
        Gdx.app.log( RunningJoe.LOG, "Playing sound: " + sound.name() );
    }

    /**
     * Sets the sound volume which must be inside the range [0,1].
     */
    public void setVolume(
        float volume )
    {
        Gdx.app.log( RunningJoe.LOG, "Adjusting sound volume to: " + volume );

        // check and set the new volume
        if( volume < 0 || volume > 1f ) {
            throw new IllegalArgumentException( "The volume must be inside the range: [0,1]" );
        }
        this.volume = volume;
    }

    /**
     * Enables or disabled the sound.
     */
    public void setEnabled(
        boolean enabled )
    {
        this.enabled = enabled;
    }   

    /**
     * Disposes the sound manager.
     */
    public void dispose()
    {
        Gdx.app.log( RunningJoe.LOG, "Disposing sound manager" );
    }
}