package efficom.runningjoe.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.BodyDef;
import efficom.runningjoe.RunningJoe;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * A list of RjBlock
 *
 * @author Guillaume BAILLEUL
 */
public class RjBlockList implements IRenderable{
    /**
     * List of @linkRjBlock objects witch represents the ground
     */
    private LinkedList<RjBlock> groundBlocks;
    private RjWorld world;
    private int blockCounter = 0;

    public RjBlockList(RjWorld world)
    {
        this.world = world;
        this.groundBlocks = new LinkedList<RjBlock>();
    }

    private void generateGroud() {
        float camPos = this.world.getCamera().viewportWidth / 2 + this.world.getCamera().position.x;
        float lastBlockX = this.groundBlocks.size() == 0 ? 0 : this.groundBlocks.getLast().getPosition().x;

        while (this.groundBlocks.size() == 0 || lastBlockX < (camPos + 100) ){
            float posX = 0;
            // Find the position of the previous bloc
            if (this.groundBlocks.size() != 0) {
                RjBlock body = this.groundBlocks.get(this.groundBlocks.size() - 1);
                posX = RunningJoe.MeterToPix(body.getPosition().x)	+ RjBlock.BLOCK_WIDTH * 2;
            }

            // Create the new block
            RjBlock lastBlock = getLastBlock() ;
            int indexBlock = blockCounter++;
            RjBlock groundBody = new RjBlock(this.world, "Floor "+ posX, indexBlock);
            groundBody.generateRandomBlock(posX);
            //Gdx.app.log(RunningJoe.LOG, "Created block: " + posX + " number " + indexBlock );

            this.groundBlocks.addLast(groundBody);
            lastBlockX = this.groundBlocks.getLast().getPosition().x;

        }

        this.fallingGround();

        if (this.groundBlocks.size() > 1000)
            Gdx.app.log(RunningJoe.LOG, "Too much ground blocks: " + this.groundBlocks.size());
    }

    public RjBlock getLastBlock(){
        try {
            return this.groundBlocks.getLast();
        }
        catch(Exception e){
            return null;
        }
    }

    /**
     * LET FALL FIRST BLOCKS
     *
     * @return void
     */
    public void fallingGround() {
        // DELETE OUT OF RANGE BLOCKS
        if (this.groundBlocks.size() > 0) {
            Iterator<RjBlock> i = this.groundBlocks.iterator();
            while (i.hasNext()) {
                RjBlock bloc = i.next();
                if (bloc.getPosition().x < (this.world.getCamera().position.x - (this.world.getCamera().viewportWidth / 1.25))) {
                    this.world.getWorld().destroyBody(bloc.body);
                    i.remove();
                } else if (bloc.getPosition().x < (this.world.getCamera().position.x - (this.world.getCamera().viewportWidth * 0.35f))) {
                    // bloc.s
                    bloc.body.setType(BodyDef.BodyType.DynamicBody);
                }
            }
        }
    }

    public void render()
    {
        generateGroud();
    }

    public void draw(SpriteBatch spriteBatch){
        Iterator<RjBlock> bi = groundBlocks.iterator();
        while(bi.hasNext()){
            RjBlock block = bi.next();
            block.draw(spriteBatch);
        }
    }


}
