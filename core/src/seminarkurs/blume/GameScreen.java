package seminarkurs.blume;

/**
 * Created by Leon on 22.02.2018.
 */

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * Created by Leon on 04.02.2018.
 */

public class GameScreen implements Screen {

    private MyGdxGame game;

    private Stage stage;

    private Skin uiSkin;

    private GamePlayer gamePlayer;

    private boolean bMapError = false;

    private BitmapFont font_error = new BitmapFont();

    private int screen_width;
    private int screen_height;

    private Button butBack;

    private BitmapFont font_Name;


    private Touchpad touchpad;
    private Touchpad.TouchpadStyle touchpadStyle;
    private Skin touchpadSkin;
    private Drawable touchBackground;
    private Drawable touchKnob;
    private Texture blockTexture;
    private Sprite blockSprite;


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


        //Move blockSprite with TouchPad
        blockSprite.setX(blockSprite.getX() + touchpad.getKnobPercentX()*(int) gamePlayer.getSpeed());
        blockSprite.setY(blockSprite.getY() + touchpad.getKnobPercentY()*(int) gamePlayer.getSpeed());

        blockSprite.draw(stage.getBatch());


        stage.act(Gdx.graphics.getDeltaTime());


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

        gamePlayer = new GamePlayer();


        setupInterface();

        if(LocalPlayer.getMap() != null)
        {
            font_error.getData().setScale(5f);
            font_error.setColor(Color.GREEN);

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

        Gdx.app.log("Init", "davor");

        Gdx.input.setInputProcessor(stage);
    }

    private void initTouchpad() {
        //Create a touchpad skin
        touchpadSkin = new Skin();
        //Set background image
        touchpadSkin.add("touchBackground", new Texture("buttons/touchpad/touchBackground.png"));
        //Set knob image
        touchpadSkin.add("touchKnob", new Texture("buttons/touchpad/touchKnob.png"));
        //Create TouchPad Style
        touchpadStyle = new Touchpad.TouchpadStyle();
        //Create Drawable's from TouchPad skin
        touchBackground = touchpadSkin.getDrawable("touchBackground");
        touchKnob = touchpadSkin.getDrawable("touchKnob");
        //Apply the Drawables to the TouchPad Style
        touchpadStyle.background = touchBackground;
        touchpadStyle.knob = touchKnob;
        //Create new TouchPad with the created style
        touchpad = new Touchpad(10, touchpadStyle);
        //setBounds(x,y,width,height)
        touchpad.setBounds(15, 15, 200, 200);

        //Create a Stage and add TouchPad
        stage.addActor(touchpad);



        //Create block sprite
        blockTexture = new Texture(Gdx.files.internal("buttons/touchpad/block.png"));
        blockSprite = new Sprite(blockTexture);
        //Set position to centre of the screen
        blockSprite.setPosition(screen_width/2-blockSprite.getWidth()/2, screen_height/2-blockSprite.getHeight()/2);



        Gdx.input.setInputProcessor(stage);
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
        initPauseButton();
        initTouchpad();
    }

}
