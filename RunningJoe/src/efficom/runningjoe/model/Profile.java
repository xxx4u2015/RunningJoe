package efficom.runningjoe.model;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.OrderedMap;
import efficom.runningjoe.services.ProfileManager;

/**
 * The player's profile.
 * <p>
 * This class is used to store the game progress, and is persisted to the file
 * system when the game exists.
 * 
 * @see ProfileManager
 */
public class Profile implements Serializable
{   
	private boolean isSoundEnabled, isMusicEnabled;
	private float soundVolume, musicVolume;
	
    public Profile()
    {
    	
    }
    
    public boolean isSoundEnabled() {
		return isSoundEnabled;
	}

	public void setSoundEnabled(boolean isSoundEnabled) {
		this.isSoundEnabled = isSoundEnabled;
	}

	public boolean isMusicEnabled() {
		return isMusicEnabled;
	}

	public void setMusicEnabled(boolean isMusicEnabled) {
		this.isMusicEnabled = isMusicEnabled;
	}
	
	public float getSoundVolume() {
		return soundVolume;
	}
	
	public void setSoundVolume(float soundVolume) {
		this.soundVolume = soundVolume;
	}
	
	public float getMusicVolume() {
		return musicVolume;
	}

	public void setMusicVolume(float musicVolume) {
		this.musicVolume = musicVolume;
	}    
    
    @Override
    public void read(Json json, OrderedMap<String,Object> jsonData )
    {
        // read the some basic properties
    	isSoundEnabled = json.readValue( "isSoundEnabled", Boolean.class, jsonData );
    	isMusicEnabled = json.readValue( "isMusicEnabled", Boolean.class, jsonData );
    	soundVolume = json.readValue( "soundVolume", Float.class, jsonData );
    	musicVolume = json.readValue( "musicVolume", Float.class, jsonData );

        // libgdx handles the keys of JSON formatted HashMaps as Strings, but we
        // want it to be an integer instead (levelId)
        /*Map<String,Integer> highScores = json.readValue( "highScores", HashMap.class,
            Integer.class, jsonData );
        for( String levelIdAsString : highScores.keySet() ) {
            int levelId = Integer.valueOf( levelIdAsString );
            Integer highScore = highScores.get( levelIdAsString );
            this.highScores.put( levelId, highScore );
        }

        // finally, read the ship
        ship = json.readValue( "ship", Ship.class, jsonData );*/
    }

    @Override
    public void write(Json json)
    {
        json.writeValue( "isSoundEnabled", isSoundEnabled );
        json.writeValue( "isMusicEnabled", isMusicEnabled );
        json.writeValue( "soundVolume", soundVolume );
        json.writeValue( "musicVolume", musicVolume );
    }
}