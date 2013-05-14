package efficom.runningjoe.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.Iterator;
import java.util.LinkedList;

import efficom.runningjoe.RunningJoe;


/**
 * The classe that handle the backgroud with it own speed to create a parallax
 * 
 * @author Guillaume BAILLEUL
 */
public class Background{
	private float speed;
	private RjWorld world;
    private LinkedList<TextureWrapper> listTr;
    private int trWidth, trHeight;
    private TextureRegion region;
    private int deleted = 0;
	
	public Background(RjWorld world,TextureRegion region, float speed){
		this.world = world;
		this.speed = speed;
        listTr = new LinkedList<TextureWrapper>();

        this.region = region;

        TextureWrapper tr = new TextureWrapper(region, new Vector2(0,0));
        trWidth = tr.width;
        trHeight = tr.height;
        listTr.add(tr);

	}
	
	/**
	 * Make the ground going on and generate/destroy the blocs
	 */
	public void DrawTexture(SpriteBatch spriteBatch)
	{		
		OrthographicCamera camera = world.getCamera();		
		
		Vector2 vecCam = new Vector2(
				camera.position.x - camera.viewportWidth /2,
				camera.position.y - camera.viewportHeight /2);
    	
    	// Inverse the tr
    	if(listTr.getLast().position.x < RunningJoe.MeterToPix(camera.position.x - camera.viewportWidth)){
            listTr.add(new TextureWrapper(region, new Vector2(0,0)));
            Gdx.app.log(RunningJoe.LOG, "Created background");
    	}
    	
    	// Calculate position
    	Vector2 pos1 = new Vector2(
    			trWidth/2 - RunningJoe.MeterToPix(vecCam.x*this.speed),
    			trHeight/2 - RunningJoe.MeterToPix(vecCam.y));

        Iterator<TextureWrapper> iTr = listTr.iterator();
        int i = 0;
        TextureWrapper toDelete = null;
        while(iTr.hasNext())
        {
            TextureWrapper tr = iTr.next();
            tr.SetPosition(pos1.x + trWidth * (i+deleted), pos1.y);
            tr.Draw(spriteBatch);
            // Set a block to delete if not displayed anymore
            if( tr.position.x < -trWidth/2) toDelete = tr;
            i++;
        }

        // Delete the block not displayed
        if (toDelete != null)
        {
            listTr.remove(toDelete);
            Gdx.app.log(RunningJoe.LOG, "Deleted background");
            this.deleted++;
        }


    	
	}
}
