package seminarkurs.blume;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Properties;

/**
 * Created by Leon on 22.01.2018.
 */

public class Settings {
    private static Preferences prefs;

    public static void init()
    {
        prefs = Gdx.app.getPreferences("Settings");
    }

    private static int settings_count = 4;


    public static boolean isSaveUser() {
        return prefs.getBoolean("isSaveUser");
    }

    public static void setSaveUser(boolean saveUser) {
        prefs.putBoolean("isSaveUser", saveUser);
        prefs.flush();
    }


    public static boolean isSavePass() {
        return prefs.getBoolean("isSavePass");
    }

    public static void setSavePass(boolean savePass) {
        prefs.putBoolean("isSavePass", savePass);
        prefs.flush();
    }


    public static int getVolume_music() {
        return prefs.getInteger("volume_music");
    }

    public static void setVolume_music(int volume_music) {
        prefs.putInteger("volume_sound", volume_music);
        prefs.flush();
    }


    public static int getVolume_sound() {
        return prefs.getInteger("volume_sound");
    }

    public static void setVolume_sound(int volume_sound) {
        prefs.putInteger("volume_sound", volume_sound);
        prefs.flush();
    }


    public static String getUsername() {
        return prefs.getString("username");
    }

    public static void setUsername(String username) {
        prefs.putString("username", username);
        prefs.flush();
    }


    public static String getPassword() {
        String encrypted_password = prefs.getString("password");
        String decrypted_password = Crypt.decrypt(encrypted_password, "Katze");
        return decrypted_password;
    }

    public static void setPassword(String password) {
        String decrypted_pw = password;
        String encrypted_pw = Crypt.encrypt(decrypted_pw, "Katze");
        prefs.putString("password", encrypted_pw);
        prefs.flush();
    }


}
