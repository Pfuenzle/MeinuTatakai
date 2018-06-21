package seminarkurs.blume;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by Nicole on 05.06.2018.
 */

public class MapSelectScreen implements Screen {
    //Variablen Deklaration
    private MyGdxGame game;

    private Stage stage;
    private String name[] ={"Pinku","Kiseki"}; // Array damit noch weitere maps hinzugefügt werden können
    private String map_name[] = {"map_1.png", "map_2.png"};
    private Sprite map_sprite[];
    private Skin uiSkin;

    private int screen_width;
    private int screen_height;

    private int current_selection;

    private int next_screen;

    private Button butBack;

    private Label[] mapList;

    private BitmapFont font_titel;
    private BitmapFont font_text;

    OrthographicCamera cam;

    private final float VIRTUAL_WIDTH = 1920;
    private final float VIRTUAL_HEIGHT = 1080;

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        stage.getBatch().setProjectionMatrix(cam.combined);

        stage.getBatch().begin();
        font_titel.draw(stage.getBatch(), "Mapauswahl",(int)(VIRTUAL_WIDTH * 0.25f), (int)(VIRTUAL_HEIGHT / 5 * 4.7)); //Text rendern
        map_sprite[NetworkPlayer.getMapID()].draw(stage.getBatch());// holt Id der Map
        drawLabels();
        stage.getBatch().end();
        stage.act();
        stage.draw();
    }

    void mapload(){ // Methode zum Maps Laden
        map_sprite = new Sprite[20];
        for(int i = 0; i < name.length; i++)
        {
            Texture tempTexture = new Texture("maps/" + map_name[i]);
            map_sprite[i] = new Sprite(tempTexture);
            map_sprite[i].setScale(0.7f);
            map_sprite[i].setOrigin((int)(VIRTUAL_WIDTH * 0.72f), (int)(VIRTUAL_HEIGHT * 0.12f));
        }
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
    } // Löschen nicht mehr benötigter Objekte

    public MapSelectScreen(MyGdxGame game, int screen) //Konstruktor
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
        //nimmt größe des Bildschirms
        this.screen_width = Gdx.graphics.getWidth();
        this.screen_height = Gdx.graphics.getHeight();

        mapload();

        Gdx.input.setInputProcessor(stage);
        LocalPlayer.setbIsSelecting(true);
    }

    public void initFont() // Initialisierung der Schriftart
    {
        font_titel = game.getCharSelectFontTitle();
        font_titel.getData().setScale(2);
        font_text = game.getCharSelectFont();
    }

    void initLabels()
    {// Initialisierung der schriften
        mapList = new Label[6];
        for(int i = 0;i < name.length;i++)
        {
            font_text.setColor(Color.BLACK);
            Label.LabelStyle style = new Label.LabelStyle(font_text, Color.BLACK);
            font_text.setColor(Color.BLACK);
            style.font = font_text;
            final int temp = i;
            mapList[i] = new Label(name[i], style);
            mapList[i].setColor(Color.BLACK);
            mapList[i].setPosition((int)(VIRTUAL_WIDTH * 0.03f), (int)((VIRTUAL_HEIGHT) - 350) - (i * 120));
            mapList[i].addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    current_selection = temp;
                    NetworkPlayer.setMapID(temp); //setzen der gewählten Map
                }
            });
            stage.addActor(mapList[i]);
        }
    }

    void drawLabels()//ausgabe der schriften
    {
        for(int i= 0; i < name.length; i++){
            mapList[i].draw(stage.getBatch(), 255);
        }
    }

    public void initBackButton() //Initialisierung backButton
    {
        butBack = new TextButton("Back", uiSkin); // Text im Button
        butBack.setTransform(true);
        butBack.setScale(2f); // größe
        butBack.setPosition((int)(VIRTUAL_WIDTH * 0.01f), (int)(VIRTUAL_HEIGHT * 0.85)); // Position
        butBack.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                LocalPlayer.setbIsSelecting(false);
                dispose();
                game.setScreen(new CharSelectScreen(game, next_screen)); // neuen Screen öffen
                return true;
            }
        });
        stage.addActor(butBack);
    }

    public void setupInterface()
    {//Initialisierung continue Button
        TextButton button_weiter = new TextButton("continue",uiSkin); // text Button Continue
        button_weiter.setTransform(true);
        button_weiter.setScale(2); // größe
        button_weiter.setPosition((int)(VIRTUAL_WIDTH * 0.8f), (int)(VIRTUAL_HEIGHT*0.02f)); // Position
        button_weiter.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                LocalPlayer.setMap(current_selection);
                LocalPlayer.setbIsSelecting(false);
                dispose();

                if(next_screen == 1)
                    game.setScreen(new MultiPlayerScreen(game));// neuen Screen aufrufen
                else
                    game.setScreen(new MainScreen(game));// neuen Screen aufrufen
                return true;
            }
        });
        stage.addActor(button_weiter);

    }
}
