package seminarkurs.blume;

/**
 * Created by Leon on 22.02.2018.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;


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

    private Touchpad touchpad;
    private ProgressBar healthBar;

    GameUi ui;
    private GamePlayerAnimation animationActor;

    public GameScreen(MyGdxGame game) {
        this.game = game;
        stage = game.getStage();
        gamePlayer = new GamePlayer();

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

        setupInterface();



        Box2D.init();


        animationActor = new GamePlayerAnimation();

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        stage.getBatch().begin();
        stage.act();
        if (bMapError)
            font_error.draw(stage.getBatch(), "ERROR LOADING MAP!", screen_width / 3, screen_height / 20 * 18);
        else
            font_error.draw(stage.getBatch(), "DONE LOADING MAP " + LocalPlayer.getMap().getName(), screen_width / 3, screen_height / 20 * 18);

        renderMovement(delta);

        //Border down
        if (animationActor.getY() < 50)
            animationActor.setY(51);

        if (animationActor.getX() < 0)
            animationActor.setX(1);
        else if (animationActor.getX() > screen_width - animationActor.getWidth())
            animationActor.setX(screen_width - animationActor.getWidth());


        //Anzeigen aktualisieren
        healthBar.setValue((float) gamePlayer.getHealth());


       // playerSprite.draw(stage.getBatch());
       // enemySprite.draw(stage.getBatch());
        ui.initEnemy().draw(stage.getBatch());
        stage.addActor(animationActor);
        animationActor.act(delta);
        animationActor.draw(stage.getBatch(),0);

        stage.act(Gdx.graphics.getDeltaTime());

        stage.getBatch().end();
        stage.draw();
    }

    private void renderMovement(float delta){
        //Move playerSprite with TouchPad
        if (touchpad.getKnobPercentX() > 0) {
            //Rechts laufen
           // animationActor.setFlip(false, false);
            animationActor.setX(animationActor.getX() + touchpad.getKnobPercentX() * (int) gamePlayer.getSpeed() * delta);
        } else if (touchpad.getKnobPercentX() < 0) {
            //Links laufen
         //   animationActor.setFlip(true, false);
            animationActor.setX(animationActor.getX() + touchpad.getKnobPercentX() * (int) gamePlayer.getSpeed() * delta);
        }
        if (0.7 < touchpad.getKnobPercentY()) {
            //springen

            animationActor.setY(animationActor.getY() + touchpad.getKnobPercentY() * (int) gamePlayer.getSpeed() * delta);
        } else if (-0.7 > touchpad.getKnobPercentY()) {
            //ducken
            animationActor.setY(animationActor.getY() + touchpad.getKnobPercentY() * (int) gamePlayer.getSpeed() * delta);
        }
    }


    private void setupInterface() {
        touchpad = ui.initTouchpad();
        stage.addActor(touchpad);


        healthBar = ui.initHealthBar(500,1000);
        stage.addActor(healthBar);

        stage.addActor(ui.initAttackButton());
        stage.addActor(ui.initPauseButton());

    }




    @Override
    public void show() {

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
}
