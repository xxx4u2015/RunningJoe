package efficom.runningjoe.core;

import java.util.Random;

/**
 * @author Sylvain MERLIN
 */
public class RjObstacleBox extends RjObstacle {

    protected float scaleCoef;

    /**
     * Abstract class representing every graphic objects such as Joe,Blocks and Bonus
     *
     * @param world RjWorld object the Graphic Item belongs to
     * @param name  Name of the Graphic Item
     */
    public RjObstacleBox(RjWorld world) {
        super(world, "box");
        this.img="box";

        // Base image size is 128
        // Standard bordered Random Method
        Random rand = new Random();
        this.scaleCoef = 1 + rand.nextInt(4 - 1 + 1) + 1;
        this.width = 128/scaleCoef;

        this.density = 0.5f*scaleCoef ;
        this.friction = 0.4f;
        this.restitution = 0.5f;

    }

    @Override
    public float getScale(){
        return 1f/scaleCoef;
    }
}
