package seminarkurs.blume;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
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
 * Created by Leon & Nicole on 28.01.2018.
 */

public class LoginScreen implements Screen {

    private final MyGdxGame game;// hauptklasse als argumment zB dür setscreenn//neuen screnn aufrufen skin holen

    private Stage stage; //Alles rendern /drauf zeichen

    private Skin uiSkin; // Ausssehen

    private Button button_reg; // registriren
    private Button button_log; // einloggen

    private int ret_type = 0;
    private String ret_text = "";

    private BitmapFont font_msg; //textart
    private BitmapFont font_title; // Titel
    private BitmapFont font_japanese; // jap. Titel

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        stage.getBatch().begin(); // rendern anfangen
        renderLogin();             //Statusnachrichten
        font_title.draw(stage.getBatch(), "Meinu Tatakai", (int)(game.getScreenX() / 7), (int)(game.getScreenY() / 5 * 4.4)); //Rendere Titel
        font_japanese.draw(stage.getBatch(), "雌犬戦い", (int)(game.getScreenX() / 2), (int)(game.getScreenY() / 5 * 3.5)); //Rendere Titel
        stage.getBatch().end(); // rendern enden
        stage.act();
        try {
            stage.draw(); //Auf dem Bildschirm zeigen
        }
        catch(Exception e) //Fehlermeldung abfangen
        {

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
    public void dispose() {
        stage.clear();
    } // Löschen

    public LoginScreen(final MyGdxGame game)// konstrucktor
    {
        this.game = game;

        stage = game.getStage();
        uiSkin = game.getSkin();

        initFont();

        initInterfaces();

        Gdx.input.setInputProcessor(stage);
    }

    public void renderLogin()//rendern
    {
        int msg_x = game.getScreenX()/3;
        int msg_y = game.getScreenY()/12*2;
        if(ret_type == 1) //hat sich eingeloggt
        {
            font_msg.draw(stage.getBatch(), ret_text, msg_x, msg_y);
        }

        if(ret_type == 2) //hat sich registriert
        {
            font_msg.draw(stage.getBatch(), ret_text, msg_x, msg_y);
        }

    }

    public void initFont()
    {
        font_msg = new BitmapFont();
        font_msg.setColor(Color.RED);
        font_msg.getData().setScale(3f);

       /* font_title = new BitmapFont(Gdx.files.internal("skin/raw/font-title-export.fnt"));
        font_title.getData().setScale(4f);
        font_title.setColor(25, 105, 180, 255);
        font_title.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);*/

         String TITLE_FONT_CHARS = "Meinu Tatakai"; // Titel

        final String TITLE_FONT_PATH = "Action_Man.ttf"; // Skin
        // titel initaliesieren
        FreeTypeFontGenerator title_generator = new FreeTypeFontGenerator(Gdx.files.internal(TITLE_FONT_PATH));
        FreeTypeFontGenerator.FreeTypeFontParameter title_parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        title_parameter.characters = TITLE_FONT_CHARS;
        title_parameter.size = 200;
        title_parameter.borderColor = Color.BLACK;
        title_parameter.borderWidth = 3;
        title_parameter.color = Color.BLUE;
        font_title = title_generator.generateFont(title_parameter);
        title_generator.dispose();

        String FONT_CHARS = "雌犬戦い"; // jap.Titel
        // jap.titel initalisieren
        final String FONT_PATH = "japanese.ttf";
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(FONT_PATH));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.characters = FONT_CHARS;
        parameter.size = 160;
        parameter.color = Color.PINK;
        font_japanese = generator.generateFont(parameter);
        generator.dispose();
    }

    private void initInterfaces()
    {
        float input_width = game.getScreenX()/3;
        float input_height = game.getScreenY()/12;
        // textfeld username
        final TextField usernameTextField = new TextField("", uiSkin);
        usernameTextField.setPosition(input_width,input_height*6);
        usernameTextField.setSize(input_width, input_height);
        usernameTextField.setMessageText("Username");
        if(Settings.isSaveUser())
            usernameTextField.setText(Settings.getUsername());
        stage.addActor(usernameTextField);
        // textfeld password
        final TextField passwordTextField = new TextField("", uiSkin);
        passwordTextField.setPosition(input_width,input_height*4.5f );
        passwordTextField.setSize(input_width, input_height);
        passwordTextField.setMessageText("Password");
        passwordTextField.setPasswordCharacter('*');
        passwordTextField.setPasswordMode(true);
        if(Settings.isSavePass())
            passwordTextField.setText(Settings.getPassword());
        stage.addActor(passwordTextField);
        // Checkbox username
        final CheckBox userCheckBox = new CheckBox("Save Username", uiSkin);
        userCheckBox.setPosition(input_width,input_height*4f );
        userCheckBox.setScale(2f);
        userCheckBox.setChecked(Settings.isSaveUser());
        stage.addActor(userCheckBox);
        // Checkbox password
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
        // Button registrieren
        button_reg = new TextButton("Register",uiSkin);
        button_reg.setSize(input_width/3,input_height);
        button_reg.setPosition(input_width,input_height*2f);
        button_reg.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                button_reg.setDisabled(true); //Deaktiviere Buttons, um mehrmaliges Klicken zu verhindern
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
                    socket = Gdx.net.newClientSocket(Net.Protocol.TCP, NetworkPlayer.getMainServer(), 1337, socketHints);
                }
                catch(com.badlogic.gdx.utils.GdxRuntimeException e)
                {
                    ret_type = 1;
                    ret_text = "Server unreachable";
                    button_reg.setDisabled(false); //Aktviere Buttons wieder
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
                        if(!packet_ret.isBroken())
                        {
                            ret_text = packet_ret.getMsg();
                            ret_type = 2;
                            button_reg.setDisabled(false);
                            button_log.setDisabled(false);
                        }
                        else
                        {
                            InputEvent event1 = new InputEvent();
                            event1.setType(InputEvent.Type.touchDown);
                            button_reg.fire(event1);
                        }
                    }
                }).start();
                return true;
            }
        });
        stage.addActor(button_reg);
        // Login Button
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
                    socket = Gdx.net.newClientSocket(Net.Protocol.TCP, NetworkPlayer.getMainServer(), 1337, socketHints);
                }
                catch(com.badlogic.gdx.utils.GdxRuntimeException e) //Verbindung fehlgeschlagen
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
                        if(!packet_ret.isBroken()){
                            ret_text = packet_ret.getMsg();
                            ret_type = 1;
                            if(packet_ret.getReturn())
                            {
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
                              NetworkPlayer.update(); //Update Stats von Spieler
                              dispose();
                              game.setScreen(new MainScreen(game));
                            }
                        }
                        else
                        {
                            InputEvent event1 = new InputEvent();
                            event1.setType(InputEvent.Type.touchDown);
                            button_log.fire(event1);
                        }
                    }
                }).start();
                return true;
            }
        });
        stage.addActor(button_log);
    }


}
