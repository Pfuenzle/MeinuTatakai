package seminarkurs.blume;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Leon on 22.02.2018.
 */

public class MapPinku extends Map {

    public MapPinku()
    {
        super();
        setName("Pinku");
        initBackground("maps/map_1.png");
        setScale(2);
    }

}
