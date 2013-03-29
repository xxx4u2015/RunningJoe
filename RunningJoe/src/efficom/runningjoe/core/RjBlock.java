package efficom.runningjoe.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

/**
 * 
 * Class witch represents blocks for the ground or the roof of the game.
 * 
 * @author Sylvain MERLIN
 */
public class RjBlock extends AbstractGraphicItem {

	/**
	 * Texture of the RjBlock
	 */
	protected Texture texture;
	
	/**
	 * Inherited Constructor
	 * 
	 * @param world RjWorld object the Graphic Item belongs to
	 * @param name Name of the Graphic Item
	 */
	public RjBlock(RjWorld world, String name) {
		
		super(world, name);
		
		this.texture = new Texture(Gdx.files.internal("images/grass2.png"));
		this.body = null;
		infos = new GraphicItemInfos(name, sprite);
		
	}
	
	/**
	 * Method to create a randomly generated Roof for the ground or the Ground
	 *
	 * @param position Position of the randomly generated block
	 * @return Returns a random RjBlock
	 */
	public void generateRandomBlock(float position){
		BodyDef groundBodyDef = new BodyDef();

		groundBodyDef.position.set(new Vector2(position, 0));
		this.body = this.world.getWorld().createBody(groundBodyDef);
		PolygonShape groundBox = new PolygonShape();
		groundBox.setAsBox(texture.getTextureData().getWidth(), 20.0f);
		this.body.createFixture(groundBox, 0.0f);
		GraphicItemInfos infosFloor = new GraphicItemInfos("Floor "+position, new Sprite(texture));
		this.body.setUserData(infosFloor);
	}

	
	/**
	 * Method to get the position of the block in the world.
	 * 
	 * @return Vector2 Position of the block in the RjWolrd's world grid.
	 */
	public Vector2 getPosition(){
		if(this.body == null){
			return new Vector2(0,0);
		}
		else{
			return this.body.getPosition();
		}
	}


}
