package efficom.runningjoe.services;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;

public class AssetsManager extends AssetManager {
	private static volatile AssetsManager instance = null;
	private Texture ground;
	
	private AssetsManager(){
		super();
		this.setGround(new Texture(Gdx.files.internal("images/grass.png")));
		// we set the linear texture filter to improve the stretching
        this.ground.setFilter( TextureFilter.Linear, TextureFilter.Linear );
		
	}
	
	/**
	 * 
	 * AssetManager
	 * 
	 * Instantiate one (and only one) assetManager
	 * 
	 * @return return the only instantiated object
	 */
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
	
	public Texture getGround() {
		return this.ground;
	}

	public void setGround(Texture ground) {
		this.ground = ground;
	}

}
