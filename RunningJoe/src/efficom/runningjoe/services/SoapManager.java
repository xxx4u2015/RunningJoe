package efficom.runningjoe.services;

import com.badlogic.gdx.Gdx;
import efficom.runningjoe.RunningJoe;
import efficom.runningjoe.ws.userapi.*;

public class SoapManager {
	private static volatile SoapManager instance = null;
	UserApiPortType userWs;
	int userId = -1;
	String name = null;
	
	private SoapManager(){
		super();
		this.connect();		
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
	
	private void connect(){
		try{
			UserApiService userWsService = new UserApiService();		
			userWs = userWsService.getUserApiPort();
			
		}catch(Exception e){
			Gdx.app.log( RunningJoe.LOG, "Error while trying to connect : "
					+ e.getMessage());			
		}
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
	 * @param Name of the player
	 * @param Password of the player
	 * @return Success: null or error description
	 */
	public String Login(String prmName, String prmPassword)
	{
		String ret = null;
		
		try{
			int result = userWs.login(prmName, prmPassword);
			this.userId = result;
			
			if(result > 0){
				this.userId = result;
				this.name = prmName;
			}else{
				ret = this.userWs.errordescription(
				LanguagesManager.getInstance().getLanguage(), result);
			}
			
		}catch(Exception e){
			ret = LanguagesManager.getInstance().getString("ConnectionProblem");
			Gdx.app.log( RunningJoe.LOG, "Error while recording result : "
					+ e.getMessage());
		}
		
		return ret;				
	}
	
	/**
	 * Try to record the score on the server
	 * @param The score value
	 * @return Success: null, Error: description
	 */
	public String RecordScore(double score)
	{
		String ret = null;
		
		try{
			int result = userWs.recordscore(this.name, (int)score);
			
			if(result < 0){								
				ret = this.userWs.errordescription(
						LanguagesManager.getInstance().getLanguage(), result);
				Gdx.app.log( RunningJoe.LOG, "Error while recording result : "
						+ ret);
			}else{
				Gdx.app.log( RunningJoe.LOG, "Score recorded");				
			}
			
		}catch(Exception e){
			ret = LanguagesManager.getInstance().getString("ConnectionProblem");
			Gdx.app.log( RunningJoe.LOG, "Error while recording result : "
					+ e.getMessage());
		}
		
		return ret;			
	}
	
	/**
	 * Retrieving high scores from the web service
	 * @return Array of score
	 */
	public ArrayOfScore GetHighScores()
	{
		try{
			ArrayOfScore result = userWs.highscores(10, 0);
			Gdx.app.log( RunningJoe.LOG, "Highscores retrieved");			
			return result;
			
		}catch(Exception e){
			Gdx.app.log( RunningJoe.LOG, "Error while retrieving highscores : "
					+ e.getMessage());
			
			return null;
		}		
	}
	
	/**
	 * Retrieving high scores from the web service
	 * @return Array of score
	 */
	public ArrayOfString GetHighScoresStrings()
	{		
		try{
			ArrayOfString result = userWs.highscoresstring(10,0);
			Gdx.app.log( RunningJoe.LOG, "Highscores retrieved");			
			return result;
			
		}catch(Exception e){
			Gdx.app.log( RunningJoe.LOG, "Error while retrieving highscores : "
					+ e.getMessage());
			
			return null;
		}
	}
	

}