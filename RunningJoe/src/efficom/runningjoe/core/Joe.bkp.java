package efficom.runningjoe.core;

import java.util.List;

import aurelienribon.bodyeditor.BodyEditorLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.WorldManifold;

public class Joe extends AbstractGraphicItem {
	private static final float BODY_WIDTH = 40;
	private int speed = 40;
	
	public Joe(RjWorld world){		
		super(world, "Joe");
		this.sprite = new Sprite(new Texture(
				Gdx.files.internal("images/standingjoe.png")));
		new Texture(Gdx.files.internal("images/standingjoe.png"));
		this.createStandingJoe();
	}
	
	/*
	 * Create the standing joe representation
	 */
	private void createStandingJoe() {
		infos = new GraphicItemInfos("Joe",this.sprite);
		// 0. Create a loader for the file saved from the editor.
		FileHandle fh = Gdx.files.internal("data/joe.json");
	    BodyEditorLoader loader = new BodyEditorLoader(fh);
	 
	    // 1. Create a BodyDef, as usual.
	    BodyDef bd = new BodyDef();
	    bd.position.set(this.world.getCamera().viewportWidth - 100, 25);
	    bd.type = BodyType.DynamicBody;
	    	    	 
	    // 2. Create a FixtureDef, as usual.
	    FixtureDef fd = new FixtureDef();
	    fd.density = 1.0f;
	    fd.friction = 0.0f;
	    fd.restitution = 0.0f;
	    
	    // 3. Create a Body, as usual.
	    this.body = this.world.getWorld().createBody(bd);	    
	 
	    // 4. Create the body fixture automatically by using the loader.
	    loader.attachFixture(body, "StandingJoe", fd, BODY_WIDTH);
	    
	    	    
	    this.body.setUserData(infos);
	    
	    
	}	
	
	/*
	 * 
	 */
	private void createRunningJoe()
	{
	}
	
	/*
	 * 
	 */
	private void createJumpingJoe()
	{
	}
	
	/*
	 * 
	 */
	public void Jump()
    {
		Vector2 pos = this.body.getPosition();
    	Vector2 vel = this.body.getLinearVelocity();
        vel.y = 5;//upwards - don't change x velocity
        
        
        if(this.hasContact()) {
        	body.setLinearVelocity(vel.x, 0);
            System.out.println("jump before: " + body.getLinearVelocity());
            body.setTransform(pos.x, pos.y + 0.01f, 0);
    		body.applyLinearImpulse(0, 30, pos.x, pos.y);			
    		System.out.println("jump, " + body.getLinearVelocity());        	
        }
        
        
        
    }
	
	/*
	 * 	
	 */
	public void render()
	{
		if(this.world.isStarded()){
			this.run();
			
			// Remove frixion from fixture if joe has no ground contact.
			if(!this.hasContact()){        	
	        	for(Fixture fix : body.getFixtureList())
	        		fix.setFriction(0.0f);
	        }
		}
	}
	
	/*
	 * 
	 */
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
