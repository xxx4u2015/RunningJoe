package efficom.runningjoe.core;

import aurelienribon.bodyeditor.BodyEditorLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Joe extends AbstractGraphicItem {
	private static final float BODY_WIDTH = 40;
	private int speed = 0;
	
	public Joe(RjWorld world, Texture texture){		
		super(world, "Joe", new Sprite(texture));
		new Texture(Gdx.files.internal("images/standingjoe.png"));
		this.createStandingJoe();
	}
	
	/*
	 * Create the standing joe representation
	 */
	private void createStandingJoe() {
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
	    body = world.getWorld().createBody(bd);	    
	 
	    // 4. Create the body fixture automatically by using the loader.
	    loader.attachFixture(body, "StandingJoe", fd, BODY_WIDTH);	    
	    	    
	    body.setUserData(this.infos);
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
    	Vector2 vel = this.body.getLinearVelocity();
        vel.y = 5;//upwards - don't change x velocity
        
        if(this.hasContact())
        	body.setLinearVelocity(vel);    	
    }
	
	/*
	 * 	
	 */
	public void render()
	{
		if(this.world.isStarded())
			this.run();
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
