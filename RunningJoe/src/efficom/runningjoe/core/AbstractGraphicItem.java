package efficom.runningjoe.core;

import java.util.Iterator;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;

public abstract class AbstractGraphicItem {
	protected RjWorld world;
	protected GraphicItemInfos infos;
	protected Body body;
	protected Sprite sprite;
	protected Fixture physicFixture;
	
	/**
	 * Abstract class representing every graphic objects such as Joe,Blocks and Bonus
	 * 
	 * @param world RjWorld object the Graphic Item belongs to
	 * @param name Name of the Graphic Item
	 */
	public AbstractGraphicItem(RjWorld world, String name){
		this.world = world;
	}
	
	public boolean hasContact()
	{
		boolean hasContact = false;
        Iterator<Contact> it = this.world.getWorld().getContactList().iterator();        
        while(it.hasNext() && !hasContact){
        	Contact item = it.next();
        	Fixture fA = item.getFixtureA();
        	Fixture fB = item.getFixtureB();
        	if(fB.getBody() == body || fA.getBody() == body)
        		hasContact = true;        	
        }
        
        return hasContact;
	}
}