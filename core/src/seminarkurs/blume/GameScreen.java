package seminarkurs.blume;

/**
 * Created by Leon on 22.02.2018.
 */

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Created by Leon on 04.02.2018.
 */

public class GameScreen implements Screen {

    private MyGdxGame game;

    private Stage stage;

    private Skin uiSkin;

    private boolean bMapError = false;

    private BitmapFont font_error = new BitmapFont();

    private int screen_width;
    private int screen_height;

    private Button butBack;

    private BitmapFont font_Name;
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        stage.getBatch().begin();
        stage.act();
        if(bMapError)
            font_error.draw(stage.getBatch(), "ERROR LOADING MAP!", screen_width/3, screen_height/20*18);
        else
            font_error.draw(stage.getBatch(), "DONE LOADING MAP " + LocalPlayer.getMap().getName(), screen_width/3, screen_height/20*18);
        stage.getBatch().end();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.clear();
    }

    public GameScreen(MyGdxGame game)
    {
        this.game = game;
        stage = game.getStage();

        uiSkin = game.getSkin();

        LocalPlayer.setbIsIngame(true);

        initPauseButton();

        if(LocalPlayer.getMap() != null)
        {
            font_error.getData().setScale(5f);
            font_error.setColor(Color.GREEN);
            setupInterface();
        }

        else
        {
            font_error.getData().setScale(5f);
            font_error.setColor(Color.RED);
            Gdx.app.log("GameScreen", "Error: Map is null");
            bMapError = true;
        }

        this.screen_width = Gdx.graphics.getWidth();
        this.screen_height = Gdx.graphics.getHeight();
    }

    public void initPauseButton()
    {
        butBack = new TextButton("||", uiSkin);
        butBack.setTransform(true);
        butBack.setScale(2f);
        butBack.setPosition(game.getScreenX()/25, game.getScreenY()/15*13);
        butBack.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                //dispose();
                //game.setScreen(new MainScreen(game));
                //Add pause-code
                return true;
            }
        });
        stage.addActor(butBack);
    }

    public void setupInterface()
    {

    }

}
