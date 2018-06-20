package seminarkurs.blume;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Created by Nicole on 28.01.2018.
 */

public class MainScreen implements Screen {
// Deklaration
    private MyGdxGame game;

    private Stage stage;

    private Skin uiSkin;

    private Button butSingle;
    private Button butMulti;
    private Button butCareer;

    float input_width;
    float input_height;

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {// rendern
        stage.getBatch().begin();
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
    }// Löschen

    public MainScreen(final MyGdxGame game) {
        this.game = game;
        stage = game.getStage();

        uiSkin = game.getSkin();

        setupInterface();

        Gdx.input.setInputProcessor(stage);
    }

    private void setupInterface()
    {
        input_width = game.getScreenX()/3;
        input_height = game.getScreenY()/12;
        // Singelplayer Button
        butSingle = new TextButton("Singleplayer", uiSkin);
        butSingle.setTransform(true);
        butSingle.setScale(2f);
        butSingle.setSize(input_width / 3, input_height);
        butSingle.setPosition((game.getScreenX()/2)-butSingle.getWidth(), input_height * 7);
        butSingle.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                dispose();
                game.setScreen(new CharSelectScreen(game, 0));
                return true;
            }
        });
        stage.addActor(butSingle);
        // Multiplayer Button
        butMulti = new TextButton("Multiplayer", uiSkin);
        butMulti.setTransform(true);
        butMulti.setScale(2f);
        butMulti.setSize(input_width / 3, input_height);
        butMulti.setPosition((game.getScreenX()/2)-butMulti.getWidth(), input_height * 4.5f);
        butMulti.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                dispose();
                game.setScreen(new CharSelectScreen(game, 1));
                return true;
            }
        });
        stage.addActor(butMulti);
        // Career Button
        butCareer = new TextButton("CareerScreen", uiSkin);
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
        // Setting Button
        TextButton button = new TextButton("Settings",uiSkin);
        button.setScale(2f);
        button.setTransform(true);
        button.setPosition(game.getScreenX()*0.75f, game.getScreenY()*0.9f);
        button.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                dispose();
                game.setScreen(new SettingsScreen(game));
                return true;
            }
        });
        stage.addActor(button);

    }

}
