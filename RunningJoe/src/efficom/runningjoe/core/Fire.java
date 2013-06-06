package efficom.runningjoe.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import efficom.runningjoe.RunningJoe;

/**
 *
 * @author Guillaume BAILLEUL
 */
public class Fire extends AbstractGraphicItem{
    static final int FRAME_WIDTH = 128;
    static final int FRAME_HEIGHT = 64;
    private static final int FRAME_COLS = 4;         // #1
    private static final int FRAME_ROWS = 4;

    private Animation animation;
    float stateTime;
    private TextureRegion[] animationFrames;

    /***
     * Class constructor
     * @param world
     */
    public Fire(RjWorld world){
        super(world, "Fire", GraphicItemType.FIRE);

        // Create the Texture
        this.animationFrames = createFrames(
                new Texture(Gdx.files.internal("images/fire.png")),
                FRAME_ROWS,
                FRAME_COLS,
                FRAME_WIDTH,
                FRAME_HEIGHT);

        TextureRegion region = animation.getKeyFrame(stateTime, true);
        LoadTexture(region, new Vector2(0,0));

    }

    /***
     * Calculate the texture position and draw it
     * @param spriteBatch The sprite batch
     */
    public void draw(SpriteBatch spriteBatch)
    {
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion region = animation.getKeyFrame(stateTime, true);
        tr.SetTextureRegion(region);

        this.tr.SetPosition(0, 0);

        // Draw the texture
        this.tr.Draw(spriteBatch);
    }

    /**
     * Load the textures into an array and create a animation object
     * @param texture   The texture to load
     * @param rows  The number of rows in the texture file
     * @param cols  The number of columns in the texture file
     * @param width The width of a frame in pixels
     * @param height The height of a frame in pixels
     * @return The array of frames
     */
    private TextureRegion[] createFrames(Texture texture, int rows, int cols, int width, int height)
    {
        TextureRegion[][] regions = TextureRegion.split(texture, width, height);
        TextureRegion[] frames = new TextureRegion[cols * rows];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                frames[index++] = regions[i][j];
            }
        }

        animation = new Animation(0.05f, frames);
        stateTime = 0.0f;

        return frames;
    }
}
