package seminarkurs.blume;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Animation {

    static com.badlogic.gdx.graphics.g2d.Animation char1_jumpAnimation;
    static float char1_jumpDuration = 0.25f;
    static float char1_jumpStateTime;
    static TextureRegion player1_jumpCurrentFrame;
    static float player1_jump_elapsed_time = 0f;

    static  com.badlogic.gdx.graphics.g2d.Animation char1_walkAnimation;
    static float char1_walkDuration = 0.1f;
    static float char1_walkStateTime;
    static TextureRegion player1_walkCurrentFrame;
    static float player1_walk_elapsed_time = 0f;

    static com.badlogic.gdx.graphics.g2d.Animation char1_schlagAnimation;
    static float char1_schlagDuration = 0.11f;
    static float char1_schlagStateTime;
    static TextureRegion player1_schlagCurrentFrame;
    static TextureRegion player2_schlagCurrentFrame;
    static float player1_schlag_elapsed_time = 0f;
    static float player2_schlag_elapsed_time = 0f;

    static com.badlogic.gdx.graphics.g2d.Animation char1_trittAnimation;
    static float char1_trittDuration = 0.08f;
    static float char1_trittStateTime;
    static TextureRegion player1_trittCurrentFrame;
    static float player1_tritt_elapsed_time = 0f;

    static Texture texture_char1_still;
    static Sprite sprite_char1_still;

    public static void initChar1() // Initialisiere Animation von Character 1
    {
        TextureAtlas atlas;
        atlas = new TextureAtlas(Gdx.files.internal("char1_sprung/char1_sprung.atlas"));
        Array<TextureAtlas.AtlasRegion> jumpFrames = atlas.getRegions();

        char1_jumpAnimation = new com.badlogic.gdx.graphics.g2d.Animation(char1_jumpDuration, jumpFrames, com.badlogic.gdx.graphics.g2d.Animation.PlayMode.LOOP);
        char1_jumpStateTime = 10f;

        TextureAtlas walkatlas;
        walkatlas = new TextureAtlas(Gdx.files.internal("char1_walk/char1_walk.atlas"));
        Array<TextureAtlas.AtlasRegion> walkrunningFrames = walkatlas.getRegions();

        char1_walkAnimation = new com.badlogic.gdx.graphics.g2d.Animation(char1_walkDuration, walkrunningFrames, com.badlogic.gdx.graphics.g2d.Animation.PlayMode.LOOP);
        char1_walkStateTime = 10f;
        player1_walkCurrentFrame = (TextureRegion) char1_walkAnimation.getKeyFrame(0);

        TextureAtlas schlagatlas;
        schlagatlas = new TextureAtlas(Gdx.files.internal("char1_schlag/char1_schlag.atlas"));
        Array<TextureAtlas.AtlasRegion> schlagrunningFrames = schlagatlas.getRegions();

        char1_schlagAnimation = new com.badlogic.gdx.graphics.g2d.Animation(char1_schlagDuration, schlagrunningFrames);
        char1_schlagStateTime = 10f;

        TextureAtlas trittatlas;
        trittatlas = new TextureAtlas(Gdx.files.internal("char1_tritt/char1_tritt.atlas"));
        Array<TextureAtlas.AtlasRegion> trittrunningFrames = trittatlas.getRegions();

        char1_trittAnimation = new com.badlogic.gdx.graphics.g2d.Animation(char1_trittDuration, trittrunningFrames);
        char1_trittStateTime = 10f;


        texture_char1_still = new Texture(Gdx.files.internal("char1_standing.png"));
        sprite_char1_still = new Sprite(texture_char1_still, 477, 715);
    }



    static com.badlogic.gdx.graphics.g2d.Animation char2_jumpAnimation;
    static float char2_jumpDuration = 0.25f;
    static float char2_jumpStateTime;
    static TextureRegion player2_jumpCurrentFrame;
    static float player2_jump_elapsed_time = 0f;

    static  com.badlogic.gdx.graphics.g2d.Animation char2_walkAnimation;
    static float char2_walkDuration = 0.1f;
    static float char2_walkStateTime;
    static TextureRegion player2_walkCurrentFrame;
    static float player2_walk_elapsed_time = 0f;

    static com.badlogic.gdx.graphics.g2d.Animation char2_schlagAnimation;
    static float char2_schlagDuration = 0.11f;
    static float char2_schlagStateTime;

    static com.badlogic.gdx.graphics.g2d.Animation char2_trittAnimation;
    static float char2_trittDuration = 0.08f;
    static float char2_trittStateTime;
    static TextureRegion player2_trittCurrentFrame;
    static float player2_tritt_elapsed_time = 0f;

    static Texture texture_char2_still;
    static Sprite sprite_char2_still;

    public static void initChar2() // Initialisiere Animation von Spieler 2
    {
        TextureAtlas atlas;
        atlas = new TextureAtlas(Gdx.files.internal("char2_sprung/char2_sprung.atlas"));
        Array<TextureAtlas.AtlasRegion> jumpFrames = atlas.getRegions();

        char2_jumpAnimation = new com.badlogic.gdx.graphics.g2d.Animation(char2_jumpDuration, jumpFrames, com.badlogic.gdx.graphics.g2d.Animation.PlayMode.LOOP);
        char2_jumpStateTime = 10f;

        TextureAtlas walkatlas;
        walkatlas = new TextureAtlas(Gdx.files.internal("char2_walk/char2_walk.atlas"));
        Array<TextureAtlas.AtlasRegion> walkrunningFrames = walkatlas.getRegions();

        char2_walkAnimation = new com.badlogic.gdx.graphics.g2d.Animation(char2_walkDuration, walkrunningFrames, com.badlogic.gdx.graphics.g2d.Animation.PlayMode.LOOP);
        char2_walkStateTime = 10f;
        player2_walkCurrentFrame = (TextureRegion) char2_walkAnimation.getKeyFrame(0);

        TextureAtlas schlagatlas;
        schlagatlas = new TextureAtlas(Gdx.files.internal("char2_schlag/char2_schlag.atlas"));
        Array<TextureAtlas.AtlasRegion> schlagrunningFrames = schlagatlas.getRegions();

        char2_schlagAnimation = new com.badlogic.gdx.graphics.g2d.Animation(char2_schlagDuration, schlagrunningFrames);
        char2_schlagStateTime = 10f;

        TextureAtlas trittatlas;
        trittatlas = new TextureAtlas(Gdx.files.internal("char2_tritt/char2_tritt.atlas"));
        Array<TextureAtlas.AtlasRegion> trittrunningFrames = trittatlas.getRegions();

        char2_trittAnimation = new com.badlogic.gdx.graphics.g2d.Animation(char2_trittDuration, trittrunningFrames);
        char2_trittStateTime = 10f;

        texture_char2_still = new Texture(Gdx.files.internal("char2_standing.png"));
        sprite_char2_still = new Sprite(texture_char2_still, 225, 668);
    }

    static com.badlogic.gdx.graphics.g2d.Animation loadingAnimation;
    static TextureRegion loadingCurrentFrame;
    static float loading_elapsed_time = 0f;

    public static void initAll()
    {
        initChar1();
        initChar2();

        TextureAtlas loadingAtlas;
        loadingAtlas = new TextureAtlas(Gdx.files.internal("loading/loading.atlas"));
        Array<TextureAtlas.AtlasRegion> loadingFrames = loadingAtlas.getRegions();

        loadingAnimation = new com.badlogic.gdx.graphics.g2d.Animation(0.125f, loadingFrames, com.badlogic.gdx.graphics.g2d.Animation.PlayMode.LOOP);
    }

}
