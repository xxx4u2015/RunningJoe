package efficom.runningjoe.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;


public class RjBlock extends AbstractGraphicItem {

	protected Texture grassTex;
	
	/**
	 * Class constructor
	 * 
	 * @param world RjWorld the block belongs to
	 * @param name Name of the block
	 */
	public RjBlock(RjWorld world, String name, Sprite sprite) {
		super(world, name);
		
		grassTex = new Texture(Gdx.files.internal("images/grass2.png"));
	}

	


}
