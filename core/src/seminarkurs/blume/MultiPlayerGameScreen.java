package seminarkurs.blume;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;

import java.io.IOException;

/**
 * Created by Leon on 27.02.2018.
 */

public class MultiPlayerGameScreen  extends ApplicationAdapter implements Screen{

    private MyGdxGame game;

    private MultiPlayer MP;

    private Stage stage;

    private Skin uiSkin;

    private int screen_width;
    private int screen_height;

    private Button butBack;
    private Button butLeft;
    private Button butRight;

    private BitmapFont statusFont;



    Animation runningAnimation;
    float animationDuration = 0.115f;
    float stateTime;
    SpriteBatch sBatch;
    // Frame that must be rendered at each time
    private TextureRegion currentFrame;
    private float elapsed_time = 0f;
    TextureAtlas atlas;

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        elapsed_time += delta;
        currentFrame = (TextureRegion) runningAnimation.getKeyFrame(elapsed_time);

        stage.getBatch().begin();
        stage.getBatch().draw(currentFrame, (float)MP.getEnemy().getX(), (float)MP.getEnemy().getY(), 600, 600);
        game.font_yellow.draw(stage.getBatch(), "Server status: " + MP.getServerStatus(), screen_width/3, screen_height/20*16);
        game.font_yellow.draw(stage.getBatch(), "Your X: " + MP.getLocalPlayer().getX() + " Y: " + MP.getLocalPlayer().getY(), screen_width/3, screen_height/20*13);
        game.font_yellow.draw(stage.getBatch(), "Enemy X: " + MP.getEnemy().getX() + " Y: " + MP.getEnemy().getY(), screen_width/3, screen_height/20*10);
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
    public void create()
    {
        this.stage = game.getStage();

        this.uiSkin = game.getSkin();

        this.screen_width = Gdx.graphics.getWidth();
        this.screen_height = Gdx.graphics.getHeight();

        LocalPlayer.setbIsIngame(true);

        initPauseButton();

        setupInterface();

        statusFont = new BitmapFont();

        statusFont.getData().setScale(3f);
        statusFont.setColor(Color.GREEN);
        //statusFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);



        //Animation
        atlas = new TextureAtlas(Gdx.files.internal("tritt/tritt.atlas"));
        Array<TextureAtlas.AtlasRegion> runningFrames = atlas.getRegions();

        runningAnimation = new Animation(animationDuration, runningFrames);
        stateTime = 10f;
    }

    @Override
    public void dispose() {
        stage.clear();
    }

    public MultiPlayerGameScreen(MyGdxGame game, int PORT) throws IOException, ClassNotFoundException {
        this.game = game;
        MP = new MultiPlayer(PORT);
        create();
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
        butLeft = new TextButton("<--", uiSkin);
        butLeft.setTransform(true);
        butLeft.setScale(2f);
        //butLeft.setSize(screen_width / 3, screen_height);
        butLeft.setPosition(screen_width / 12, screen_height / 9);
        butLeft.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                try {
                    MP.moveLeft(100);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return true;
            }
        });
        stage.addActor(butLeft);

        butRight = new TextButton("-->", uiSkin);
        butRight.setTransform(true);
        butRight.setScale(2f);
        //butRight.setSize(screen_width / 3, screen_height);
        butRight.setPosition(screen_width / 12 * 3, screen_height / 9);
        butRight.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                try {
                    MP.moveRight(100);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return true;
            }
        });
        stage.addActor(butRight);
    }
}
