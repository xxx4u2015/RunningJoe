package efficom.runningjoe.services;

import com.badlogic.gdx.Gdx;
import efficom.runningjoe.RunningJoe;
import efficom.runningjoe.ws.userapi.*;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public class SoapManager {
	private static volatile SoapManager instance = null;
    UserApiPortType2 portType2;
	int userId = -1;
	String name = null;
	
	private SoapManager(){
        portType2 = new UserApiPortType2();
	}
	
	public final static SoapManager getInstance() {
    	if (instance == null) {
            synchronized(SoapManager.class) {
              if (instance == null) {
                instance = new SoapManager();
              }
            }
         }                
        return instance;
    }

	
	/**
	 * @return Return true if the client is connected
	 */
	public boolean getIsConnected()
	{
		return this.userId > -1 ? true : false;
	}
	
	/**
	 * Try to log the user into the server
	 * @param prmName of the player
	 * @param prmPassword of the player
	 * @return Success: null or error description
	 */
	public String Login(String prmName, String prmPassword)
	{
		String ret = null;

        try {
            int error = portType2.login(prmName, prmPassword);

            this.userId = error;

            if(error < 0){
                ret = this.portType2.errordescription(
                        LanguagesManager.getInstance().getLanguage(), error);
            }else{
                this.userId = error;
                this.name = prmName;

                ret = null;
            }

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (XmlPullParserException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
		
		return ret;				
	}

    /**
     * Try to log the user into the server
     * @param prmName of the player
     * @param prmEmail email of the player
     * @param prmPassword of the player
     * @return Success: null or error description
     */
    public String Register(String prmName, String prmEmail, String prmPassword)
    {
        String ret = null;

        try {
            int error = portType2.register(prmName, prmEmail, prmPassword);

            this.userId = error;

            if(error < 0){
                ret = this.portType2.errordescription(
                        LanguagesManager.getInstance().getLanguage(), error);
            }else{
                ret = null;
            }

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (XmlPullParserException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return ret;
    }
	
	/**
	 * Try to record the score on the server
	 * @param score score value
	 * @return Success: null, Error: description
	 */
	public String RecordScore(double score)
	{
		String ret = null;

        try {
            int error = portType2.recordscore(this.name, (int)score);

            if(error < 0){
                ret = this.portType2.errordescription(
                        LanguagesManager.getInstance().getLanguage(), error);
                Gdx.app.log( RunningJoe.LOG, "Error while recording result : "
                        + ret);
            }else{
                Gdx.app.log( RunningJoe.LOG, "Score recorded");
            }

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (XmlPullParserException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
		
		return ret;			
	}
	

	/**
	 * Retrieving high scores from the web service
	 * @return Array of score
	 */
	public ArrayList<String> GetHighScoresStrings()
	{
        try {
            ArrayList<String> result = portType2.highscoresstring(10, 0);
            Gdx.app.log( RunningJoe.LOG, "Highscores retrieved");
            return result;

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (XmlPullParserException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return null;
	}
	

}