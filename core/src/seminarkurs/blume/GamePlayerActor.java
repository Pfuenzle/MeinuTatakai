package seminarkurs.blume;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by Flo on 30.03.2018.
 */

public class GamePlayerActor extends Actor{


    Animation<TextureAtlas.AtlasRegion> walkAnimation;
    Animation<TextureAtlas.AtlasRegion> jumpAnimation;
    Animation<TextureAtlas.AtlasRegion> kickAnimation;
    Animation<TextureAtlas.AtlasRegion> beatAnimation;
    Animation<TextureAtlas.AtlasRegion> ultimateAnimation;
    Animation<TextureAtlas.AtlasRegion> blockAnimation;

    TextureRegion textureRegion;
    GamePlayer gamePlayer;
    private final Stage stage;



    float drawX,drawY;
    int regionHight;
    int regionWidth;
    boolean facingRight;

    GamePlayerActor(GamePlayer gamePlayer) {
        super();
        this.gamePlayer = gamePlayer;
        this.stage = gamePlayer.getGamePlayerStage();
        loadAnimations();
    }

    void doWalking(boolean isRight){
        if(isRight) facingRight=true;
        else facingRight=false;
    }
    //Todo: make Kick
    void doKick(){
        if(facingRight){
            //Kick nach rechts
        }else{
            //Kick nach links
        }
    }
    void doJumping(){

    }


    @Override
    public void act(float delta) {
        float time =+ delta;


        textureRegion = walkAnimation.getKeyFrame(time, true);
        regionHight = textureRegion.getRegionY();
        regionWidth = textureRegion.getRegionX();
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        stage.getBatch().draw(textureRegion,drawX ,drawY-150);


    }

    private void loadAnimations() {
        TextureAtlas walkingAtlas = new TextureAtlas("player/" + gamePlayer.getSkin() + "/atlas/Walk.atlas");
        walkAnimation = new Animation<TextureAtlas.AtlasRegion>(1f / 2f, walkingAtlas.getRegions());

        //TextureAtlas jumpAtlas = new TextureAtlas("/player/" + gamePlayer.getSkin() + "/atlas/Walk.atlas");
        //jumpAnimation = new Animation<TextureAtlas.AtlasRegion>(1f / 2f, jumpAtlas.getRegions());


        TextureAtlas kickAtlas = new TextureAtlas("player/"+gamePlayer.getSkin()+"/atlas/Kick.atlas");
        kickAnimation = new Animation<TextureAtlas.AtlasRegion>(1f / 2f, kickAtlas.getRegions());

        //TextureAtlas beatAtlas = new TextureAtlas("/player/"+gamePlayer.getSkin()+"/atlas/Beat.atlas");
        //beatAnimation = new Animation<TextureAtlas.AtlasRegion>(1f / 2f, beatAtlas.getRegions());

        //TextureAtlas ultimateAtlas = new TextureAtlas("/player/"+gamePlayer.getSkin()+"/atlas/Ultimate.atlas");
        //ultimateAnimation = new Animation<TextureAtlas.AtlasRegion>(1f / 2f, ultimateAtlas.getRegions());

        //TextureAtlas blockAtlas = new TextureAtlas("/player/"+gamePlayer.getSkin()+"/atlas/Block.atlas");
        //blockAnimation = new Animation<TextureAtlas.AtlasRegion>(1f / 2f, blockAtlas.getRegions());


    }

    public int getRegionHight() {
        return regionHight;
    }

    public int getRegionWidth() {
        return regionWidth;
    }
}
