package efficom.runningjoe.services;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import efficom.runningjoe.RunningJoe;
import efficom.runningjoe.user.Profile;

/**
 * Profile operations.
 */
public class ProfileManager
{
    // the location of the profile data file
    private static final String PROFILE_DATA_FILE = "data/profile-v1.json";
    private static volatile ProfileManager instance = null;

    // the loaded profile (may be null)
    private Profile profile;

    /**
     * Creates the profile manager.
     */
    private ProfileManager()
    {
    }
    
    public final static ProfileManager getInstance() {
    	if (instance == null) {
            synchronized(ProfileManager.class) {
              if (instance == null) {
                instance = new ProfileManager();
              }
            }
         }                
        return instance;
    } 
    

    /**
     * Retrieves the player's profile, creating one if needed.
     */
    public Profile retrieveProfile()
    {
        // create the handle for the profile data file
        FileHandle profileDataFile = Gdx.files.local( PROFILE_DATA_FILE );
        Gdx.app.log( RunningJoe.LOG, "Retrieving profile from: " + profileDataFile.path() );

        // if the profile is already loaded, just return it
        if( profile != null ) return profile;

        // create the JSON utility object
        Json json = new Json();

        // check if the profile data file exists
        if( profileDataFile.exists() ) {
            // load the profile from the data file
            try {
                // read the file as text
                String profileAsText = profileDataFile.readString().trim();

                // decode the contents (if it's base64 encoded)
                if( profileAsText.matches( "^[A-Za-z0-9/+=]+$" ) ) {
                    Gdx.app.log( RunningJoe.LOG, "Persisted profile is base64 encoded" );
                    profileAsText = Base64Coder.decodeString( profileAsText );
                }

                // restore the state
                profile = json.fromJson( Profile.class, profileAsText );

            } catch( Exception e ) {

                // log the exception
                Gdx.app.error( RunningJoe.LOG, "Unable to parse existing profile data file", e );

                // recover by creating a fresh new profile data file;
                // note that the player will lose all game progress
                profile = new Profile();
                persist( profile );
            }

        } else {
            // create a new profile data file
            profile = new Profile();
            persist( profile );
        }

        // return the result
        return profile;
    }

    /**
     * Persists the given profile.
     */
    protected void persist(
        Profile profile )
    {
        // create the handle for the profile data file
        FileHandle profileDataFile = Gdx.files.local( PROFILE_DATA_FILE );
        Gdx.app.log( RunningJoe.LOG, "Persisting profile in: " + profileDataFile.path() );

        // create the JSON utility object
        Json json = new Json();

        // convert the given profile to text
        String profileAsText = json.toJson( profile );

        // encode the text
        if( ! RunningJoe.DEV_MODE ) {
            profileAsText = Base64Coder.encodeString( profileAsText );
        }

        // write the profile data file
        profileDataFile.writeString( profileAsText, false );
    }

    /**
     * Persists the player's profile.
     * <p>
     * If no profile is available, this method does nothing.
     */
    public void persist()
    {
        if( profile != null ) {
            persist( profile );
        }
    }
}