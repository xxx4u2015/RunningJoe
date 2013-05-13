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

public abstract class AbstractGraphicItem {
	protected RjWorld world;
	protected GraphicItemInfos infos;
	protected Body body;
	protected Sprite sprite;
	protected TextureWrapper tr;
	
	/**
	 * Abstract class representing every graphic objects such as Joe,Blocks and Bonus
	 * 
	 * @param world RjWorld object the Graphic Item belongs to
	 * @param name Name of the Graphic Item
	 */
	public AbstractGraphicItem(RjWorld world, String name){
		this.world = world;
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
	}
	
	public void CreateBody(Vector2 pos,float angle, BodyType bodyType){
		CreateBody(pos, angle, bodyType, false);
	}
	
	/***
	 * Load fixture from a file
	 * @param filename
	 * @param dens
	 * @param fric
	 * @param rest
	 * @param width
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
     * @param spriteBatch
     */
    public void DrawTexture(SpriteBatch spriteBatch)
    {
    	OrthographicCamera camera = world.getCamera();
    	//float xcam = camera.position.x - camera.viewportWidth /2;
    	float xBody = body.getPosition().x;    	
    	float yBody = body.getPosition().y;
    	
    	float xTextOffset = tr.width /2;
    	float yTextOffset = tr.height /2;
    	
    	// Calculate position
    	float x = ConvertToWorld(xBody)+ xTextOffset; 
    	float y = ConvertToWorld(yBody)+yTextOffset; 
    	this.tr.SetPosition(x, y);
    	
    	// Draw the texture
    	this.tr.Draw(spriteBatch);    	
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