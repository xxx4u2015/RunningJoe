package efficom.runningjoe.core;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import efficom.runningjoe.RunningJoe;


/**
 * The classe that handle the backgroud with it own speed to create a parallax
 * 
 * @author Guillaume BAILLEUL
 */
public class Background{
	private float speed;
	private RjWorld world;
	private TextureWrapper tr1, tr2;
	
	public Background(RjWorld world,TextureRegion region, float speed){
		this.world = world;
		this.speed = speed;
		tr1 = new TextureWrapper(region, new Vector2(0,0));
		tr2 = new TextureWrapper(region, new Vector2(0,0));
	}
	
	/**
	 * Make the ground going on and generate/destroy the blocs
	 */
	public void DrawTexture(SpriteBatch spriteBatch)
	{		
		OrthographicCamera camera = world.getCamera();		
		
		Vector2 vectCam = new Vector2(
				camera.position.x - camera.viewportWidth /2,
				camera.position.y - camera.viewportHeight /2);
    	
    	Vector2 vectOffset = new Vector2(tr1.width /2,tr1.height /2);
    	
    	// Inverse the tr
    	if(RunningJoe.PixToMeter(tr2.position.x) < RunningJoe.MeterToPix(vectCam.x)){
    		tr1.SetPosition(tr2.position.x, tr2.position.y);
    	}
    	
    	// Calculate position
    	Vector2 pos1 = new Vector2(
    			vectOffset.x - RunningJoe.MeterToPix(vectCam.x*this.speed),
    			vectOffset.y - RunningJoe.MeterToPix(vectCam.y));
    	this.tr1.SetPosition(pos1.x, pos1.y);
    	
    	// Draw the texture
    	this.tr1.Draw(spriteBatch);
    	
    	this.tr2.SetPosition(pos1.x+tr1.width, pos1.y);
    	this.tr2.Draw(spriteBatch);
    	
	}
}
