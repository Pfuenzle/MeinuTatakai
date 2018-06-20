package seminarkurs.blume;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by Nicole on 04.02.2018.
 */

public class CharSelectScreen implements Screen {
   // Deklaration
    private MyGdxGame game;

    private int next_screen;

    private Stage stage;
    private String name[] ={"Sakura","Yuki"};
    private Skin uiSkin;

    private int screen_width;
    private int screen_height;

    private Button butBack;

    private Label[] playerList;

    private BitmapFont font_titel;
    private BitmapFont font_text;

    ShapeRenderer shapeRenderer;

    OrthographicCamera cam;

    private final float VIRTUAL_WIDTH = 1920;
    private final float VIRTUAL_HEIGHT = 1080;

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {// Rendern
        stage.getBatch().setProjectionMatrix(cam.combined);
        stage.getBatch().begin();
        // switch bedingung für die ausgabe der figuren bilder
        switch(NetworkPlayer.getCharID()) {
            case 1:
                stage.getBatch().draw(Animation.texture_char1_still, VIRTUAL_WIDTH * 0.45f, VIRTUAL_HEIGHT * 0.1f);
                break;
            case 2:
                stage.getBatch().draw(Animation.texture_char2_still, VIRTUAL_WIDTH * 0.45f, VIRTUAL_HEIGHT * 0.1f);
                break;
        }
        // titel ausgabe
        font_titel.draw(stage.getBatch(), "Charakterauswahl",(int)(VIRTUAL_WIDTH * 0.25f), (int)(VIRTUAL_HEIGHT / 5 * 4.7));

        drawLabels();
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
    }// Löschen  nicht mehr benötigter Objekte

    public CharSelectScreen(MyGdxGame game, int screen)// Konstruktor
    {
        next_screen = screen;

        this.game = game;
        stage = game.getStage();

        uiSkin = game.getSkin();

        initBackButton();

        initFont();

        initLabels();

        cam = new OrthographicCamera();
        cam.setToOrtho(false, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);

        setupInterface();

        shapeRenderer = new ShapeRenderer();
        //nimmt größe des Bildschirms
        this.screen_width = Gdx.graphics.getWidth();
        this.screen_height = Gdx.graphics.getHeight();

        Gdx.input.setInputProcessor(stage);
        LocalPlayer.setbIsSelecting(true);
    }

    public void initFont() //Initialisiert die Schriftart
    {
        font_titel = game.getCharSelectFontTitle();
        font_titel.getData().setScale(2);
        font_text = game.getCharSelectFont();
    }

    void initLabels()
    {// Initialisierung der schriften
        playerList = new Label[6];
        for(int i = 0;i < name.length;i++)
        {
            font_text.setColor(Color.BLACK);
            Label.LabelStyle style = new Label.LabelStyle(font_text, Color.BLACK);
            font_text.setColor(Color.BLACK);
            style.font = font_text;
            final int temp = i;
            playerList[i] = new Label(name[i], style);
            playerList[i].setColor(Color.BLACK);
            playerList[i].setPosition((int)(VIRTUAL_WIDTH * 0.03f), (int)((VIRTUAL_HEIGHT) - 350) - (i * 120));
            playerList[i].addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                NetworkPlayer.setCharID(temp + 1);
            }
        });
            stage.addActor(playerList[i]);
        }
    }

    void drawLabels()//ausgabe der schriften
    {
        for(int i= 0; i < name.length; i++){
            playerList[i].draw(stage.getBatch(), 255);
        }
    }

    public void initBackButton()
    {// Back Button Initialisierung
        butBack = new TextButton("Back", uiSkin);
        butBack.setTransform(true);
        butBack.setScale(2f);
        butBack.setPosition((int)(VIRTUAL_WIDTH * 0.01f), (int)(VIRTUAL_HEIGHT * 0.85));
        butBack.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                LocalPlayer.setbIsSelecting(false);
                dispose();
                game.setScreen(new MainScreen(game));// neuen Screen öffen
                return true;
            }
        });
        stage.addActor(butBack);
    }

    public void setupInterface()
    {// continue Button Initialisierung
        TextButton button_weiter = new TextButton("continue",uiSkin);
        button_weiter.setTransform(true);
        button_weiter.setScale(2);
        button_weiter.setPosition((int)(VIRTUAL_WIDTH * 0.8f), (int)(VIRTUAL_HEIGHT*0.02f));
        button_weiter.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                dispose();
                game.setScreen(new MapSelectScreen(game, next_screen));// neuen Screen öffen
                return true;
            }
        });
        stage.addActor(button_weiter);

    }
}
