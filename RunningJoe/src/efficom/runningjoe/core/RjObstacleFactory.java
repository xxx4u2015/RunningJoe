package efficom.runningjoe.core;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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
        for (RjObstacle obstacle : this.obstacleList) {
            obstacle.draw(spriteBatch);
            if((obstacle.getClass().getSimpleName().equals("RjObstacleCar")) && ( containsCar == false) ){
                containsCar=true;
            }
        }

        // Standard bordered Random Method
        Random rand = new Random();
        int choice = rand.nextInt(2 - 1 + 1) + 1;
        RjObstacle obstacle;

        // Generate a Random RjObstacle following these rules
        switch(choice){
            case 1:
                if(this.obstacleList.size()<3){
                    obstacle = new RjObstacleBall(world);
                    this.obstacleList.add(obstacle);
                }
                else
                    obstacle = null;
                break;
            case 2:
                if(containsCar == false){
                    System.out.println("create a CAR");
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

        // Create obstacle iun the world
        if(obstacle!=null){
            float position;
            position = (float)680;
            obstacle.createObject(position);
        }

        return obstacle;
    }

}
