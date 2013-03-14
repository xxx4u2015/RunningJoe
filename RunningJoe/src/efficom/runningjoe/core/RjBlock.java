package efficom.runningjoe.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;


public class RjBlock extends AbstractGraphicItem {

	protected Texture grassTex;
	
	/**
	 * Class constructor
	 * 
	 * @param world RjWorld the block belongs to
	 * @param name Name of the block
	 */
	public RjBlock(RjWorld world, String name) {
		
		super(world, name);
		
		this.grassTex = new Texture(Gdx.files.internal("images/grass2.png"));
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
		groundBox.setAsBox(grassTex.getTextureData().getWidth(), 20.0f);
		this.body.createFixture(groundBox, 0.0f);
		GraphicItemInfos infosFloor = new GraphicItemInfos("Floor "+position, new Sprite(grassTex));
		this.body.setUserData(infosFloor);
	}

	public Vector2 getPosition(){
		if(this.body == null){
			return new Vector2(0,0);
		}
		else{
			return this.body.getPosition();
		}
	}


}
