package efficom.runningjoe.core;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import efficom.runningjoe.RunningJoe;
import efficom.runningjoe.services.AssetsManager;
import com.badlogic.gdx.graphics.Texture;

/**
 * 
 * Class witch represents blocks for the ground of the game.
 * 
 * @author Sylvain MERLIN
 */
public class RjBlock extends AbstractGraphicItem {

	/**
	 * Texture's coefficient
	 */
	private int blockNumber;
	public static int BLOCK_WIDTH = 16;
	public static int BLOCK_HEIGHT = 16;

	/**
	 * Texture of the RjBlock
	 */
	private TextureRegion texture = null;
	
	/**
	 * Inherited Constructor
	 * 
	 * @param world RjWorld object the Graphic Item belongs to
	 * @param name Name of the Graphic Item
	 */
	public RjBlock(RjWorld world, String name, int blockNumber) {
		super(world, name, GraphicItemType.GROUND);

		try{
			this.blockNumber = blockNumber;
		}
		catch(Exception e){
			this.blockNumber = 1;
		}

        Texture texture = AssetsManager.getInstance().getGround();
        int blockCapacity = texture.getWidth() /BLOCK_WIDTH - 1;

		this.texture = new TextureRegion(
                texture,
                (this.blockNumber % blockCapacity )* BLOCK_WIDTH,//(this.blockNumber % 1000) *(BLOCK_WIDTH),
                0,
                BLOCK_WIDTH*2,
                BLOCK_HEIGHT * 6);
		
		this.body = null;
		infos = new GraphicItemInfos(name, GraphicItemType.GROUND);
		
	}
	
	/**
	 * Method to create a randomly generated Roof for the ground or the Ground
	 *
	 * @param position Position of the randomly generated block
	 * @return Returns a random RjBlock
	 */
	public void generateRandomBlock(float position)
    {
		CreateBody(new Vector2(position, ConvertToBox(BLOCK_HEIGHT)), 0, BodyDef.BodyType.StaticBody);

		PolygonShape groundBox = new PolygonShape();
		groundBox.setAsBox(RunningJoe.PixToMeter(BLOCK_WIDTH), RunningJoe.PixToMeter(BLOCK_HEIGHT));
        CreateFixture(groundBox, 0.5f, 0.5f,0.0f);
		
		LoadTexture(this.texture, new Vector2(0,0));
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
