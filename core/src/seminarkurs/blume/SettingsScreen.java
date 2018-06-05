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
 * Created by Nicole on 05.06.2018.
 */

public class SettingsScreen implements Screen{

    private MyGdxGame game;

    private Stage stage;

    private Skin uiSkin;

    private Button butBack;

    private TextButton button_sound;
    private TextButton button_music;

    float input_width;
    float input_height;

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
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
    }

    public SettingsScreen(final MyGdxGame game) {
        this.game = game;
        stage = game.getStage();

        uiSkin = game.getSkin();

        initBackButton();

        setupInterface();

        Gdx.input.setInputProcessor(stage);
    }

    private void setupInterface() {
        button_music = new TextButton("",uiSkin);
        button_music.setTransform(true);
        button_music.setScale(2f);
        button_music.setPosition(game.getScreenX() / 2 - button_music.getWidth(), game.getScreenY() * 0.6f);
        button_music.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                if(Settings.isMusicEnabled())
                    Settings.setMusicEnabled(false);
                else
                    Settings.setMusicEnabled(true);
                updateButtons();
                return true;
            }
        });
        stage.addActor(button_music);

        button_sound = new TextButton("",uiSkin);
        button_sound.setTransform(true);
        button_sound.setScale(2f);
        button_sound.setPosition(game.getScreenX() / 2 - button_sound.getWidth(), game.getScreenY() * 0.3f);
        button_sound.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                if(Settings.isSoundEnabled())
                    Settings.setSoundEnabled(false);
                else
                    Settings.setSoundEnabled(true);
                updateButtons();
                return true;
            }
        });
        stage.addActor(button_sound);

        updateButtons();
    }

    public void initBackButton()
    {
        butBack = new TextButton("<----", uiSkin);
        butBack.setTransform(true);
        butBack.setScale(2f);
        butBack.setPosition((int)(1920 * 0.01f), (int)(1080 * 0.85));
        butBack.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                LocalPlayer.setbIsSelecting(false);
                dispose();
                game.setScreen(new MainScreen(game));
                return true;
            }
        });
        stage.addActor(butBack);
    }

    private void updateButtons()
    {
        String sound_text = (Settings.isSoundEnabled()) ? "Disable Sound" : "Enable Sound";
        button_sound.setText(sound_text);
        String music_text = (Settings.isMusicEnabled()) ? "Disable Music" : "Enable Music";
        button_music.setText(music_text);
        MusicPlayer.updateVolume();
    }
}
