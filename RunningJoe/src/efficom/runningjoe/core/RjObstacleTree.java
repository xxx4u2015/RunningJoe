package efficom.runningjoe.core;

/**
 * @author Sylvain MERLIN
 */
public class RjObstacleTree extends RjObstacle {
    /**
     * Abstract class representing every graphic objects such as Joe,Blocks and Bonus
     *
     * @param world RjWorld object the Graphic Item belongs to
     * @param name  Name of the Graphic Item
     */
    public RjObstacleTree(RjWorld world, String name) {
        super(world, name);
        this.img="tree";
    }
}
