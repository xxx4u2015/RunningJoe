package efficom.runningjoe.core;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/***
 * Class that handle the texture for Box2D
 * 
 * @author Guillaume Bailleul
 */
public class TextureWrapper{
    TextureRegion region;
    int width;
    int height;
    Vector2 position;
    float scaleX;
    float scaleY;
    float originX;
    float originY;
    float rotation;


    public TextureWrapper(TextureRegion region,Vector2 pos){
        scaleX=1;
        scaleY=1;
        this.init(region,pos);
    }
    public TextureWrapper(TextureRegion region,Vector2 pos,float scaleX, float scaleY){
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.init(region,pos);
    }
    public void init(TextureRegion region,Vector2 pos){
        this.position=pos;
        SetTextureRegion(region);

    }
    public void SetTextureRegion(TextureRegion region){
         this.region=region;
         width=region.getRegionWidth();
         height=region.getRegionHeight();
         originX=width/2;
         originY=height/2;
    }
    public int GetWidth(){
       return width;
    } 
    public int GetHeight(){
         return height;
    } 

    public void SetRotation(float r){
         rotation=r;
    }

    public void SetPosition(float x,float y){
        position.set(x,y);
    }

    public void Draw(SpriteBatch sp){
         sp.draw(region,position.x-width/2f, position.y-height/2f,
          originX, originY, width, height,
         scaleX, scaleY, rotation);
    }
}