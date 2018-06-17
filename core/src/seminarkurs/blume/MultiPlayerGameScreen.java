package seminarkurs.blume;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
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

    public MyGdxGame getGame() {
        return game;
    }

    private MyGdxGame game;

    private MultiPlayer MP;

    private Stage stage;

    private Skin uiSkin;

    private int screen_width;
    private int screen_height;

    private OrthographicCamera cam;

    private final float VIRTUAL_WIDTH = 1920;
    private final float VIRTUAL_HEIGHT = 1080;

    private Button butBack;
    private Button butLeft;
    private Button butRight;
    private Button butJump;
    private Button butTritt;
    private Button butSchlag;

    private BitmapFont statusFont;

    private boolean player1_isInJump = false;
    private boolean moveleft = false;
    private boolean moveright = false;
    private boolean schlag = false;
    private boolean tritt = false;
    private int player1_sprung = 0;

    private boolean player2_isInJump = false;

    private static int walk_width = 10;

   private boolean player1_hasSentJump = false;

    private int player1_char;
    private int player2_char;

    private boolean isLoading = false;

    private boolean canTritt = true;
    private boolean canSchlag = true;

    private boolean showWinningScreen = false;
    private int winningScreenFrames = 240;

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        stage.getBatch().setProjectionMatrix(cam.combined);
        stage.getBatch().begin();
        if(!MP.getWinner().equals(""))
        {
            isLoading = false;
            showWinningScreen = true;
        }
        if(isLoading)
        {
            game.font_debug.draw(stage.getBatch(), "Connecting to Server", screen_width/2, screen_height / 4 * 3);
            Animation.loading_elapsed_time += delta;
            Animation.loadingCurrentFrame = (TextureRegion) Animation.char2_trittAnimation.getKeyFrame(Animation.loading_elapsed_time);
            stage.getBatch().draw(Animation.loadingCurrentFrame, screen_width / 2, screen_height / 5 * 2);
        }
        else if(showWinningScreen)
        {
            if(MP.getWinner().equals(NetworkPlayer.getUsername()))
            {
                game.font_debug.getData().setScale(3.5f);
                game.font_debug.setColor(Color.PINK);
                game.font_debug.draw(stage.getBatch(), "You won", screen_width / 4 * 1.5f, screen_height / 4 * 3);
                game.font_debug.getData().setScale(1f);
                game.font_debug.setColor(Color.BLACK);
            }
            else
            {
                game.font_debug.getData().setScale(2.5f);
                game.font_debug.setColor(Color.PINK);
                game.font_debug.draw(stage.getBatch(), "You lost", screen_width / 4 * 1.5f, screen_height / 4 * 3);
                game.font_debug.getData().setScale(1f);
                game.font_debug.setColor(Color.BLACK);
            }
            game.font_debug.draw(stage.getBatch(), "Winner: " + MP.getWinner() + "\nLooser: " + MP.getLooser(), screen_width / 2.5f, screen_height / 2);
            winningScreenFrames--;
            if(winningScreenFrames == 0) {
                dispose();
                game.setScreen(new MainScreen(game));
                LocalPlayer.setMap(0);
                MP.dispose();
            }
        }
        else {
            player1_char = MP.getLocalPlayer().getPlayer();
            player2_char = MP.getEnemy().getPlayer();
            try {
                animatePlayer1();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            animatePlayer2();

 //TODO debug code
            String enemy = "Enemy";
            String you = "You";
            if (MP.getEnemy().getDirection() == -1)
                enemy = "<-- GEGNER";
            else
                enemy = "GEGNER -->";
            if (MP.getLocalPlayer().getDirection() == -1)
                you = "<-- DU";
            else
                you = "DU -->";
            game.font_debug.draw(stage.getBatch(), "Dir: " + MP.getLocalPlayer().getDirection() + " NR: " + MultiPlayer.getPlayerNr() + "PNR: " + MP.getLocalPlayer().getPlayer(), screen_width / 3, screen_height / 20 * 16);
            game.font_debug.draw(stage.getBatch(), "Your X: " + MP.getLocalPlayer().getX() + " Y: " + MP.getLocalPlayer().getY() + " Direction: " + you, screen_width / 3, screen_height / 20 * 13);
            game.font_debug.draw(stage.getBatch(), "Enemy X: " + MP.getEnemy().getX() + " Y: " + MP.getEnemy().getY() + " Direction: " + enemy, screen_width / 3, screen_height / 20 * 10);


            game.font_debug.draw(stage.getBatch(), "Your HP: " + MP.getLocalPlayer().getHealth(), screen_width / 12, screen_height / 12);
            game.font_debug.draw(stage.getBatch(), "Enemy HP: " + MP.getEnemy().getHealth(), screen_width / 2, screen_height / 12);
        }
        stage.getBatch().end();
        stage.act();
        stage.draw();
    }

    private void animatePlayer1() throws IOException{
        boolean animation_done = false;
        if(butRight.isPressed() && MP.getLocalPlayer().getX() + walk_width < 1460)
        {
            moveright = true;
            Animation.player1_walk_elapsed_time += Gdx.graphics.getDeltaTime();

            if(player1_char == 2)
                Animation.player1_walkCurrentFrame = (TextureRegion) Animation.char2_walkAnimation.getKeyFrame(Animation.player1_walk_elapsed_time);
            else
                Animation.player1_walkCurrentFrame = (TextureRegion) Animation.char1_walkAnimation.getKeyFrame(Animation.player1_walk_elapsed_time);

            stage.getBatch().draw(Animation.player1_walkCurrentFrame, (float)MP.getLocalPlayer().getX(), (float)MP.getLocalPlayer().getY());
            animation_done = true;

            try {
                MP.moveRight(walk_width);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(butLeft.isPressed() && MP.getLocalPlayer().getX() - walk_width > -180)
        {
            moveleft = true;
            Animation.player1_walk_elapsed_time += Gdx.graphics.getDeltaTime();

            if(player1_char == 2)
                Animation.player1_walkCurrentFrame = (TextureRegion) Animation.char2_walkAnimation.getKeyFrame(Animation.player1_walk_elapsed_time);
            else
                Animation.player1_walkCurrentFrame = (TextureRegion) Animation.char1_walkAnimation.getKeyFrame(Animation.player1_walk_elapsed_time);

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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(player1_isInJump)
        {
            seminarkurs.blume.Animation.player1_jump_elapsed_time += Gdx.graphics.getDeltaTime();

            if(player1_char == 2)
                seminarkurs.blume.Animation.player1_jumpCurrentFrame = (TextureRegion) seminarkurs.blume.Animation.char2_jumpAnimation.getKeyFrame(seminarkurs.blume.Animation.player1_jump_elapsed_time);
            else
                seminarkurs.blume.Animation.player1_jumpCurrentFrame = (TextureRegion) seminarkurs.blume.Animation.char1_jumpAnimation.getKeyFrame(seminarkurs.blume.Animation.player1_jump_elapsed_time);

            if(!animation_done) {
                stage.getBatch().draw(seminarkurs.blume.Animation.player1_jumpCurrentFrame, (float) MP.getLocalPlayer().getX(), (float) MP.getLocalPlayer().getY());
                animation_done = true;
            }
            if(!player1_hasSentJump) {
                MP.jump(250);
                player1_hasSentJump = true;
            }
            player1_sprung++;
            if(player1_sprung > 24/* && seminarkurs.blume.Animation.char1_jumpAnimation.isAnimationFinished(seminarkurs.blume.Animation.player1_jump_elapsed_time)*/)
            {
                MP.onGround(250);
                player1_hasSentJump = false;
                player1_sprung = 0;
                seminarkurs.blume.Animation.player1_jump_elapsed_time = 0;
                player1_isInJump = false;
            }
        }

        if(schlag)
        {
            Animation.player1_schlag_elapsed_time += Gdx.graphics.getDeltaTime();

            if(player1_char == 2)
                Animation.player1_schlagCurrentFrame = (TextureRegion) Animation.char2_schlagAnimation.getKeyFrame(Animation.player1_schlag_elapsed_time);
            else
                Animation.player1_schlagCurrentFrame = (TextureRegion) Animation.char1_schlagAnimation.getKeyFrame(Animation.player1_schlag_elapsed_time);

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

            if(Animation.char1_schlagAnimation.isAnimationFinished(Animation.player1_schlag_elapsed_time) || Animation.char2_schlagAnimation.isAnimationFinished(Animation.player1_schlag_elapsed_time)) {
                canSchlag = true;
                schlag = false;
                Animation.player1_schlag_elapsed_time = 0;
            }
        }

        if(tritt)
        {
            Animation.player1_tritt_elapsed_time += Gdx.graphics.getDeltaTime();

            if(player1_char == 2)
                Animation.player1_trittCurrentFrame = (TextureRegion) Animation.char2_trittAnimation.getKeyFrame(Animation.player1_tritt_elapsed_time);
            else
                Animation.player1_trittCurrentFrame = (TextureRegion) Animation.char1_trittAnimation.getKeyFrame(Animation.player1_tritt_elapsed_time);

            int direction = MP.getLocalPlayer().getDirection();
            if(direction == -1) {
                Animation.player1_trittCurrentFrame.flip(true, false);
                if(!animation_done)
                {
                    stage.getBatch().draw(Animation.player1_trittCurrentFrame, (float) MP.getLocalPlayer().getX(), (float) MP.getLocalPlayer().getY());
                    animation_done = true;
                }
                Animation.player1_trittCurrentFrame.flip(true, false);
            }
            else if(direction == 1) {
                if(!animation_done)
                {
                    stage.getBatch().draw(Animation.player1_trittCurrentFrame, (float) MP.getLocalPlayer().getX(), (float) MP.getLocalPlayer().getY());
                    animation_done = true;
                }
            }

            if(player1_char == 2)
            {
                if(Animation.char2_trittAnimation.isAnimationFinished(Animation.player1_tritt_elapsed_time)) {
                    canTritt = true;
                    tritt = false;
                    Animation.player1_tritt_elapsed_time = 0;
                }
            }
            else
            {
                if(Animation.char1_trittAnimation.isAnimationFinished(Animation.player1_tritt_elapsed_time)) {
                    canTritt = true;
                    tritt = false;
                    Animation.player1_tritt_elapsed_time = 0;
                }
            }

        }

        if(!animation_done)
        {
            if(player1_char == 2)
                stage.getBatch().draw(Animation.sprite_char2_still, (float)MP.getLocalPlayer().getX(), (float)MP.getLocalPlayer().getY());
            else
                stage.getBatch().draw(Animation.sprite_char1_still, (float)MP.getLocalPlayer().getX(), (float)MP.getLocalPlayer().getY());
            MP.doNothing();
        }
    }

    private void animatePlayer2()
    {
        boolean animation_done = false;
        int direction;

        if(MP.getEnemy().getAction() == 2)
        {
            Animation.player2_walk_elapsed_time += Gdx.graphics.getDeltaTime();

            if(player2_char == 2)
                Animation.player2_walkCurrentFrame = (TextureRegion) Animation.char2_walkAnimation.getKeyFrame(Animation.player2_walk_elapsed_time);
            else
                Animation.player2_walkCurrentFrame = (TextureRegion) Animation.char1_walkAnimation.getKeyFrame(Animation.player2_walk_elapsed_time);

            stage.getBatch().draw(Animation.player2_walkCurrentFrame, (float)MP.getEnemy().getX(), (float)MP.getEnemy().getY());
            animation_done = true;
        }

        if(MP.getEnemy().getAction() == 1)
        {
            Animation.player2_walk_elapsed_time += Gdx.graphics.getDeltaTime();

            if(player2_char == 2)
                Animation.player2_walkCurrentFrame = (TextureRegion) Animation.char2_walkAnimation.getKeyFrame(Animation.player2_walk_elapsed_time);
            else
                Animation.player2_walkCurrentFrame = (TextureRegion) Animation.char1_walkAnimation.getKeyFrame(Animation.player2_walk_elapsed_time);


            if(!animation_done) {
                Animation.player2_walkCurrentFrame.flip(true, false);
                stage.getBatch().draw(Animation.player2_walkCurrentFrame, (float) MP.getEnemy().getX(), (float) MP.getEnemy().getY());
                animation_done = true;
                Animation.player2_walkCurrentFrame.flip(true, false);
            }
        }

        player2_isInJump = MP.getEnemy().getAction() == 3;

        if(player2_isInJump)
        {
            seminarkurs.blume.Animation.player2_jump_elapsed_time += Gdx.graphics.getDeltaTime();

            if(player2_char == 2)
                seminarkurs.blume.Animation.player2_jumpCurrentFrame = (TextureRegion) seminarkurs.blume.Animation.char2_jumpAnimation.getKeyFrame(seminarkurs.blume.Animation.player1_jump_elapsed_time);
            else
                seminarkurs.blume.Animation.player2_jumpCurrentFrame = (TextureRegion) seminarkurs.blume.Animation.char1_jumpAnimation.getKeyFrame(seminarkurs.blume.Animation.player1_jump_elapsed_time);

            if(!animation_done) {
                stage.getBatch().draw(seminarkurs.blume.Animation.player2_jumpCurrentFrame, (float) MP.getEnemy().getX(), (float) MP.getEnemy().getY());
                animation_done = true;
            }
            /*player2_sprung++;
            if(player2_sprung > 24)
            {
                player2_sprung = 0;
                seminarkurs.blume.Animation.player1_jump_elapsed_time = 0;
                player2_isInJump = false;
            }*/
        }

        if(MP.getEnemy().getAction() == 5) {
            Animation.player2_tritt_elapsed_time += Gdx.graphics.getDeltaTime();

            if (player2_char == 2)
                Animation.player2_trittCurrentFrame = (TextureRegion) Animation.char2_trittAnimation.getKeyFrame(Animation.player2_tritt_elapsed_time);
            else
                Animation.player2_trittCurrentFrame = (TextureRegion) Animation.char1_trittAnimation.getKeyFrame(Animation.player2_tritt_elapsed_time);

            direction = MP.getEnemy().getDirection();
            if (direction == -1) {
                Animation.player2_trittCurrentFrame.flip(true, false);
                if (!animation_done) {
                    stage.getBatch().draw(Animation.player2_trittCurrentFrame, (float) MP.getEnemy().getX(), (float) MP.getEnemy().getY());
                    animation_done = true;
                }
                Animation.player2_trittCurrentFrame.flip(true, false);
            } else if (direction == 1) {
                if (!animation_done) {
                    stage.getBatch().draw(Animation.player2_trittCurrentFrame, (float) MP.getEnemy().getX(), (float) MP.getEnemy().getY());
                    animation_done = true;
                }
            }

            if(player2_char == 2)
            {
                if(Animation.char2_trittAnimation.isAnimationFinished(Animation.player2_tritt_elapsed_time)) {
                    Animation.player2_tritt_elapsed_time = 0;
                }
            }
            else
            {
                if(Animation.char1_trittAnimation.isAnimationFinished(Animation.player2_tritt_elapsed_time)) {
                    Animation.player2_tritt_elapsed_time = 0;
                }
            }
        }
        if (MP.getEnemy().getAction() == 6) {
            Animation.player2_schlag_elapsed_time += Gdx.graphics.getDeltaTime();
            if (player2_char == 2)
                Animation.player2_schlagCurrentFrame = (TextureRegion) Animation.char2_schlagAnimation.getKeyFrame(Animation.player2_schlag_elapsed_time);
            else
                Animation.player2_schlagCurrentFrame = (TextureRegion) Animation.char1_schlagAnimation.getKeyFrame(Animation.player2_schlag_elapsed_time);

            direction = MP.getEnemy().getDirection();
            if (direction == -1) {
                Animation.player2_schlagCurrentFrame.flip(true, false);
                if (!animation_done) {
                    try {
                        stage.getBatch().draw(Animation.player2_schlagCurrentFrame, (float) MP.getEnemy().getX(), (float) MP.getEnemy().getY());
                    }
                    catch (Exception e)
                    {

                    }
                    animation_done = true;
                }
                Animation.player2_schlagCurrentFrame.flip(true, false);
            } else if (direction == 1) {
                if (!animation_done) {
                    try {
                        stage.getBatch().draw(Animation.player2_schlagCurrentFrame, (float) MP.getEnemy().getX(), (float) MP.getEnemy().getY());
                    }
                    catch (Exception e)
                    {

                    }
                    animation_done = true;
                }
            }

            if(player2_char == 2)
            {
                if(Animation.char2_schlagAnimation.isAnimationFinished(Animation.player2_schlag_elapsed_time)) {
                    Animation.player2_schlag_elapsed_time = 0;
                }
            }
            else
            {
                if(Animation.char1_schlagAnimation.isAnimationFinished(Animation.player2_schlag_elapsed_time)) {
                    Animation.player2_schlag_elapsed_time = 0;
                }
            }
        }

        if(!animation_done)
        {
            switch(player2_char)
            {
                case 1:
                    stage.getBatch().draw(Animation.sprite_char1_still, (float)MP.getEnemy().getX(), (float)MP.getEnemy().getY());
                    break;
                case 2:
                    stage.getBatch().draw(Animation.sprite_char2_still, (float)MP.getEnemy().getX(), (float)MP.getEnemy().getY());
                    break;
                default:
                    stage.getBatch().draw(Animation.sprite_char1_still, (float)MP.getEnemy().getX(), (float)MP.getEnemy().getY());
            }
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

    private void showLoadingScreen()
    {
        isLoading = true;
    }

    public void exitLoadingScreen()
    {
        isLoading = false;
    }

    @Override
    public void create()
    {
        showLoadingScreen();

        this.stage = game.getStage();

        this.uiSkin = game.getSkin();

        this.screen_width = Gdx.graphics.getWidth();
        this.screen_height = Gdx.graphics.getHeight();

        cam = new OrthographicCamera();
        cam.setToOrtho(false, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);


        LocalPlayer.setbIsIngame(true);

        initPauseButton();

        setupInterface();

        statusFont = new BitmapFont();

        statusFont.getData().setScale(3f);
        statusFont.setColor(Color.GREEN);
        //statusFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        Gdx.input.setInputProcessor(stage);

        player1_char = MP.getLocalPlayer().getPlayer();
        player2_char = MP.getEnemy().getPlayer();
    }

    @Override
    public void dispose() {
        stage.clear();
    }

    public MultiPlayerGameScreen(MyGdxGame game, int PORT){
        this.game = game;
        MP = new MultiPlayer(PORT, this);
        create();
    }

    private void initPauseButton()
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

    private void setupInterface()
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
        butJump.setPosition(screen_width / 12 * 9, screen_height / 9);
        butJump.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                player1_isInJump = true;
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
                if(canSchlag) {
                    canSchlag = false;
                    try {
                        MP.doSchlag();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    schlag = true;
                }
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
                if(canTritt) {
                    canTritt = false;
                    try {
                        MP.doTritt();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    tritt = true;
                }
                return true;
            }
        });
        stage.addActor(butTritt);
    }
}
