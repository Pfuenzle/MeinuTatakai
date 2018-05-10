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

    ProgressBar healthBar;
    Touchpad touchpad;
    private ImageButton butAttack;
    private Button butBack;
    private Touchpad.TouchpadStyle touchpadStyle;
    private Skin touchpadSkin;
    private Drawable touchBackground;
    private Drawable touchKnob;

    GameUi(MyGdxGame game) {
        this.game = game;
        this.stage = game.getStage();
        this.uiSkin = game.getSkin();

        setupInterface();

    }

    void setupInterface() {
        stage.addActor(initTouchpad());
        stage.addActor(initHealthBar(500, 1000));

        stage.addActor(initAttackButton());
        stage.addActor(initPauseButton());

    }

    private ImageButton initAttackButton() {
        butAttack = new ImageButton(uiSkin);
        butAttack.setTransform(true);
        butAttack.setScale(2f);
        butAttack.setPosition(game.getScreenX() / 25 * 20, game.getScreenY() / 15 * 3);
        butAttack.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //dispose();
                //game.setScreen(new MainScreen(game));
                //Add pause-code
                return true;
            }
        });
        return butAttack;
    }

    private ProgressBar initHealthBar(int x, int y) {
        healthBar = new ProgressBar(0, 1000, 10, false, uiSkin);
        healthBar.setPosition(x, y);
        healthBar.setScale(1);

        return healthBar;
    }

    private Touchpad initTouchpad() {
        //Create a touchpad skin
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
        butBack.setScale(2f);
        butBack.setPosition(game.getScreenX() / 25, game.getScreenY() / 15 * 13);
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
