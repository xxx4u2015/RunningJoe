package efficom.runningjoe.core;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class AbstractGraphicItem {
	RjWorld world;
	GraphicItemInfos infos;
	
	public AbstractGraphicItem(RjWorld world, String name, Sprite sprite){
		infos = new GraphicItemInfos(name, sprite);
		this.world = world;
	}
}