package efficom.runningjoe.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
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

    /**
     * Enum needed to get the type of the obstacle
     *
     * Only used in RjObstacle.
     */
    protected enum obstacleType{
        BALL("ball"),
        BOX("box"),
        CAGE("cage"),
        TREE("tree");

        private String name;

        obstacleType(String name){
            this.name = name;
        }

        public String getName(){
            return this.name;
        }
    };

    protected obstacleType type;
    protected float density;
    protected float friction;
    protected float restitution;
    protected float width;
    /**
     * Abstract class representing every graphic objects such as Joe,Blocks and Bonus
     *
     * @param world RjWorld object the Graphic Item belongs to
     * @param name  Name of the Graphic Item
     */
    public RjObstacle(RjWorld world, String name) {
        super(world, name);
    }



    public void createObject(){

        // Create the body and fixture
        CreateBody(initPos,0, BodyDef.BodyType.DynamicBody, true);

        // Load Fixture from a Json
        LoadFixture("data/"+this.type.getName()+".json", this.density, this.friction, this.restitution,this.width);

        // Create the Texture
        TextureRegion region = new TextureRegion(new Texture(Gdx.files.internal("images/obstacles/"+this.type.getName()+".png")));
        LoadTexture(region, new Vector2(0,0));

        infos = new GraphicItemInfos("Joe");
        this.body.setUserData(infos);
    }

}
