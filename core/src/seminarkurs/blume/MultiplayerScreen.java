package seminarkurs.blume;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.io.*;
import java.net.*;
import java.nio.charset.Charset;

import static java.nio.charset.StandardCharsets.*;

/**
 * Created by Leon on 01.02.2018.
 */

public class MultiplayerScreen implements Screen {


    private MyGdxGame game;

    private Stage stage;

    private Skin uiSkin;

    private boolean inQueue = false;
    private boolean inGame = false;

    private int screen_width;
    private int screen_height;

    private Button butBack;
    private Button butRank;
    private Button butSearch;
    private Button butCancel;

    Socket rankedSocket = null;
    DataOutputStream rankedOutputStream = null;

    private BitmapFont font;

    private float input_width;
    private float input_height;

    private int queueing_players = 0;

    private String debug = "";

    String enemy;
    String port;
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        stage.getBatch().begin();
        if(inQueue)
            font.draw(stage.getBatch(), "In Queue with " + NetworkPlayer.getRP()+ " RP. Players in Queue: " + String.valueOf(queueing_players), screen_width/20*6, screen_height/20*10);
        if(inGame)
            font.draw(stage.getBatch(), "Found match against " + enemy + " on Port " + port, screen_width/20*6, screen_height/20*7);
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
    public void dispose() {
        stage.clear();
    }

    public MultiplayerScreen(MyGdxGame game)
    {
        this.game = game;
        stage = game.getStage();

        uiSkin = game.getSkin();

        initBackButton();

        setupInterface();

        this.screen_width = Gdx.graphics.getWidth();
        this.screen_height = Gdx.graphics.getHeight();

        font = new BitmapFont();
        font.getData().setScale(4f);
        font.setColor(Color.BLACK);
    }

    public void initBackButton()
    {
        butBack = new TextButton("<----", uiSkin);
        butBack.setTransform(true);
        butBack.setScale(2f);
        butBack.setPosition(game.getScreenX()/25, game.getScreenY()/15*13);
        butBack.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                dispose();
                game.setScreen(new MainScreen(game));
                return true;
            }
        });
        stage.addActor(butBack);
    }

    private void setupInterface()
    {
        input_width = game.getScreenX() / 3;
        input_height = game.getScreenY() / 12;

        font = new BitmapFont();

        butRank = new TextButton("Play ranked game", uiSkin);
        butRank.setTransform(true);
        butRank.setScale(2f);
        butRank.setSize(input_width / 3, input_height);
        butRank.setPosition((game.getScreenX() / 2) - butRank.getWidth(), input_height * 7);
        butRank.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                try {
                    startRanked();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            }
        });
        stage.addActor(butRank);

        butSearch = new TextButton("Search player", uiSkin);
        butSearch.setTransform(true);
        butSearch.setScale(2f);
        butSearch.setSize(input_width / 3, input_height);
        butSearch.setPosition((game.getScreenX() / 2) - butSearch.getWidth(), input_height * 4.5f);
        butSearch.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                dispose();
                game.setScreen(new MultiplayerScreen(game));
                return true;
            }
        });
        stage.addActor(butSearch);

        Texture playTexture1 = new Texture(Gdx.files.internal("char1_standing.png"));
        Drawable char1_but = new TextureRegionDrawable(new TextureRegion(playTexture1));

        ImageButton imgBtn1 = new ImageButton(char1_but);

        imgBtn1.setTransform(true);
        imgBtn1.setScale(1f);
        imgBtn1.setPosition(1920 / 10, 1080 / 10);
        imgBtn1.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                NetworkPlayer.Player = 1;
                return true;
            }
        });
        stage.addActor(imgBtn1);

        Texture playTexture2 = new Texture(Gdx.files.internal("char2_standing.png"));
        Drawable char2_but = new TextureRegionDrawable(new TextureRegion(playTexture2));

        ImageButton imgBtn2 = new ImageButton(char2_but);

        imgBtn2.setTransform(true);
        imgBtn2.setScale(1f);
        imgBtn2.setPosition(1920 / 4 * 3, 1080 / 10);
        imgBtn2.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                NetworkPlayer.Player = 2;
                return true;
            }
        });
        stage.addActor(imgBtn2);
    }

    private void startRanked() throws IOException {
        butRank.remove();
        butSearch.remove();
        butCancel = new TextButton("Cancel", uiSkin);
        butCancel.setTransform(true);
        butCancel.setScale(2f);
        butCancel.setSize(input_width / 3, input_height);
        butCancel.setPosition((game.getScreenX() / 2) - butRank.getWidth(), input_height * 7);
        butCancel.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                try {
                    rankedOutputStream.writeBytes("x5x9");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                dispose();
                game.setScreen(new MultiplayerScreen(game));
                return true;
            }
        });
        stage.addActor(butCancel);
        final String enterPacket = "x5x0x" + NetworkPlayer.fill(NetworkPlayer.getUsername().length(), 4) + "x" + NetworkPlayer.getUsername() + "x" + NetworkPlayer.getSESSION();
        new Thread(new Runnable() {
            @Override
            public void run() {
                inQueue = true;
                try {
                    rankedSocket = new Socket(NetworkPlayer.getMainServer(), 1337);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    rankedOutputStream = new DataOutputStream(rankedSocket.getOutputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    rankedOutputStream.writeBytes(enterPacket); //send packet to enter queue
                } catch (IOException e) {
                    e.printStackTrace();
                }

                BufferedReader inFromServer = null;
                try {
                    inFromServer = new BufferedReader(new InputStreamReader(rankedSocket.getInputStream()));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Gdx.app.setLogLevel(Application.LOG_DEBUG);

                while(inQueue)
                {
                String response = "";
                    try {
                        response = inFromServer.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(response != null)
                        Gdx.app.debug("PACKET", response);
                    if(response != null) {
                        if (response.substring(0, 3).equalsIgnoreCase("5x2")) {
                            int queueing_players_str = 0;
                            try {
                                queueing_players_str = Integer.parseInt(response.substring(4, 8));
                            }
                            catch(Exception e)
                            {
                                e.printStackTrace();
                            }
                            queueing_players = Integer.parseInt(String.valueOf(queueing_players_str));
                        } else if (response.substring(0, 3).equals("5x1")) {
                            Gdx.app.debug("Game found", response);
                            //5x1xLENGTHxENEMYxPORT
                            int enemy_length = Integer.parseInt(response.substring(4, 8));
                            enemy = response.substring(9, 9 + enemy_length);
                            int offset = 10 + enemy_length;
                            port = response.substring(offset);
                            inQueue = false;
                            inGame = true;
                            try {
                                rankedSocket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            dispose();
                            try {
                                game.setScreen(new MultiPlayerGameScreen(game, Integer.parseInt(port)));
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }).start();
    }

}