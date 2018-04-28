package seminarkurs.blume;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * Created by Leon on 28.01.2018.
 */

public class LoginScreen implements Screen {

    final MyGdxGame game;

    private Stage stage;

    private Skin uiSkin;

    Button button_reg;
    Button button_log;

    private BitmapFont font_msg;

    private int ret_type = 0;
    private String ret_text = "";

    //PopUp pop = new PopUp(stage, "text");

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        stage.getBatch().begin();
        renderLogin(stage);
        //pop.render();
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

    public LoginScreen(final MyGdxGame game)
    {
        this.game = game;

        font_msg = new BitmapFont();
        font_msg.setColor(Color.RED);
        font_msg.getData().setScale(3f);

        stage = game.getStage();
        uiSkin = game.getSkin();

        setupInterfaces();

        Gdx.input.setInputProcessor(stage);
    }

    public void renderLogin(Stage stage)
    {
        int msg_x = game.getScreenX()/3;
        int msg_y = game.getScreenY()/12*2;
        if(ret_type == 1) //Draw Login Message
        {
            font_msg.draw(stage.getBatch(), ret_text, msg_x, msg_y);
        }

        if(ret_type == 2) //Draw Register Message
        {
            font_msg.draw(stage.getBatch(), ret_text, msg_x, msg_y);
        }

    }

    private void setupInterfaces()
    {
        float input_width = game.getScreenX()/3;
        float input_height = game.getScreenY()/12;

        final TextField usernameTextField = new TextField("", uiSkin);
        usernameTextField.setPosition(input_width,input_height*6);
        usernameTextField.setSize(input_width, input_height);
        usernameTextField.setMessageText("Username");
        if(Settings.isSaveUser())
            usernameTextField.setText(Settings.getUsername());
        stage.addActor(usernameTextField);

        final TextField passwordTextField = new TextField("", uiSkin);
        passwordTextField.setPosition(input_width,input_height*4.5f );
        passwordTextField.setSize(input_width, input_height);
        passwordTextField.setMessageText("Password");
        passwordTextField.setPasswordCharacter('*');
        passwordTextField.setPasswordMode(true);
        if(Settings.isSavePass())
            passwordTextField.setText(Settings.getPassword());
        stage.addActor(passwordTextField);

        final CheckBox userCheckBox = new CheckBox("Save Username", uiSkin);
        userCheckBox.setPosition(input_width,input_height*4f );
        userCheckBox.setScale(2f);
        userCheckBox.setChecked(Settings.isSaveUser());
        stage.addActor(userCheckBox);

        final CheckBox passCheckBox = new CheckBox("Save Password", uiSkin);
        passCheckBox.setPosition(input_width,input_height*3.5f );
        passCheckBox.setScale(2f);
        passCheckBox.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                userCheckBox.setChecked(true);
                return true;
            }
        });
        passCheckBox.setChecked(Settings.isSavePass());
        stage.addActor(passCheckBox);

        button_reg = new TextButton("Register",uiSkin);
        button_reg.setSize(input_width/3,input_height);
        button_reg.setPosition(input_width,input_height*2f);
        button_reg.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                button_reg.setDisabled(true);
                button_log.setDisabled(true);
                String packettype = "0x2";
                String username = usernameTextField.getText();
                //NetworkPlayer.setUsername(username);
                String password = passwordTextField.getText();
                String packet = packettype + "x" + NetworkPlayer.fill(username.length(), 4) + "x" + username + "x" + NetworkPlayer.fill(password.length(), 4) + "x" + password + "x" + NetworkPlayer.getHWID();
                SocketHints socketHints = new SocketHints();
                Socket socket = null;
                try
                {
                    socket = Gdx.net.newClientSocket(Net.Protocol.TCP, "seminarkurs.pfuenzle.io", 1337, socketHints);
                }
                catch(com.badlogic.gdx.utils.GdxRuntimeException e)
                {
                    ret_type = 1;
                    ret_text = "Server unreachable";
                    button_reg.setDisabled(false);
                    button_log.setDisabled(false);
                    return false;
                }

                try {
                    socket.getOutputStream().write(packet.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                final Socket finalSocket = socket;
                new Thread(new Runnable() {
                    @Override
                    public void run () {
                        String resp = "";
                        try {
                            resp = new BufferedReader(new InputStreamReader(finalSocket.getInputStream())).readLine();
                        } catch (IOException e) {
                            button_reg.setDisabled(false);
                            button_log.setDisabled(false);
                            e.printStackTrace();
                        }
                        RegisterPacket packet_ret = new RegisterPacket(resp);
                        ret_text = packet_ret.getMsg();
                        //Gdx.app.log("AccountPacket", resp);
                        //Gdx.app.log("AccountPacket", String.valueOf(packet_ret.getReturn()));
                        //Gdx.app.log("AccountPacket", packet_ret.getMsg());
                        ret_type = 2;
                        button_reg.setDisabled(false);
                        button_log.setDisabled(false);
                    }
                }).start();
                return true;
            }
        });
        stage.addActor(button_reg);

        button_log = new TextButton("Login",uiSkin);
        button_log.setSize(input_width/3,input_height);
        button_log.setPosition(input_width + 2*(input_width/3),input_height*2f);
        button_log.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                button_reg.setDisabled(true);
                button_log.setDisabled(true);
                String packettype = "0x1";
                final String username = usernameTextField.getText();
                NetworkPlayer.setUsername(username);
                final String password = passwordTextField.getText();
                String packet = packettype + "x" + NetworkPlayer.fill(username.length(), 4) + "x" + username + "x" + NetworkPlayer.fill(password.length(), 4) + "x" + password + "x" + NetworkPlayer.getHWID();
                SocketHints socketHints = new SocketHints();
                Socket socket = null;
                try
                {
                    socket = Gdx.net.newClientSocket(Net.Protocol.TCP, "seminarkurs.pfuenzle.io", 1337, socketHints);
                }
                catch(com.badlogic.gdx.utils.GdxRuntimeException e)
                {
                    ret_type = 1;
                    ret_text = "Server unreachable";
                    button_reg.setDisabled(false);
                    button_log.setDisabled(false);
                    return false;
                }
                try {
                    socket.getOutputStream().write(packet.getBytes());
                } catch (IOException e) {
                    ret_type = 1;
                    ret_text = "Server unreachable";
                    button_reg.setDisabled(false);
                    button_log.setDisabled(false);
                    e.printStackTrace();
                    return false;
                }

                final Socket finalSocket = socket;
                new Thread(new Runnable() {
                    @Override
                    public void run () {
                        String resp = "";
                        try {
                            resp = new BufferedReader(new InputStreamReader(finalSocket.getInputStream())).readLine();
                        } catch (IOException e) {
                            button_reg.setDisabled(false);
                            button_log.setDisabled(false);
                            e.printStackTrace();
                        }
                        LoginPacket packet_ret = new LoginPacket(resp);
                        ret_text = packet_ret.getMsg();
                        ret_type = 1;
                        //Gdx.app.log("LoginPacket", resp);
                        //Gdx.app.log("LoginPacket", String.valueOf(packet_ret.getReturn()));
                        //Gdx.app.log("LoginPacket", packet_ret.getMsg());
                        if(packet_ret.getReturn())
                        {
                            //game.sOldScreen = new LoginScreen(game);
                            if(userCheckBox.isChecked())
                            {
                                Settings.setUsername(username);
                                Settings.setSaveUser(true);
                            }
                            else
                            {
                                Settings.setUsername("");
                                Settings.setSaveUser(false);
                            }
                            if(passCheckBox.isChecked())
                            {
                                Settings.setPassword(password);
                                Settings.setSavePass(true);
                            }
                            else{
                                Settings.setPassword("");
                                Settings.setSavePass(false);
                            }
                            NetworkPlayer.update();
                            dispose();
                            game.setScreen(new MainScreen(game));
                        }
                    }
                }).start();
                return true;
            }
        });
        stage.addActor(button_log);
    }


}
