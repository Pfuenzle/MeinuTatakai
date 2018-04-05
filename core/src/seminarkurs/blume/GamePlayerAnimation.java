package seminarkurs.blume;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Flo on 30.03.2018.
 */

public class GamePlayerAnimation extends Actor {
    TextureAtlas walkingAtlas;
    Animation<TextureAtlas.AtlasRegion> walkAnimation;
    TextureRegion currentRegion;

    GamePlayerAnimation() {
        walkingAtlas = new TextureAtlas("player/atlas/Walking.atlas");
        walkAnimation = new Animation<TextureAtlas.AtlasRegion>(1f / 2f, walkingAtlas.getRegions());
    }

    @Override
    public void act(float delta) {


        float time =+ delta;


        currentRegion = walkAnimation.getKeyFrame(time, true);
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(currentRegion, getX(), getY());
    }
}
