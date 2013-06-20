package efficom.runningjoe.core;

/**
 * 
 * 
 * @author Guillaume BAILLEUL
 */
public class Score {
	private float value = 0;
	
	public Score()
	{
		this.value = 0;	
	}
	
	public Score(int initValue)
	{
		this.value = initValue;
	}
	
	public int getValue(){
		return (int)this.value;
	}
	
	public void addValue(float value){
		this.value += value;
	}
	

}
