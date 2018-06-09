package seminarkurs.blume;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * Created by Flo on 30.03.2018.
 */

public class GameUi {
    private final MyGdxGame game;
    private final Skin uiSkin;
    Stage stage;

    ProgressBar healthBar, healthBarEnemy;
    Touchpad touchpad;
    TextButton butKick,butBeat;
    Button butBack;


    GameUi(MyGdxGame game) {
        this.game = game;
        this.stage = game.getStage();
        this.uiSkin = game.getSkin();

        setupInterface();

    }

    void setupInterface() {
        stage.addActor(initKickButton());
        stage.addActor(initBeatButton());

        stage.addActor(initHealthBar());
        stage.addActor(initEnemyHealthBar());
        stage.addActor(initPauseButton());

        stage.addActor(initTouchpad());
    }

    private TextButton initKickButton() {
        butKick = new TextButton("Kick",uiSkin);
        butKick.setTransform(true);
        butKick.setScale(2f);
        butKick.setPosition(game.getScreenX() / 25 * 18, game.getScreenY() / 15 * 2);

        return butKick;
    }
    private TextButton initBeatButton() {
        butBeat = new TextButton("Beat",uiSkin);
        butBeat.setTransform(true);
        butBeat.setScale(2f);
        butBeat.setPosition(game.getScreenX() / 25 * 21, game.getScreenY() / 15 * 4);

        return butBeat;
    }

    private ProgressBar initHealthBar() {
        int width= 300;
        healthBar = new ProgressBar(0, 1000, 10, false, uiSkin);
        healthBar.setPosition(game.getScreenX()/4-width/2, game.getScreenY()/25*23);
        healthBar.setScale(1);
        healthBar.setSize(width,20);


        return healthBar;
    }

    private ProgressBar initEnemyHealthBar(){
        int width = 300;
        healthBarEnemy = new ProgressBar(0, 1000, 10, false, uiSkin);
        healthBarEnemy.setPosition(game.getScreenX()/4*3-width/2, game.getScreenY()/25*23);
        healthBarEnemy.setScale(1);
        healthBarEnemy.setSize(width,20);
        healthBarEnemy.setAnimateDuration(1f);

        return healthBarEnemy;
    }

    public Touchpad initTouchpad() {
        Touchpad.TouchpadStyle touchpadStyle;
        Skin touchpadSkin;
        Drawable touchBackground;
        Drawable touchKnob;//Create a touchpad skin
        touchpadSkin = new Skin();
        //Set background image
        touchpadSkin.add("touchBackground", new Texture("buttons/touchpad/circle.png"));
        //Set knob image
        touchpadSkin.add("touchKnob", new Texture("buttons/touchpad/dot.png"));
        //Create TouchPad Style
        touchpadStyle = new Touchpad.TouchpadStyle();
        //Create Drawable's from TouchPad skin
        touchBackground = touchpadSkin.getDrawable("touchBackground");
        touchKnob = touchpadSkin.getDrawable("touchKnob");
        //Apply the Drawables to the TouchPad Style
        touchpadStyle.background = touchBackground;
        touchpadStyle.knob = touchKnob;
        //Create new TouchPad with the created style

        touchpad = new Touchpad(40, touchpadStyle);
        //setBounds(x,y,width,height)
        touchpad.setBounds(50, 50, touchBackground.getMinWidth(), touchBackground.getMinHeight());

        //Create a Stage and add TouchPad
        return touchpad;

    }

    private Button initPauseButton() {
        butBack = new TextButton("||", uiSkin);
        butBack.setTransform(true);
        butBack.setScale(1.3f);
        butBack.setPosition(game.getScreenX() / 50, game.getScreenY() / 30 * 27);
        butBack.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //dispose();
                //game.setScreen(new MainScreen(game));
                //Add pause-code
                return true;
            }
        });
        return butBack;
    }



}
