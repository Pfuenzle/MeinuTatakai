package seminarkurs.blume;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by Leon on 22.02.2018.
 */

public class Map {
    protected String mapName;

    BitmapFont font_error = new BitmapFont();
    private boolean bLoadMapError = false;


    public Texture backgroundTexture;
    public Sprite backgroundSprite;

    private int screen_width;
    private int screen_height;

    public String getName()
    {
        return mapName;
    }

    public void setName(String name)
    {
        mapName = name;
    }

    public void initBackground(String mapImage)
    {
        try
        {
            backgroundTexture = new Texture(mapImage);
            backgroundSprite = new Sprite(backgroundTexture);
            backgroundSprite.setScale(2f);
            float background_origin_x = 0;
            float background_origin_y = 0;
            backgroundSprite.setOrigin(background_origin_x, background_origin_y);
        }
        catch(Exception e)
        {
            bLoadMapError = true;
            Gdx.app.log("Map", "Error: " + e.toString());
        }

    }

    public void drawMap(Stage stage)
    {
        if(!bLoadMapError)
            backgroundSprite.draw(stage.getBatch());
        else
            font_error.draw(stage.getBatch(), "ERROR DRAWING MAP " + this.getName(), screen_width/3, screen_height/20*18);
    }

    public Map()
    {
        font_error.getData().setScale(5f);
        font_error.setColor(Color.RED);
        this.screen_width = Gdx.graphics.getWidth();
        this.screen_height = Gdx.graphics.getHeight();
    }
}
