package efficom.runningjoe.services;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Disposable;
import efficom.runningjoe.RunningJoe;
import efficom.runningjoe.services.LRUCache;
import efficom.runningjoe.services.LRUCache.CacheEntryRemovedListener;
import efficom.runningjoe.services.RunningJoeSound;

/**
 * A service that manages the sound effects.
 */
public class SoundManager implements CacheEntryRemovedListener<RunningJoeSound,Sound>, Disposable
{   
	private static volatile SoundManager instance = null;
    /**
     * The volume to be set on the sound.
     */
    private float volume = 1f;

    /**
     * Whether the sound is enabled.
     */
    private boolean enabled = true;
    
    /**
     * The sound cache.
     */
    private final LRUCache<RunningJoeSound, Sound> soundCache;    

    /**
     * Creates the sound manager.
     */
    private SoundManager()
    {
    	soundCache = new LRUCache<RunningJoeSound, Sound>( 10 );
        soundCache.setEntryRemovedListener( this );
    }
    
    public final static SoundManager getInstance() {
    	if (instance == null) {
            synchronized(SoundManager.class) {
              if (instance == null) {
                instance = new SoundManager();
              }
            }
         }                
        return instance;
    }

    /**
     * Plays the specified sound.
     */
    public void play(RunningJoeSound sound )
    {
        // check if the sound is enabled
        if( ! enabled ) return;
        
        // try and get the sound from the cache
        Sound soundToPlay = soundCache.get( sound );
        if( soundToPlay == null ) {
            FileHandle soundFile = Gdx.files.internal( sound.getFileName() );
            soundToPlay = Gdx.audio.newSound( soundFile );
            soundCache.add( sound, soundToPlay );
        }
        
        // play the sound
        Gdx.app.log( RunningJoe.LOG, "Playing sound: " + sound.name() );
        soundToPlay.play(volume);
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
    
    // EntryRemovedListener implementation
    @Override
    public void notifyEntryRemoved( RunningJoeSound key,Sound value )
    {
        Gdx.app.log( RunningJoe.LOG, "Disposing sound: " + key.name() );
        value.dispose();
    }


    /**
     * Disposes the sound manager.
     */
    public void dispose()
    {
        Gdx.app.log( RunningJoe.LOG, "Disposing sound manager" );
        for( Sound sound : soundCache.retrieveAll() ) {
            sound.stop();
            sound.dispose();
        }
    }
}