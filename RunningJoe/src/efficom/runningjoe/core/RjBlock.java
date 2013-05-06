package efficom.runningjoe.core;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import efficom.runningjoe.RunningJoe;
import efficom.runningjoe.services.AssetsManager;

/**
 * 
 * Class witch represents blocks for the ground or the roof of the game.
 * 
 * @author Sylvain MERLIN
 */
public class RjBlock extends AbstractGraphicItem {
	
	/**
	 * Texture's coefficient
	 */
	private int blockNumber;
	public static float BLOCK_WIDTH = 16;
	public static float BLOCK_HEIGHT = 8;
	
	/**
	 * Texture of the RjBlock
	 */
	private TextureRegion texture;
	
	/**
	 * Inherited Constructor
	 * 
	 * @param world RjWorld object the Graphic Item belongs to
	 * @param name Name of the Graphic Item
	 */
	public RjBlock(RjWorld world, String name) {
		super(world, name);
		
		try{
			this.blockNumber = world.getLastBlock().getNextBlockNumber();
		}
		catch(Exception e){
			this.blockNumber = 1;
		}
		this.texture = new TextureRegion( AssetsManager.getInstance().getGround(), this.blockNumber*32, 0, 32, 48 );
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

		groundBodyDef.position.set(new Vector2(position, RunningJoe.PixToMeter(BLOCK_HEIGHT)));
		this.body = this.world.getWorld().createBody(groundBodyDef);
		PolygonShape groundBox = new PolygonShape();
		groundBox.setAsBox(RunningJoe.PixToMeter(BLOCK_WIDTH), RunningJoe.PixToMeter(BLOCK_HEIGHT));
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
	
	public int getNextBlockNumber(){
		int nextBlockNumber=this.blockNumber+1;
		if(nextBlockNumber>15){
			nextBlockNumber=1;
		}
		return nextBlockNumber;
	}
}
