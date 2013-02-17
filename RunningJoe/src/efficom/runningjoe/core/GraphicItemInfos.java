package efficom.runningjoe.core;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class GraphicItemInfos {
	Sprite sprite;
	String name;
	
	public GraphicItemInfos(String name, Sprite sprite)
	{
		this.sprite = sprite;
		this.name = name;		
	}
	
	public Texture getTexture() {
		return sprite.getTexture();
	}

	public void setTexture(Texture texture) {
		this.sprite.setTexture(texture);
	}	
	
	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

}
