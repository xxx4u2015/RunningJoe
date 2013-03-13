package efficom.runningjoe.services;
import com.badlogic.gdx.assets.AssetManager;

public class AssetsManager extends AssetManager {
	private static volatile AssetsManager instance = null;
	
	private AssetsManager(){
		super();		
	}
	
	public final static AssetsManager getInstance() {
    	if (instance == null) {
            synchronized(AssetsManager.class) {
              if (instance == null) {
                instance = new AssetsManager();
              }
            }
         }                
        return instance;
    }

}
