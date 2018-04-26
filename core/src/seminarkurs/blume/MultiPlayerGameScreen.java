package seminarkurs.blume;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Leon on 27.02.2018.
 */

public class MultiPlayerGameScreen implements Screen {

    private MyGdxGame game;

    private MultiPlayer MP;

    private Stage stage;

    private Skin uiSkin;

    private int screen_width;
    private int screen_height;

    private Button butBack;

    private BitmapFont statusFont;

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        stage.getBatch().begin();
        game.font_yellow.draw(stage.getBatch(), "Server status: " + MP.getServerStatus(), screen_width/3, screen_height/20*16);
        game.font_yellow.draw(stage.getBatch(), "Enemy name: " + MP.getEnemy().getUsername() + " with " + MP.getEnemy().getRP() + " RP", screen_width/4, screen_height/8*5);
        game.font_yellow.draw(stage.getBatch(), "Enemy model: " + MP.getEnemy().getPlayer() + " with skin: " + MP.getEnemy().getSkin(), screen_width/4, screen_height/8*3);
        //statusFont.draw(stage.getBatch(), "Enemy name: ", screen_width/4, screen_height/4*3);
        stage.getBatch().end();
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

    public MultiPlayerGameScreen(MyGdxGame game, int PORT, String enemy) throws IOException, ClassNotFoundException {
        this.game = game;
        this.stage = game.getStage();

        this.uiSkin = game.getSkin();

        LocalPlayer.setbIsIngame(true);

        initPauseButton();

        this.screen_width = Gdx.graphics.getWidth();
        this.screen_height = Gdx.graphics.getHeight();

        MP = new MultiPlayer(PORT);

        statusFont = new BitmapFont();

        statusFont.getData().setScale(3f);
        statusFont.setColor(Color.GREEN);
        //statusFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
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
                dispose();
                game.setScreen(new MainScreen(game));
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
