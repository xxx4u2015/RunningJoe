package efficom.runningjoe.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox.SelectBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import efficom.runningjoe.services.LanguagesManager;

import efficom.runningjoe.RunningJoe;

/**
 * The base class for all game screens.
 */
public abstract class AbstractScreen implements Screen
{
    // the fixed viewport dimensions (ratio: 1.6)    
    protected final RunningJoe game;
    protected final TextButtonStyle buttonStyle; 
    protected final Stage stage;
    protected final LabelStyle labelStyle;
    protected final TextFieldStyle textFieldStyle;
    protected final SelectBoxStyle selectBoxStyle;
    
    private LanguagesManager languageManager;    
    private Table table;
    private BitmapFont font;
    private SpriteBatch batch;
    private Skin skin;
    private TextureAtlas atlas;
    private InputMultiplexer inputMultiplexer;

    public AbstractScreen(RunningJoe game )
    {    	
        this.game = game;
        // Language manager
        this.languageManager = LanguagesManager.getInstance();
        
        this.stage = new Stage(RunningJoe.GAME_VIEWPORT_WIDTH, RunningJoe.GAME_VIEWPORT_HEIGHT, true);
        //inputMultiplexer = new InputMultiplexer(stage);
        Gdx.input.setInputProcessor(stage);
                
        //Button style
        this.buttonStyle = new TextButtonStyle();
        this.buttonStyle.font = new BitmapFont();
        this.buttonStyle.fontColor = Color.WHITE;
        this.buttonStyle.pressedOffsetY = 1f;
        this.buttonStyle.downFontColor = new Color(0.8f, 0.8f, 0.8f, 1f);
        
        //Label style        
        labelStyle = new LabelStyle();
        labelStyle.font = new BitmapFont();
        labelStyle.fontColor = Color.WHITE;
        
        //Text field style
        textFieldStyle = new TextFieldStyle();
        textFieldStyle.font = new BitmapFont();
        textFieldStyle.fontColor = Color.WHITE;
        
        // Select Box style
        this.selectBoxStyle = new SelectBoxStyle();
        this.selectBoxStyle.font = new BitmapFont();
        this.selectBoxStyle.fontColor = Color.WHITE;
        
        //Creation du skin
        this.skin = new Skin();
        skin.add("default", labelStyle);
        
    }
    
    protected InputMultiplexer getInputMultiplexer()
    {
    	return this.inputMultiplexer;
    }
    
    protected LanguagesManager getLanguagesManager()
    {
    	return this.languageManager;
    }

    protected String getName()
    {
        return getClass().getSimpleName();
    }

    protected boolean isGameScreen()
    {
        return false;
    }

    // Lazily loaded collaborators

    public BitmapFont getFont()
    {
        if( font == null ) {
            font = new BitmapFont();
        }
        return font;
    }

    public SpriteBatch getBatch()
    {
        if( batch == null ) {
            batch = new SpriteBatch();
        }
        return batch;
    }

    public TextureAtlas getAtlas()
    {
        if( atlas == null ) {
            atlas = new TextureAtlas( Gdx.files.internal( "image-atlases/pages.atlas" ) );
        }
        return atlas;
    }

    protected Skin getSkin()
    {
        if( skin == null ) {
            FileHandle skinFile = Gdx.files.internal( "skin/uiskin.json" );
            skin = new Skin( skinFile );
        }
        return skin;
    }
    
    protected Table getTable()
    {
        if( table == null ) {
            table = new Table();
            table.setSkin(this.getSkin());
            table.setFillParent( true );
            if( RunningJoe.DEV_MODE ) {
                table.debug();
            }
            stage.addActor( table );
        }
        return table;
    }

    // Screen implementation

    @Override
    public void show()
    {
        Gdx.app.log( RunningJoe.LOG, "Showing screen: " + getName() );
    }

    @Override
    public void resize(int width, int height )
    {
        Gdx.app.log( RunningJoe.LOG, "Resizing screen: " + getName() + " to: " + width + " x " + height );
    }

    @Override
    public void render(float delta)
    {       
    	stage.act( delta );
        
        // draw the actors
        stage.draw();

        // draw the table debug lines
        if(RunningJoe.DEV_MODE )Table.drawDebug( stage );
    }

    @Override
    public void hide()
    {
        Gdx.app.log( RunningJoe.LOG, "Hiding screen: " + getName() );

        // dispose the screen when leaving the screen;
        // note that the dipose() method is not called automatically by the
        // framework, so we must figure out when it's appropriate to call it
        dispose();
    }

    @Override
    public void pause()
    {
        Gdx.app.log( RunningJoe.LOG, "Pausing screen: " + getName() );
    }

    @Override
    public void resume()
    {
        Gdx.app.log( RunningJoe.LOG, "Resuming screen: " + getName() );
    }

    @Override
    public void dispose()
    {
        Gdx.app.log( RunningJoe.LOG, "Disposing screen: " + getName() );

        // as the collaborators are lazily loaded, they may be null
        if( font != null ) font.dispose();
        if( batch != null ) batch.dispose();
        if( skin != null ) skin.dispose();
        if( atlas != null ) atlas.dispose();
    }
}