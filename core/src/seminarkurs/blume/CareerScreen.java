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

public class CareerScreen implements Screen {


    MyGdxGame game;

    Stage stage;

    private int screen_width;
    private int screen_height;

    Button butBack;

    private BitmapFont font_Name;
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        game.stage.getBatch().begin();
        font_Name.draw(stage.getBatch(), NetworkPlayer.getUsername(), screen_width/2, (screen_height/4)*3);
        String RankPointsMsg = "Your RP: " + NetworkPlayer.getRP();
        font_Name.draw(stage.getBatch(), RankPointsMsg, screen_width/8*3, (screen_height/4)*2);
        String WinsMsg = "Your wins: " + NetworkPlayer.getWins();
        font_Name.draw(stage.getBatch(), WinsMsg, screen_width/4, (screen_height/4)*1);
        String LossesMsg = "Your losses: " + NetworkPlayer.getLosses();
        font_Name.draw(stage.getBatch(), LossesMsg, screen_width/2, (screen_height/4)*1);
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

    public CareerScreen(MyGdxGame game)
    {
        this.game = game;
        stage = game.stage;

        initBackButton();

        setupInterfaces();

        this.screen_width = Gdx.graphics.getWidth();
        this.screen_height = Gdx.graphics.getHeight();
    }

    private void setupInterfaces()
    {
        font_Name = new BitmapFont();
        font_Name.getData().setScale(5f);
        font_Name.setColor(Color.GREEN);
    }

    public void initBackButton()
    {
        butBack = new TextButton("<----", game.uiSkin);
        butBack.setTransform(true);
        butBack.setScale(2f);
        //butBack.setSize(input_width / 3, input_height);
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

}
