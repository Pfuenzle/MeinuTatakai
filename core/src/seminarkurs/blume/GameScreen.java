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
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;


/**
 * Created by Leon on 04.02.2018.
 */

public abstract class GameScreen implements Screen {
    /*, ContactListener {

    private MyGdxGame game;

    private Stage stage;

    private Skin uiSkin;


    private boolean bMapError = false;

    private BitmapFont font_error = new BitmapFont();

    private int screen_width;
    private int screen_height;
    private float world_width;

    GameUi ui;


    Box2DDebugRenderer b2dr;
    OrthographicCamera camera;
    Matrix4 debugMatrix;

    World world;
    GamePlayer player;
    GamePlayer enemy;
    Body ground,leftWall,rightWall;
    float pixelToMeter = 0.0052f;

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
        loadButListeners();
        //Todo: Add Multiplayer Names

        camera = new OrthographicCamera();
        camera.setToOrtho(false, screen_width*0.0052f,screen_height*0.0052f);

        debugMatrix = new Matrix4(camera.combined);


        Box2D.init();
        world = new World(new Vector2(0f,-9.8f),false);
        world_width = screen_width*pixelToMeter;
        world.setContactListener(this);

        b2dr = new Box2DDebugRenderer();

        player = new GamePlayer(stage, world,  "normal", false);
        stage.addActor(player);


        enemy = new GamePlayer(stage, world, "katze",true);
        stage.addActor(enemy);
        //Todo: Enemy von MP steuern lassen

        ground = new GameBodies(world,world_width).createGround();
        leftWall = new GameBodies(world,world_width).createWall(false);
        rightWall = new GameBodies(world,  world_width).createWall(true);


        Gdx.input.setInputProcessor(stage);
    }

    private void loadButListeners() {
        ui.butKick.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                player.doKick();

                return true;
            }
        });

        ui.butBeat.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                player.doBeat();

                return true;
            }
        });
    }


    @Override
    public void render(float delta) {
        update(Gdx.graphics.getDeltaTime());

        stage.getBatch().begin();


        /*if (bMapError)
            font_error.draw(stage.getBatch(), "ERROR LOADING MAP!", screen_width / 3, screen_height / 20 * 18);
        else
            font_error.draw(stage.getBatch(), "DONE LOADING MAP " + LocalPlayer.getMap().getName(), screen_width / 3, screen_height / 20 * 18);
        */
/*
        player.draw(stage.getBatch(),1);
        enemy.draw(stage.getBatch(),1);

        stage.getBatch().end();
        stage.draw();

        b2dr.render(world,debugMatrix);
    }

    private void update(float delta) {
        stage.act(delta);

        world.step(Gdx.graphics.getDeltaTime(),6,2);

        updateMovement();

        updateCollision();
        //Anzeigen aktualisieren
        ui.healthBar.setValue((float) player.getHealth());
        ui.healthBarEnemy.setValue((float)enemy.getHealth());

        //Camerafokus auf Spieler
        //cameraUpdate(delta);
    }

    private void updateCollision() {
        if(player.kick){
            enemy.setHealth(enemy.getHealth()-80);
            player.kick = false;
        }else if(player.beat){
            enemy.setHealth(enemy.getHealth()-50);
            player.beat = false;
        }

    }

    private void cameraUpdate(float delta) {
        Vector3 position = camera.position;
        position.x =  player.getX();
        position.y =  player.getY();
        camera.position.set(position);
        camera.update();
    }
    private void updateMovement(){
        //Move playerSprite with TouchPad
        if (ui.touchpad.getKnobPercentX() > 0 && ui.touchpad.getKnobPercentY()<0.7) {
            //Rechts laufen
            player.doWalking(true, ui.touchpad.getKnobPercentX());
        } else if (ui.touchpad.getKnobPercentX() < 0 && ui.touchpad.getKnobPercentY()<0.7) {
            //Links laufen
            player.doWalking(false, ui.touchpad.getKnobPercentX());
        }
        if (0.7 < ui.touchpad.getKnobPercentY()) {
            player.doJumping();
        }
        if(ui.touchpad.getKnobPercentX()==0 && ui.touchpad.getKnobPercentY()==0) {
            player.doNotWalk();
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

    @Override
    public void beginContact(Contact contact) {
        if(contact.getFixtureA().getBody().getUserData()=="Player" && contact.getFixtureB().getBody().getUserData()=="Enemy"){
            System.out.println("Player hittable");
            player.setCanHitEnemy(true);
        }else if (contact.getFixtureA().getBody().getUserData()=="Player" && contact.getFixtureB().getUserData()=="Ground"){
            player.canJump = true;

        }
    }

    @Override
    public void endContact(Contact contact) {
        if(contact.getFixtureA().getBody().getUserData()=="Player" && contact.getFixtureB().getBody().getUserData()=="Enemy") {
            System.out.println("Player away");
            player.setCanHitEnemy(false);
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
*/
}