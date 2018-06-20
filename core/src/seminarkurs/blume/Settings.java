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
        prefs = Gdx.app.getPreferences("Settings"); //Initialisiere Preferences-Objekt
    }

    public static boolean isSaveUser() {
        return prefs.getBoolean("isSaveUser");
    } //Lese aus, ob der Nutzer das Passwort gespeichert haben will

    public static void setSaveUser(boolean saveUser) {
        prefs.putBoolean("isSaveUser", saveUser);
        prefs.flush();
    }//Speichere, ob der Nutzer das Passwort gespeichert haben will


    public static boolean isSavePass() {
        return prefs.getBoolean("isSavePass");
    }//Lese aus, ob der Nutzer den Usernamen gespeichert haben will

    public static void setSavePass(boolean savePass) {
        prefs.putBoolean("isSavePass", savePass);
        prefs.flush();
    }//Speichere, ob der Nutzer den Usernamen gespeichert haben will


    public static Boolean isMusicEnabled() {
        return prefs.getBoolean("volume_music");
    } //

    public static void setMusicEnabled(boolean volume_music) {
        prefs.putBoolean("volume_music", volume_music);
        prefs.flush();
    } //Speichere Musik-Einstellung des Nutzers


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


    public static String getPassword() { //Lese Passwort-Hash aus
        String password = prefs.getString("password");
        return password;
    }

    public static void setPassword(String password) { //Speichere Passwort-Hash
        prefs.putString("password", password);
        prefs.flush();
    }


}
