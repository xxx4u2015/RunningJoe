package efficom.runningjoe.core;

/**
 * 
 * 
 * @author Guillaume BAILLEUL
 */
public class Score {
	private double value = 0;
	
	public Score()
	{
		this.value = 0;	
	}
	
	public Score(double initValue)
	{
		this.value = initValue;
	}
	
	public double getValue(){
		return this.value;
	}
	
	public void addValue(double value){
		this.value += value;
	}
	

}
