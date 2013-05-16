package efficom.runningjoe.core;

/**
 * Created with IntelliJ IDEA.
 * User: smerlin
 * Date: 15/05/13
 * Time: 15:02
 * To change this template use File | Settings | File Templates.
 */
public class RjObstacleBall extends RjObstacle {
    /**
     * Abstract class representing every graphic objects such as Joe,Blocks and Bonus
     *
     * @param world RjWorld object the Graphic Item belongs to
     * @param name  Name of the Graphic Item
     */
    public RjObstacleBall(RjWorld world) {
        super(world, "ball");
        this.img="ball";
        this.width = 128;
        this.density = 1f ;
        this.friction = 0.4f;
        this.restitution = 0.5f;

    }
}
