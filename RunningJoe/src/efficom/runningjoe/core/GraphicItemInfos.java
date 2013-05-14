package efficom.runningjoe.core;

/**
 * A class that handle the informations of a graphic item
 *
 * @author Guillaume BAILLEUL
 */
public class GraphicItemInfos {
    String name;
	
	public GraphicItemInfos(String name)
	{
		this.name = name;		
	}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
