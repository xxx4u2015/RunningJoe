package efficom.runningjoe.core;

import com.badlogic.gdx.graphics.Texture;


/**
 * The classe that handle the backgroud with it own speed to create a paralax
 * 
 * @author Guillaume BAILLEUL
 */
public class Background implements IRenderable {
	private float speed;
	private Texture texture;
	private RjWorld world;
	
	public Background(RjWorld world, Texture texture, float speed){
		this.world = world;
		this.speed = speed;
		this.texture = texture;
	}
	
	/**
	 * Make the ground going on and generate/destroy the blocs
	 */
	public void render()
	{
		
	}
}
