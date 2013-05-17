package efficom.runningjoe.core;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: smerlin
 * Date: 15/05/13
 * Time: 15:02
 * To change this template use File | Settings | File Templates.
 */
public class RjObstacleBall extends RjObstacle {

    int scaleCoef;

    /**
     * Abstract class representing every graphic objects such as Joe,Blocks and Bonus
     *
     * @param world RjWorld object the Graphic Item belongs to
     * @param name  Name of the Graphic Item
     */
    public RjObstacleBall(RjWorld world) {
        super(world, "ball");
        this.img="ball";

        // Base image size is 128
        // Standard bordered Random Method
        Random rand = new Random();
        this.scaleCoef = rand.nextInt(4 - 1 + 1) + 1;
        this.width = 128/scaleCoef;

        this.density = 1f ;
        this.friction = 0.4f;
        this.restitution = 0.5f;

    }

    @Override
    public float getScale(){
        return 1f/scaleCoef;
    }
}
