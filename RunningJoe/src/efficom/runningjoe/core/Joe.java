package efficom.runningjoe.core;

import java.util.Iterator;
import java.util.List;

import aurelienribon.bodyeditor.BodyEditorLoader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Joe extends AbstractGraphicItem {
	private Body body;
	private static final float BODY_WIDTH=40;
	SpriteBatch spriteBatch;	
	Texture standing;
	private int speed = 100;
	//private Box2DSpriteAdapter adapter;
	
	public Joe(RjWorld world, Texture texture){		
		super(world, "Joe", new Sprite(texture));
		standing = new Texture(Gdx.files.internal("images/standingjoe.png"));
		this.createStandingJoe();
	}
	
	private void createStandingJoe() {
	    // 0. Create a loader for the file saved from the editor.
		FileHandle fh = Gdx.files.internal("data/joe.json");
	    BodyEditorLoader loader = new BodyEditorLoader(fh);
	 
	    // 1. Create a BodyDef, as usual.
	    BodyDef bd = new BodyDef();
	    bd.position.set(world.getCamera().viewportWidth - 100, 25);
	    bd.type = BodyType.DynamicBody;
	    	    	 
	    // 2. Create a FixtureDef, as usual.
	    FixtureDef fd = new FixtureDef();
	    fd.density = 1;
	    fd.friction = 0.5f;
	    fd.restitution = 0.3f;
	    
	    // 3. Create a Body, as usual.
	    body = world.getWorld().createBody(bd);
	    
	 
	    // 4. Create the body fixture automatically by using the loader.
	    loader.attachFixture(body, "StandingJoe", fd, BODY_WIDTH);	
	    
	    	    
	    body.setUserData(this.infos);
	}
	
	public void Jump()
    {
    	Vector2 vel = this.body.getLinearVelocity();
        vel.y = 50;//upwards - don't change x velocity
        
        boolean hasContact = false;
        Iterator<Contact> it = this.world.getWorld().getContactList().iterator();        
        while(it.hasNext() && !hasContact){
        	Contact item = it.next();
        	Fixture fA = item.getFixtureA();
        	Fixture fB = item.getFixtureB();
        	if(fB.getBody() == body || fA.getBody() == body)
        		hasContact = true;        	
        }
        
        if(hasContact)
        	body.setLinearVelocity(vel);    	
    }
	
	public void render()
	{
		if(this.world.isStarded())
			this.run();
	}
	
	private void run()
	{
		Vector2 vel = this.body.getLinearVelocity();
        vel.x = speed;//upwards - don't change x velocity
        body.setLinearVelocity(vel);  
	} 
	
	public int getSpeed()
	{
		return this.speed;
	}
}
