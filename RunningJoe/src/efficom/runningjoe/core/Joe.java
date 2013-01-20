package efficom.runningjoe.core;

import aurelienribon.bodyeditor.BodyEditorLoader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import efficom.runningjoe.RunningJoe;

public class Joe extends AbstractGraphicItem {
	private Body body;
	private static final float BODY_WIDTH=100f;
	
	
	public Joe(RjWorld world){
		super(world);		
		this.createStandingJoe();
	}
	
	private void createStandingJoe() {
	    // 0. Create a loader for the file saved from the editor.
		FileHandle fh = Gdx.files.internal("data/joe.json");
	    BodyEditorLoader loader = new BodyEditorLoader(fh);
	 
	    // 1. Create a BodyDef, as usual.
	    BodyDef bd = new BodyDef();
	    bd.position.set(world.getCamera().viewportWidth/2, world.getCamera().viewportHeight/2);
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
	}
	
	public void Jump()
    {
    	Vector2 vel = this.body.getLinearVelocity();
        vel.y = 100;//upwards - don't change x velocity
        body.setLinearVelocity(vel);    	
    }

}
