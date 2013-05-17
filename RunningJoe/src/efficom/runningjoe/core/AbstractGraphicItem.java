package efficom.runningjoe.core;

import java.util.Iterator;
import aurelienribon.bodyeditor.BodyEditorLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import efficom.runningjoe.RunningJoe;
import com.badlogic.gdx.physics.box2d.Shape;

public abstract class AbstractGraphicItem implements IDrawable, IRenderable{
    public enum Position{TOP, LEFT, BOTTOM, RIGHT}

	protected RjWorld world;
	protected GraphicItemInfos infos;
	protected Body body;
	protected Sprite sprite;
	protected TextureWrapper tr;
    protected Boolean isLoadedFixture = false;
	
	/**
	 * Abstract class representing every graphic objects such as Joe,Blocks and Bonus
	 * 
	 * @param world RjWorld object the Graphic Item belongs to
	 * @param name Name of the Graphic Item
	 */
	public AbstractGraphicItem(RjWorld world, String name, GraphicItemType type){
		this.world = world;
        infos = new GraphicItemInfos(name, type);
	}
	
	/**
	 * Create the body of the item
	 * @param pos
	 * @param angle
	 * @param bodyType
	 * @param rot Fixed rotation (Default = false)
	 */
	public void CreateBody(Vector2 pos,float angle, BodyType bodyType, boolean rot){
		BodyDef bodyDef = new BodyDef(); 
	    bodyDef.type = bodyType;
	    bodyDef.position.set(ConvertToBox(pos.x),ConvertToBox(pos.y));
	    bodyDef.angle=angle;
	    bodyDef.fixedRotation = rot;

	    body = world.getWorld().createBody(bodyDef);
        body.setUserData(this.infos);


	}
	
	public void CreateBody(Vector2 pos,float angle, BodyType bodyType){
		CreateBody(pos, angle, bodyType, false);
	}

    /***
     * Create fixture
     * @param dens  Density from 0 to 1
     * @param fric  Friction from 0 to 1
     * @param rest  Restitution from à to 1
     */
    public void CreateFixture(Shape shape, float dens, float fric, float rest)
    {
        // Create a FixtureDef
        FixtureDef fd = new FixtureDef();
        fd.density = dens;
        fd.friction = fric;
        fd.restitution = rest;
        fd.shape = shape;

        this.body.createFixture(fd);
    }
	
	/***
	 * Load fixture from a file
	 * @param filename  The texture file name
	 * @param dens  Density from 0 to 1
	 * @param fric  Friction from 0 to 1
	 * @param rest  Restitution from à to 1
	 * @param width The width of the element
	 */
	public void LoadFixture(String filename,String fixtureName, float dens, float fric, float rest, float width)
	{
		// Create a loader for the file saved from the editor.
		FileHandle fh = Gdx.files.internal(filename);
		BodyEditorLoader loader = new BodyEditorLoader(fh);
		
		// Create a FixtureDef
	    FixtureDef fd = new FixtureDef();
	    fd.density = dens;
	    fd.friction = fric;
	    fd.restitution = rest;

        isLoadedFixture = true;
		
		// Create the body fixture automatically by using the loader.
	    loader.attachFixture(body, fixtureName, fd, ConvertToBox(width));
	}

    /**
     * Add TextureWrapper to the Graphic Item
     * @param region
     * @param pos
     */
	public void LoadTexture(TextureRegion region, Vector2 pos)
	{
		tr = new TextureWrapper(region, pos);
	}
    public void LoadTexture(TextureRegion region, Vector2 pos,float x, float y)
    {
        tr = new TextureWrapper(region, pos,x,y);
    }
	
	float ConvertToBox(float x){
		return RunningJoe.PixToMeter(x);
    }
	
    float ConvertToWorld(float x){
        return RunningJoe.MeterToPix(x);
    }
    
    /***
     * Calculate the texture position and draw it
     * @param spriteBatch The sprite batch
     */
    public void draw(SpriteBatch spriteBatch)
    {
    	OrthographicCamera camera = world.getCamera();
        Vector2 vecCam = new Vector2(
                camera.position.x - camera.viewportWidth /2,
                camera.position.y - camera.viewportHeight /2
            );

        // Calculate position of the center of the body
        Vector2 vecPos = new Vector2(
                ConvertToWorld(body.getWorldCenter().x - vecCam.x),
                ConvertToWorld(body.getWorldCenter().y - vecCam.y));


    	this.tr.SetPosition(vecPos.x, vecPos.y);

        this.tr.SetRotation(this.body.getAngle()* MathUtils.radiansToDegrees);

    	// Draw the texture
    	this.tr.Draw(spriteBatch);
    }

    /**
     * @todo Implement the logic
     */
    public void render()
    {

    }
	
	public boolean hasContact()
	{
		boolean hasContact = false;
        Iterator<Contact> it = this.world.getWorld().getContactList().iterator();        
        while(it.hasNext() && !hasContact){
        	Contact item = it.next();
        	Fixture fA = item.getFixtureA();
        	Fixture fB = item.getFixtureB();
        	if(fB.getBody() == body || fA.getBody() == body)
        		hasContact = true;        	
        }
        
        return hasContact;
	}

    public boolean hasGroundContact()
    {
        boolean touch = false;

        Iterator<Contact> it = RunningJoe.getInstance().getWorld().getWorld().getContactList().iterator();
        while(it.hasNext() && !touch){
            Contact item = it.next();

            Fixture fA = item.getFixtureA();
            Fixture fB = item.getFixtureB();

            if((fB.getBody() == body || fA.getBody() == body)){
                GraphicItemInfos infos;

                if(fB.getBody() == body)
                    infos = (GraphicItemInfos) fA.getBody().getUserData();
                else
                    infos = (GraphicItemInfos) fB.getBody().getUserData();

                if(infos != null && infos.getType() == GraphicItemType.GROUND)
                    touch = true;
            }
        }

        return touch;
    }

    /**
     * Return true item is out of screen
     * @return True if object is out of screen
     */
    public boolean isOutOfScreen()
    {
        if(ScreenPosition() != null)
            return true;
        else
            return false;
    }

    /**
     * Get the relative position of the object from the out screen
     *
     * @return The relative direction from the screen
     */
    public Position ScreenPosition()
    {
        OrthographicCamera camera = this.world.getCamera();

        if(body.getPosition().x  + ConvertToBox(tr.width) < (camera.position.x - (camera.viewportWidth / 2))){
            return Position.LEFT;
        }else if(body.getPosition().x  > (camera.position.x + (camera.viewportWidth / 2))){
            return Position.RIGHT;
        }else if(body.getPosition().y  < (camera.position.y - (camera.viewportWidth / 2))){
            return Position.BOTTOM;
        }else if(body.getPosition().y  > (camera.position.y + (camera.viewportWidth / 2))){
            return Position.TOP;
        }

        return null;
    }


    /**
     * Remove the Graphic Item From the World
     */
    public void destroy(){
        this.world.getWorld().destroyBody(this.body);
    }
}