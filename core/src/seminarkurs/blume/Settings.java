package seminarkurs.blume;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Created by Leon on 22.01.2018.
 */

public class Settings {
    private static Preferences prefs;
    public static void init()
    {
        prefs = Gdx.app.getPreferences("Settings");
    }

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


    public static Boolean isMusicEnabled() {
        return prefs.getBoolean("volume_music");
    }

    public static void setMusicEnabled(boolean volume_music) {
        prefs.putBoolean("volume_music", volume_music);
        prefs.flush();
    }


    public static boolean isSoundEnabled() {
        return prefs.getBoolean("volume_sound");
    }

    public static void setSoundEnabled(boolean volume_sound) {
        prefs.putBoolean("volume_sound", volume_sound);
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
        String password = prefs.getString("password");
        return password;
    }

    public static void setPassword(String password) {
        prefs.putString("password", password);
        prefs.flush();
    }


}
