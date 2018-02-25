package seminarkurs.blume;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import java.awt.Font;

/**
 * Created by Leon on 01.02.2018.
 */

public class MultiplayerScreen implements Screen {


    MyGdxGame game;

    Stage stage;

    private int screen_width;
    private int screen_height;

    Button butBack;
    Button butRank;
    Button butSearch;


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        game.stage.getBatch().begin();
        game.stage.getBatch().end();
        stage.act();
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

    public MultiplayerScreen(MyGdxGame game)
    {
        this.game = game;
        stage = game.stage;

        initBackButton();

        setupInterface();

        this.screen_width = Gdx.graphics.getWidth();
        this.screen_height = Gdx.graphics.getHeight();
    }

    public void initBackButton()
    {
        butBack = new TextButton("<----", game.uiSkin);
        butBack.setTransform(true);
        butBack.setScale(2f);
        butBack.setPosition(game.getScreenX()/25, game.getScreenY()/15*13);
        butBack.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                dispose();
                game.setScreen(new MainScreen(game));
                return true;
            }
        });
        stage.addActor(butBack);
    }

    private void setupInterface()
    {
        float input_width = game.getScreenX() / 3;
        float input_height = game.getScreenY() / 12;

        butRank = new TextButton("Play ranked game", game.uiSkin);
        butRank.setTransform(true);
        butRank.setScale(2f);
        butRank.setSize(input_width / 3, input_height);
        butRank.setPosition((game.getScreenX() / 2) - butRank.getWidth(), input_height * 7);
        butRank.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(butRank);

        butSearch = new TextButton("Search player", game.uiSkin);
        butSearch.setTransform(true);
        butSearch.setScale(2f);
        butSearch.setSize(input_width / 3, input_height);
        butSearch.setPosition((game.getScreenX() / 2) - butSearch.getWidth(), input_height * 4.5f);
        butSearch.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                dispose();
                game.setScreen(new MultiplayerScreen(game));
                return true;
            }
        });
        stage.addActor(butSearch);
    }

}
