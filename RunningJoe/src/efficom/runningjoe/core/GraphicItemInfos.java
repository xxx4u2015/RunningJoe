package efficom.runningjoe.core;

/**
 * A class that handle the informations of a graphic item
 *
 * @author Guillaume BAILLEUL
 */
public class GraphicItemInfos {
    private String name;
    private GraphicItemType type;

	
	public GraphicItemInfos(String name, GraphicItemType type)
	{
		this.name = name;
        this.type = type;
	}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GraphicItemType getType(){return this.type;}
}
