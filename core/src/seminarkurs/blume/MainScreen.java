package seminarkurs.blume;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Created by Leon on 28.01.2018.
 */

public class MainScreen implements Screen {

    MyGdxGame game;

    Stage stage;

    Screen gThis;

    Button butSingle;
    Button butMulti;
    Button butCareer;

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

    public MainScreen(final MyGdxGame game) {
        this.game = game;
        stage = game.stage;

        gThis = this;

        game.sNewScreen = this;

        setupInterface();

        Gdx.input.setInputProcessor(stage);
    }

    private void setupInterface()
    {
        float input_width = game.getScreenX()/3;
        float input_height = game.getScreenY()/12;

        butSingle = new TextButton("Singleplayer", game.uiSkin);
        butSingle.setTransform(true);
        butSingle.setScale(2f);
        butSingle.setSize(input_width / 3, input_height);
        butSingle.setPosition((game.getScreenX()/2)-butSingle.getWidth(), input_height * 7);
        butSingle.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                dispose();
                LocalPlayer.setMap(new MapCastle());
                game.setScreen(new /*CharSelectScreen(game)*/GameScreen(game));
                return true;
            }
        });
        stage.addActor(butSingle);

        butMulti = new TextButton("Multiplayer", game.uiSkin);
        butMulti.setTransform(true);
        butMulti.setScale(2f);
        butMulti.setSize(input_width / 3, input_height);
        butMulti.setPosition((game.getScreenX()/2)-butMulti.getWidth(), input_height * 4.5f);
        butMulti.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                dispose();
                game.setScreen(new MultiplayerScreen(game));
                return true;
            }
        });
        stage.addActor(butMulti);

        butCareer = new TextButton("CareerScreen", game.uiSkin);
        butCareer.setTransform(true);
        butCareer.setScale(2f);
        butCareer.setSize(input_width / 3, input_height);
        butCareer.setPosition((game.getScreenX()/2)-butCareer.getWidth(), input_height * 2);
        butCareer.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                dispose();
                game.setScreen(new CareerScreen(game));
                return true;
            }
        });
        stage.addActor(butCareer);
    }

}
