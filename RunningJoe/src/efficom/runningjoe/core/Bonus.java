package efficom.runningjoe.core;

/**
 * Class witch represents Bonus object that Joe can grab
 * 
 * I'll work on it after the ground and roof generation
 * 
 * @author Sylvain MERLIN
 *
 */
public class Bonus extends AbstractGraphicItem {

	/**
	 * Enumerate the list of bonus.
	 */
	@SuppressWarnings("unused")
	private enum typeBonus {
		RABBIT("rabbit.png"),
		SNAIL("snail.png"),
		REVERSE("reverse.png");
		
		private String picture;
		
		private typeBonus(String pic){
			this.picture = pic;
		}
		
	};
	
	/**
	 * Inherited Constructor
	 * 
	 * @param world RjWorld object the Graphic Item belongs to
	 * @param name Name of the Graphic Item
	 */
	public Bonus(RjWorld world, String name) {
		super(world, name);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Set the object with the attributes of a Bonus
	 * 
	 * @return Bonus A randomly generated bonus
	 */
	public Bonus generateRandom(){
		
		return this;
	}

}
