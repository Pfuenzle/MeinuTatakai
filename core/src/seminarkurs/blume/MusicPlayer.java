package seminarkurs.blume;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Leon on 05.06.2018.
 */

public class MusicPlayer {
    private static Music music;
    private static String[] MusicName = {"bensound-actionable.mp3", "bensound-extremeaction.mp3", "bensound-highoctane.mp3", "bensound-rumble.mp3"}; //Array mit den Namen der MP3 Dateien

    public static void loadSong(int song) //Lade einzelnes Lied aus der Playlist
    {
        AssetManager manager = new AssetManager(); //Erstelle neues AssetManager-Objekt
        manager.load("music/" + MusicName[song], Music.class); //Lade Musikdatei in Assetmanager
        manager.finishLoading();
        music = manager.get("music/" + MusicName[song], Music.class); //Lade Musikdatei in Musikobjekt

    }

    public static void startSong() //Starte aktuelles Lied
    {
        music.play();
    }

    public static void stopSong() //Beende aktuellen Song
    {
        if(music.isPlaying())
            music.stop();
    }

    public static void startPlaylist()
    {
        int song = ThreadLocalRandom.current().nextInt(0, MusicName.length); //Wähle zufälliges Lied aus Playlist
        AssetManager manager = new AssetManager();
        manager.load("music/" + MusicName[song], Music.class); //Lade das zufällige Lied in den AssetManager
        manager.finishLoading();
        music = manager.get("music/" + MusicName[song], Music.class); //Lade Musik in Objekt
        music.setOnCompletionListener(new Music.OnCompletionListener() { //Aktion, welche nach ende des Lied ausgeführt wird
            @Override
            public void onCompletion(Music aMusic) {
                startPlaylist(); //Lade nach Ende des Liedes erneut ein zufälliges und Spiele es ab
            }
        });
        updateVolume(); //Prüfe, ob Nutzer Musik ausgeschalten hat
        startSong(); //Starte initialisiertes Lied
    }

    public static void stopPlaylist() //Stoppe Playlist nach dem aktuellen Lied
    {
        music.setOnCompletionListener(new Music.OnCompletionListener() {
            @Override
            public void onCompletion(Music aMusic) {

            }
        });
    }

    public static void updateVolume() //(De)-aktiviert Lautstärke der Musik je nach Einstellung
    {
        float volume = Settings.isMusicEnabled() ? 1 : 0f;
        music.setVolume(volume);
    }

    public static void setVolume(float Volume) // Setze Musik auf beliebige Lautstärke
    {
        music.setVolume(Volume);
    }
}
