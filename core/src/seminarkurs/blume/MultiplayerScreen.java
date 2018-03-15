package seminarkurs.blume;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

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

    private int screen_width;
    private int screen_height;

    private Button butBack;
    private Button butRank;
    private Button butSearch;
    private Button butCancel;

    private BitmapFont font;

    private float input_width;
    private float input_height;

    private int queueing_players = 0;

    private String debug = "";

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        stage.getBatch().begin();
        if(inQueue)
            font.draw(stage.getBatch(), "In Queue with " + NetworkPlayer.getRP()+ " RP with " + String.valueOf(queueing_players) + " other Players" + debug, screen_width/20*7, screen_height/20*7);
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
    }

    private void startRanked() throws IOException {
        final String enterPacket = "x5x0x" + NetworkPlayer.fill(NetworkPlayer.getUsername().length(), 4) + "x" + NetworkPlayer.getUsername() + "x" + NetworkPlayer.getSESSION();
        new Thread(new Runnable() {
            @Override
            public void run() {
                inQueue = true;
                Socket clientSocket = null;
                try {
                    clientSocket = new Socket("seminarkurs.pfuenzle.io", 1337);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                DataOutputStream outputStream = null;
                try {
                    outputStream = new DataOutputStream(clientSocket.getOutputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    outputStream.writeBytes(enterPacket);
                } catch (IOException e) {
                    System.exit(1);
                    e.printStackTrace();
                }

                BufferedReader inFromServer = null;
                try {
                    inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                } catch (IOException e) {
                    System.exit(1);
                    e.printStackTrace();
                }

                while(inQueue)
                {
                String response = "";
                    try {
                        response = inFromServer.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(response.substring(0, 3).equalsIgnoreCase("5x2"))
                    {
                        int queueing_players_str = Integer.parseInt(response.substring(4, 8));
                        queueing_players = Integer.parseInt(String.valueOf(queueing_players_str));
                    }
                    //queueing_players = 5;
                    /*else if(packet_in.substring(0, 3).equals("5x1"))
                    {
                        String queueing_players_str = packet_in.substring(4, packet_in.length());
                        queueing_players = Integer.parseInt(queueing_players_str);
                    }*/
                }
            }
        }).start();


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
                dispose();
                game.setScreen(new MultiplayerScreen(game));
                return true;
            }
        });
        stage.addActor(butRank);
    }

}