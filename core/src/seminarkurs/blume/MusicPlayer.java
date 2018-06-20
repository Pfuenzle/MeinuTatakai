package seminarkurs.blume;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Nicole on 05.06.2018. //TODO wer
 */

/*
        if(!music.isPlaying())
            loadSong(ThreadLocalRandom.current().nextInt(0, MusicName.length + 1));
 */

public class MusicPlayer {
    private static Music music;
    private static String[] MusicName = {"bensound-actionable.mp3", "bensound-extremeaction.mp3", "bensound-highoctane.mp3", "bensound-rumble.mp3"};

    public static void loadSong(int song)
    {
        AssetManager manager = new AssetManager();
        manager.load("music/" + MusicName[song], Music.class);
        manager.finishLoading();
        music = manager.get("music/" + MusicName[song], Music.class);

    }

    public static void startSong()
    {
        music.play();
    }

    public static void stopSong()
    {
        if(music.isPlaying())
            music.stop();
    }

    public static void startPlaylist()
    {
        int song = ThreadLocalRandom.current().nextInt(0, MusicName.length);
        AssetManager manager = new AssetManager();
        manager.load("music/" + MusicName[song], Music.class);
        manager.finishLoading();
        music = manager.get("music/" + MusicName[song], Music.class);
        music.setOnCompletionListener(new Music.OnCompletionListener() {
            @Override
            public void onCompletion(Music aMusic) {
                startPlaylist();
            }
        });
        updateVolume();
        startSong();
    }

    public static void stopPlaylist()
    {
        music.setOnCompletionListener(new Music.OnCompletionListener() {
            @Override
            public void onCompletion(Music aMusic) {

            }
        });
    }

    public static void updateVolume() //TODO woanders rein
    {
        float volume = Settings.isMusicEnabled() ? 1 : 0f;
        music.setVolume(volume);
    }

    public static void setVolume(float Volume)
    {
        music.setVolume(Volume);
    }
}
