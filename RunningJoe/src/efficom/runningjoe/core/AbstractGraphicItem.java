package efficom.runningjoe.core;

import java.util.Iterator;

import aurelienribon.bodyeditor.BodyEditorLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import efficom.runningjoe.RunningJoe;

public abstract class AbstractGraphicItem implements IDrawable, IRenderable{
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
	public AbstractGraphicItem(RjWorld world, String name){
		this.world = world;
        infos = new GraphicItemInfos(name);
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
     * @param width The width of the element
     */
    /*public void CreateFixture(Shape shape, float dens, float fric, float rest, float width)
    {
        // Create a FixtureDef
        FixtureDef fd = new FixtureDef();
        fd.density = dens;
        fd.friction = fric;
        fd.restitution = rest;
    } */
	
	/***
	 * Load fixture from a file
	 * @param filename  The texture file name
	 * @param dens  Density from 0 to 1
	 * @param fric  Friction from 0 to 1
	 * @param rest  Restitution from à to 1
	 * @param width The width of the element
	 */
	public void LoadFixture(String filename, float dens, float fric, float rest, float width)
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
	    loader.attachFixture(body, "StandingJoe", fd, ConvertToBox(width));
	}
	
	public void LoadTexture(TextureRegion region, Vector2 pos)
	{
		tr = new TextureWrapper(region, pos);				
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


        Vector2 vecOffset = isLoadedFixture ? new Vector2(tr.width /2, tr.height /2) : new Vector2(0,0);
    	
    	// Calculate position
        Vector2 vecPos = new Vector2(
                ConvertToWorld(body.getPosition().x - vecCam.x) + vecOffset.x,
                ConvertToWorld(body.getPosition().y - vecCam.y) + vecOffset.y);

    	this.tr.SetPosition(vecPos.x, vecPos.y);
    	
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
}