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
//  deklaration
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
    public void render(float delta) { // Methode zum rendern
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
    } // löschen nicht mehr benötigter Objekte

    public SettingsScreen(final MyGdxGame game) { // konstruktor
        this.game = game;
        stage = game.getStage();

        uiSkin = game.getSkin();

        initBackButton();

        setupInterface();

        Gdx.input.setInputProcessor(stage);
    }

    private void setupInterface() { // Buttons Initialisierung
        // Musik Buttom Initialisierung
        button_music = new TextButton("",uiSkin);
        button_music.setTransform(true);
        button_music.setScale(2f);
        button_music.setHeight(100);
        button_music.setWidth(200);
        button_music.setPosition(game.getScreenX() / 2 - button_music.getWidth(), game.getScreenY() * 0.6f);
        button_music.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                if(Settings.isMusicEnabled()) //Update Einstellungen
                    Settings.setMusicEnabled(false);
                else
                    Settings.setMusicEnabled(true);
                updateButtons();
                return true;
            }
        });
        stage.addActor(button_music);
        // Sound Button Initialisierung
        button_sound = new TextButton("",uiSkin);
        button_sound.setTransform(true);
        button_sound.setScale(2f);
        button_sound.setHeight(100);
        button_sound.setWidth(200);
        button_sound.setPosition(game.getScreenX() / 2 - button_sound.getWidth(), game.getScreenY() * 0.3f);
        button_sound.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                if(Settings.isSoundEnabled()) //Update Einstellungen
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
    { // Back Button Initialisierung
        butBack = new TextButton("Back", uiSkin);
        butBack.setTransform(true);
        butBack.setScale(2f);
        butBack.setPosition((int)(1920 * 0.01f), (int)(1080 * 0.85));
        butBack.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                dispose();
                game.setScreen(new MainScreen(game)); // neuen Screen aufrufen
                return true;
            }
        });
        stage.addActor(butBack);
    }

    private void updateButtons()
    {
        // Schrift auf den Button updaten
        String sound_text = (Settings.isSoundEnabled()) ? "Disable\nSound" : "Enable\nSound";
        button_sound.setText(sound_text);
        String music_text = (Settings.isMusicEnabled()) ? "Disable\nMusic" : "Enable\nMusic";
        button_music.setText(music_text);
        MusicPlayer.updateVolume();
    }
}
