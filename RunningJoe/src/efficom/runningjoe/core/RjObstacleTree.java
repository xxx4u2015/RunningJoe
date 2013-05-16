package efficom.runningjoe.core;

/**
 * Created with IntelliJ IDEA.
 * User: smerlin
 * Date: 15/05/13
 * Time: 15:04
 * To change this template use File | Settings | File Templates.
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
