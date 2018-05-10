package seminarkurs.blume;

/**
 * Created by Leon on 22.02.2018.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;



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


    private ProgressBar healthBar;

    GameUi ui;
    private GamePlayerActor playerActor;

    Box2DDebugRenderer b2dr;
    OrthographicCamera camera;
    Matrix4 debugMatrix;

    World world;
    GamePlayer player, enemy;
    Body ground;
    float PixelInMeter = 0.0025f;

    public GameScreen(MyGdxGame game) {
        this.game = game;
        stage = game.getStage();
        uiSkin = game.getSkin();

        LocalPlayer.setbIsIngame(true);

        this.screen_width = Gdx.graphics.getWidth();
        this.screen_height = Gdx.graphics.getHeight();

        if (LocalPlayer.getMap() != null) {
            font_error.getData().setScale(5f);
            font_error.setColor(Color.GREEN);

        } else {
            font_error.getData().setScale(5f);
            font_error.setColor(Color.RED);
            Gdx.app.log("GameScreen", "Error: Map is null");
            bMapError = true;
        }

        ui = new GameUi(game);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, screen_width,screen_height);

        debugMatrix = new Matrix4(camera.combined);


        Box2D.init();
        world = new World(new Vector2(0f,-9.8f),false);
        b2dr = new Box2DDebugRenderer();

        player = new GamePlayer(stage, world,"normal");
        enemy = new GamePlayer(stage, world,"normal");

        ground = new GameBodies(world).createGround();






        Gdx.input.setInputProcessor(stage);
    }


    @Override
    public void render(float delta) {
        update(Gdx.graphics.getDeltaTime());


        stage.getBatch().begin();
        player.act(delta);
        stage.act();
        if (bMapError)
            font_error.draw(stage.getBatch(), "ERROR LOADING MAP!", screen_width / 3, screen_height / 20 * 18);
        else
            font_error.draw(stage.getBatch(), "DONE LOADING MAP " + LocalPlayer.getMap().getName(), screen_width / 3, screen_height / 20 * 18);

        stage.addActor(player.actor);
        stage.act(Gdx.graphics.getDeltaTime());


        stage.getBatch().end();
        stage.draw();

        b2dr.render(world,debugMatrix);
    }

    private void update(float delta) {
        updateMovement(delta);




        //Anzeigen aktualisieren
        ui.healthBar.setValue((float) player.getHealth());

        world.step(1/60f,6,2);

        //Camerafokus auf Spieler
        cameraUpdate(delta);
    }

    private void cameraUpdate(float delta) {
        Vector3 position = camera.position;
        position.x = (float) player.getPosX();
        position.y = (float) player.getPosY();
        camera.position.set(position);
        camera.update();
    }

    private void updateMovement(float delta){
        //Move playerSprite with TouchPad
        if (ui.touchpad.getKnobPercentX() > 0) {
            //Rechts laufen
            //playerActor.setFlip(false, false);
            //playerActor.setX(playerActor.getX() + ui.touchpad.getKnobPercentX() * (int) gamePlayer.getSpeed() * delta);
            //player.applyLinearImpulse(new Vector2(0,4), player.getPosition(),true);

            player.doWalking(true, ui.touchpad.getKnobPercentX());



        } else if (ui.touchpad.getKnobPercentX() < 0) {
            //Links laufen
            //playerActor.setFlip(true, false);
            //playerActor.setX(playerActor.getX() + ui.touchpad.getKnobPercentX() * (int) gamePlayer.getSpeed() * delta);
            player.doWalking(false, ui.touchpad.getKnobPercentX());
        }
        if (0.7 < ui.touchpad.getKnobPercentY()) {
            //springen
            //playerActor.setY(playerActor.getY() + ui.touchpad.getKnobPercentY() * (int) gamePlayer.getSpeed() * delta);
            player.doJumping();
        } else if (-0.7 > ui.touchpad.getKnobPercentY()) {
            //ducken
        }
        if(ui.touchpad.getKnobPercentX()==0 && ui.touchpad.getKnobPercentY()==0) {
            //Stehen bleiben
            player.doNothing();
        }
    }







    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, screen_width, screen_height);
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
        world.dispose();
        b2dr.dispose();
        stage.clear();
    }
}
