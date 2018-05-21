package seminarkurs.blume;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

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
    private Button butJump;
    private Button butTritt;
    private Button butSchlag;

    private BitmapFont statusFont;

    boolean isInJump = false;
    boolean moveleft = false;
    boolean moveright = false;
    boolean schlag = false;
    int sprung = 0;

    int walk_width = 10;

    boolean player1_hasSentJump = false;

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        stage.getBatch().begin();

        try {
            animatePlayer1();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        animatePlayer2();

        String enemy = "Enemy";
        String you = "You";
        if(MP.getEnemy().getDirection() == -1)
            enemy = "<-- GEGNER";
        else
            enemy = "GEGNER -->";
        if(MP.getLocalPlayer().getDirection() == -1)
            you = "<-- DU";
        else
            you = "DU -->";
        game.font_yellow.draw(stage.getBatch(), "Dir: " + MP.getLocalPlayer().getDirection() + " NR: " + MultiPlayer.getPlayerNr(), screen_width/3, screen_height/20*16);
        game.font_yellow.draw(stage.getBatch(), "Your X: " + MP.getLocalPlayer().getX() + " Y: " + MP.getLocalPlayer().getY() + " Direction: " + you, screen_width/3, screen_height/20*13);
        game.font_yellow.draw(stage.getBatch(), "Enemy X: " + MP.getEnemy().getX() + " Y: " + MP.getEnemy().getY() + " Direction: " + enemy, screen_width/3, screen_height/20*10);
        stage.getBatch().end();
        stage.act();
        stage.draw();
    }

    private void animatePlayer1() throws IOException, InterruptedException {
        boolean animation_done = false;
        if(butRight.isPressed() && MP.getLocalPlayer().getX() + walk_width < 1660)
        {
            moveright = true;
            Animation.player1_walk_elapsed_time += Gdx.graphics.getDeltaTime();
            Animation.player1_walkCurrentFrame = (TextureRegion) Animation.player1_walkAnimation.getKeyFrame(Animation.player1_walk_elapsed_time);
            stage.getBatch().draw(Animation.player1_walkCurrentFrame, (float)MP.getLocalPlayer().getX(), (float)MP.getLocalPlayer().getY());
            animation_done = true;

            try {
                MP.moveRight(walk_width);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if(butLeft.isPressed() && MP.getLocalPlayer().getX() - walk_width > 0)
        {
            moveleft = true;
            Animation.player1_walk_elapsed_time += Gdx.graphics.getDeltaTime();
            Animation.player1_walkCurrentFrame = (TextureRegion) Animation.player1_walkAnimation.getKeyFrame(Animation.player1_walk_elapsed_time);
            Animation.player1_walkCurrentFrame.flip(true, false);
            if(!animation_done) {
                stage.getBatch().draw(Animation.player1_walkCurrentFrame, (float) MP.getLocalPlayer().getX(), (float) MP.getLocalPlayer().getY());
                animation_done = true;
            }
            Animation.player1_walkCurrentFrame.flip(true, false);

            try {
                MP.moveLeft(walk_width);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if(isInJump)
        {
            seminarkurs.blume.Animation.player1_jump_elapsed_time += Gdx.graphics.getDeltaTime();
            seminarkurs.blume.Animation.player1_jumpCurrentFrame = (TextureRegion) seminarkurs.blume.Animation.player1_jumpAnimation.getKeyFrame(seminarkurs.blume.Animation.player1_jump_elapsed_time);
            if(!animation_done) {
                stage.getBatch().draw(seminarkurs.blume.Animation.player1_jumpCurrentFrame, (float) MP.getLocalPlayer().getX(), (float) MP.getLocalPlayer().getY());
                animation_done = true;
            }
            if(!player1_hasSentJump) {
                MP.moveUp(250);
                player1_hasSentJump = true;
            }
            sprung++;
            if(sprung > 24/* && seminarkurs.blume.Animation.player1_jumpAnimation.isAnimationFinished(seminarkurs.blume.Animation.player1_jump_elapsed_time)*/)
            {
                MP.moveDown(250);
                player1_hasSentJump = false;
                sprung = 0;
                seminarkurs.blume.Animation.player1_jump_elapsed_time = 0;
                isInJump = false;
            }
        }

        if(schlag)
        {
            Animation.player1_schlag_elapsed_time += Gdx.graphics.getDeltaTime();
            Animation.player1_schlagCurrentFrame = (TextureRegion) Animation.player1_schlagAnimation.getKeyFrame(Animation.player1_schlag_elapsed_time);
            int direction = MP.getLocalPlayer().getDirection();
            if(direction == -1) {
                Animation.player1_schlagCurrentFrame.flip(true, false);
                if(!animation_done)
                {
                    stage.getBatch().draw(Animation.player1_schlagCurrentFrame, (float) MP.getLocalPlayer().getX(), (float) MP.getLocalPlayer().getY());
                    animation_done = true;
                }
                Animation.player1_schlagCurrentFrame.flip(true, false);
            }
            else if(direction == 1) {
                if(!animation_done)
                {
                    stage.getBatch().draw(Animation.player1_schlagCurrentFrame, (float) MP.getLocalPlayer().getX(), (float) MP.getLocalPlayer().getY());
                    animation_done = true;
                }
            }

            if(Animation.player1_schlagAnimation.isAnimationFinished(Animation.player1_schlag_elapsed_time)) {
                schlag = false;
                Animation.player1_schlag_elapsed_time = 0;
            }
        }

        if(!animation_done)
        {
            stage.getBatch().draw(Animation.sprite_player1_still, (float)MP.getLocalPlayer().getX(), (float)MP.getLocalPlayer().getY());
        }
    }

    private void animatePlayer2()
    {
        boolean animation_done = false;
        if(!animation_done)
        {
            stage.getBatch().draw(Animation.sprite_player1_still, (float)MP.getEnemy().getX(), (float)MP.getEnemy().getY());
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

        Gdx.input.setInputProcessor(stage);

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
                return true;
            }
        });
        stage.addActor(butRight);

        butJump = new TextButton("Jump", uiSkin);
        butJump.setTransform(true);
        butJump.setScale(2f);
        //butRight.setSize(screen_width / 3, screen_height);
        butJump.setPosition(screen_width / 12 * 10, screen_height / 9);
        butJump.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(!isInJump) {
                    isInJump = true;
                }
                return true;
            }
        });
        stage.addActor(butJump);

        butSchlag = new TextButton("Schlag", uiSkin);
        butSchlag.setTransform(true);
        butSchlag.setScale(2f);
        //butLeft.setSize(screen_width / 3, screen_height);
        butSchlag.setPosition(screen_width / 12 * 10, screen_height / 9 * 2);
        butSchlag.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                schlag = true;
                return true;
            }
        });
        stage.addActor(butSchlag);

        butTritt = new TextButton("Tritt", uiSkin);
        butTritt.setTransform(true);
        butTritt.setScale(2f);
        //butLeft.setSize(screen_width / 3, screen_height);
        butTritt.setPosition(screen_width / 12 * 8, screen_height / 9 * 2);
        butTritt.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(butTritt);
    }
}
