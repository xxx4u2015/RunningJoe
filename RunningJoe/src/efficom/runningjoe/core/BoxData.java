package efficom.runningjoe.core;

/**
 * Class that contains the object datas
 * @author Guillaume BAILLEUL
 */
public class BoxData{
    int collisionGroup;
    int boxId;
    String name;
    
    public BoxData(int boxid,int collisiongroup, String name){
    	set(boxid,collisiongroup, name);
    }
    
    public void set(int boxid,int collisiongroup, String name){
    	this.boxId=boxid;
        this.collisionGroup=collisiongroup;
    }
    
    public int getBoxId(){
    	return this.boxId;
    }
    
    public int getCollisionGroup(){ 
    	return this.collisionGroup;
    }
    
    public String getName()
    {
    	return this.name;
    }
}