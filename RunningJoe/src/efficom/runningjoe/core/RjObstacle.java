package efficom.runningjoe.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;

/**
 * Created with IntelliJ IDEA.
 * User: smerlin
 * Date: 15/05/13
 * Time: 11:01
 * To change this template use File | Settings | File Templates.
 */

public class RjObstacle extends AbstractGraphicItem {

    protected float density;
    protected float friction;
    protected float restitution;
    protected float width;
    protected String name;
    protected String img;

    /**
     * Abstract class representing every graphic objects such as Joe,Blocks and Bonus
     *
     * @param world RjWorld object the Graphic Item belongs to
     * @param name  Name of the Graphic Item
     */
    public RjObstacle(RjWorld world, String name) {
        super(world, name);
        this.name = name;
    }

    public void createObject(float position){

        // Create the body and fixture
        CreateBody(new Vector2(position,80),0,BodyDef.BodyType.DynamicBody, false);
        // Load Fixture from a Json
        LoadFixture("data/"+this.img+".json", this.name,this.density, this.friction, this.restitution,this.width);

        // Create the Texture
        TextureRegion region = new TextureRegion(new Texture(Gdx.files.internal("images/obstacles/"+this.img+".png")));
        this.sprite = new Sprite(new Texture(
                Gdx.files.internal("images/obstacles/"+name+".png")));
        LoadTexture(region, new Vector2(-this.width/2,-this.width/2));

        infos = new GraphicItemInfos(this.img);

        this.body.setUserData(infos);

    }

}
