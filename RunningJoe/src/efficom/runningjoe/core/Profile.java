package efficom.runningjoe.core;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.OrderedMap;
import efficom.runningjoe.services.ProfileManager;
//import com.blogspot.steigert.tyrian.utils.TextUtils;

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
    public Profile()
    {    	
    }
    
    @SuppressWarnings( "unchecked" )
    @Override
    public void read(Json json, OrderedMap<String,Object> jsonData )
    {
        // read the some basic properties
        /*currentLevelId = json.readValue( "currentLevelId", Integer.class, jsonData );
        credits = json.readValue( "credits", Integer.class, jsonData );

        // libgdx handles the keys of JSON formatted HashMaps as Strings, but we
        // want it to be an integer instead (levelId)
        Map<String,Integer> highScores = json.readValue( "highScores", HashMap.class,
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
        /*json.writeValue( "currentLevelId", currentLevelId );
        json.writeValue( "credits", credits );
        json.writeValue( "highScores", highScores );
        json.writeValue( "ship", ship );*/
    }
}