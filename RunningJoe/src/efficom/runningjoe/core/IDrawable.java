package efficom.runningjoe.core;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/***
 *
 * @author Guillaume BAILLEUL
 */
public interface IDrawable {
    public void draw(SpriteBatch spriteBatch);
    public void LoadTexture(TextureRegion region, Vector2 pos);
}
