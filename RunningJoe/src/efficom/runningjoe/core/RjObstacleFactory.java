package efficom.runningjoe.core;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * Factory able to create RjObstacle Objects.
 *
 * @author Sylvain MERLIN
 */
public class RjObstacleFactory {

    private LinkedList<RjObstacle> obstacleList;

    RjObstacleFactory(){
        this.obstacleList = new LinkedList<RjObstacle>();
    }

    /**
     * Returns a new RjObstacle Objects.
     *
     * @return newBlock of type
     */
    public RjObstacle generateRjObstacles( RjWorld world , SpriteBatch spriteBatch){
        boolean containsCar;
        containsCar=false;

        // Clean the world from out of screens' obstacles and draw
        Iterator<RjObstacle> i = this.obstacleList.iterator();
        while(i.hasNext()){
            RjObstacle obstacle = i.next();
            try{
                if((obstacle.ScreenPosition().toString().equals("LEFT")) || (obstacle.ScreenPosition().toString().equals("BOTTOM"))){
                    obstacle.destroy();
                    i.remove();
                }
            }catch(Exception e){
                obstacle.drawObstacle(spriteBatch);
                if((obstacle.getClass().getSimpleName().equals("RjObstacleCar")) && ( containsCar == false) ){
                    containsCar=true;
                }
            }
        }

        // Standard bordered Random Method
        Random rand = new Random();
        int choice = rand.nextInt(2 - 1 + 1) + 1;
        RjObstacle obstacle;

        // Generate a Random RjObstacle following these rules
        switch(choice){
            case 1:
                if(this.obstacleList.size()<4){
                    obstacle = new RjObstacleBall(world);
                    this.obstacleList.add(obstacle);
                }
                else
                    obstacle = null;
                break;
            case 2:
                if(containsCar == false){
                    obstacle =  new RjObstacleCar(world);
                    this.obstacleList.add(obstacle);
                }
                else
                    obstacle = null;
                break;
            case 3:
                return new RjObstacleBox(world,"box");
            case 4:
                return new RjObstacleCage(world,"cage");
            case 5:
                return new RjObstacleTree(world,"tree");
            default:
                obstacle =  null;
        }

        // Create obstacle in the world
        if(obstacle!=null){
            obstacle.createObject();
        }

        return obstacle;
    }

}
