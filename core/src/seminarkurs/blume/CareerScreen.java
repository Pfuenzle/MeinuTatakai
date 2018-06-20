package seminarkurs.blume;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Leon & Nicole on 01.02.2018.
 */

public class CareerScreen implements Screen {
//  Deklaration

    private MyGdxGame game;

    private Stage stage;

    private Skin uiSkin;

    private int screen_width;
    private int screen_height;

    private Button butBack;

    private BitmapFont font_Name;

    private String username;
    private int RP;
    private int wins;
    private int losses;

    private boolean userNotFound = true;

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) { // render Methode
        stage.getBatch().begin();
        if(!userNotFound)
        {   // Rp Ausgabe
            font_Name.draw(stage.getBatch(), username, screen_width/2, (screen_height/4)*3);
            String RankPointsMsg = "Your RP: " + RP;
            // Win ausgabe
            font_Name.draw(stage.getBatch(), RankPointsMsg, screen_width/8*3, (screen_height/4)*2);
            String WinsMsg = "Your wins: " + wins;
            font_Name.draw(stage.getBatch(), WinsMsg, screen_width/4, (screen_height/4)*1);
            // Losses Ausgabe
            String LossesMsg = "Your losses: " + losses;
            font_Name.draw(stage.getBatch(), LossesMsg, screen_width/2, (screen_height/4)*1);
        }
        else
            font_Name.draw(stage.getBatch(), "User not found", screen_width/2, (screen_height/4)*1);

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
    } // Löschen nicht mehr benötigter Objekte

    public CareerScreen(MyGdxGame game) // Konstrucktor
    {
        this.game = game;
        stage = game.getStage();
        uiSkin = game.getSkin();

        initBackButton();

        setupInterfaces();//nimmt größe des Bildschirms


        this.screen_width = Gdx.graphics.getWidth();
        this.screen_height = Gdx.graphics.getHeight();
        //nimmt Daten für Rp,wins,losses vom server
        username = NetworkPlayer.getUsername();
        RP = NetworkPlayer.getRP();
        wins = NetworkPlayer.getWins();
        losses = NetworkPlayer.getLosses();

        updateStats(username);
    }

    private void setupInterfaces()
    {   // Größe, textart und farbe für Rp,wins,losses
        font_Name = new BitmapFont();
        font_Name.getData().setScale(5f);
        font_Name.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font_Name.setColor(Color.GREEN);

        float screen_x = game.getScreenX();
        float screen_y = game.getScreenY();
        // Textfeld Username  Initialisierung
        final TextField usernameTextField = new TextField("", uiSkin);
        usernameTextField.setPosition(screen_x/3,screen_y/12*9);
        usernameTextField.setSize(screen_x/3, screen_y/12);
        usernameTextField.setMessageText("Username");
        stage.addActor(usernameTextField);
        // Button Change User Initialisierung
        Button button_user = new TextButton("Change User", uiSkin);
        button_user.setPosition(screen_x/2-button_user.getScaleX()/2,screen_y/12*11);
        button_user.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                updateStats(usernameTextField.getText());
                return true;
            }
        });
        stage.addActor(button_user);
    }

    public void initBackButton()
    {    // back Button Initialisierung
        butBack = new TextButton("Back", uiSkin);
        butBack.setTransform(true);
        butBack.setScale(2f);
        butBack.setPosition(game.getScreenX()/25, game.getScreenY()/15*13);
        butBack.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                dispose();
                game.setScreen(new MainScreen(game));// neuen Screen aufrufen
                return true;
            }
        });
        stage.addActor(butBack);
    }

    private void updateStats(String username_in)
    {
        this.username = username_in;
        String packettype = "1x0";
        String packet = packettype + "x" + username;
        SocketHints socketHints = new SocketHints();
        Socket socket = null;
        try
        {
            socket = Gdx.net.newClientSocket(Net.Protocol.TCP, "seminarkurs.pfuenzle.io", 1337, socketHints);
        }
        catch(com.badlogic.gdx.utils.GdxRuntimeException e)
        {
            userNotFound = true;
            return;
        }
        try {
            socket.getOutputStream().write(packet.getBytes());
        } catch (IOException e) {
            userNotFound = true;
            e.printStackTrace();
            return;
        }

        final Socket finalSocket = socket;
        new Thread(new Runnable() {
            @Override
            public void run () {
                String resp;
                try {
                    resp = new BufferedReader(new InputStreamReader(finalSocket.getInputStream())).readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                    e.toString();
                    Gdx.app.log("UserPacket", e.toString());
                    userNotFound = true;
                    return;
                }
                //Gdx.app.log("UserPacket", resp);
                UserPacket user = new UserPacket(resp);
                if(user.getRet())
                {
                    RP = user.getRP();
                    wins = user.getWins();
                    losses = user.getLosses();
                    userNotFound = false;
                }
                else
                    userNotFound = true;

            }
        }).start();
    }

}
