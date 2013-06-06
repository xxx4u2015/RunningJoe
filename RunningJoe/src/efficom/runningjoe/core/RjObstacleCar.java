package efficom.runningjoe.core;

/**
 * @author Sylvain MERLIN
 */
public class RjObstacleCar extends RjObstacle {
    /**
     * Abstract class representing every graphic objects such as Joe,Blocks and Bonus
     *
     * @param world RjWorld object the Graphic Item belongs to
     * @param name  Name of the Graphic Item
     */
    public RjObstacleCar(RjWorld world) {
        super(world, "car");
        this.img="car";
        this.width = 256;
        this.density = 30f ;
        this.friction = 1f;
        this.restitution = 0.1f;
    }
}
