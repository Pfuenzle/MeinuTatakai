package seminarkurs.blume;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by Leon on 25.02.2018.
 */

public class PopUp {
    private Stage stage;

    private Texture back_text;
    private Rectangle rect;

    public PopUp(Stage stage, String text)
    {
        this.stage = stage;

        rect = new Rectangle();
        rect.x = 350;
        rect.y = 350;
        rect.setSize(350,350);

        back_text = new Texture("background/map_castle.png");
    }

    public void dispose()
    {

    }

    public void render()
    {
        stage.getBatch().draw(back_text, rect.x, rect.y, rect.width, rect.height);
    }
}
