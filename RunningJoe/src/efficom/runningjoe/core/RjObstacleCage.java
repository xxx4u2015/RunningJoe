package efficom.runningjoe.core;


/**
 * @author Sylvain MERLIN
 */
public class RjObstacleCage extends RjObstacle {

    /**
     * Abstract class representing every graphic objects such as Joe,Blocks and Bonus
     *
     * @param world RjWorld object the Graphic Item belongs to
     * @param name  Name of the Graphic Item
     */
    public RjObstacleCage(RjWorld world) {
        super(world, "cage");
        this.img="cage";

        this.width = 128;

        this.density = 2.5f ;
        this.friction = 0.6f;
        this.restitution = 0.2f;

    }

}
