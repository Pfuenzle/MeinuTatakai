package seminarkurs.blume;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Animation {

    static com.badlogic.gdx.graphics.g2d.Animation player1_jumpAnimation;
    static float player1_jumpDuration = 0.25f;
    static float player1_jumpStateTime;
    static TextureRegion player1_jumpCurrentFrame;
    static float player1_jump_elapsed_time = 0f;

    static  com.badlogic.gdx.graphics.g2d.Animation player1_walkAnimation;
    static float player1_walkDuration = 0.1f;
    static float player1_walkStateTime;
    static TextureRegion player1_walkCurrentFrame;
    static float player1_walk_elapsed_time = 0f;

    static com.badlogic.gdx.graphics.g2d.Animation player1_schlagAnimation;
    static float player1_schlagDuration = 0.11f;
    static float player1_schlagStateTime;
    static TextureRegion player1_schlagCurrentFrame;
    static float player1_schlag_elapsed_time = 0f;

    static Texture texture_player1_still;
    static Sprite sprite_player1_still;

    public static void initplayer1() // Initialisiere Animation von Spieler 1
    {
        TextureAtlas atlas;
        atlas = new TextureAtlas(Gdx.files.internal("figur2_sprung_animation/figur2_sprung.atlas"));
        Array<TextureAtlas.AtlasRegion> jumpFrames = atlas.getRegions();

        player1_jumpAnimation = new com.badlogic.gdx.graphics.g2d.Animation(player1_jumpDuration, jumpFrames, com.badlogic.gdx.graphics.g2d.Animation.PlayMode.LOOP);
        player1_jumpStateTime = 10f;

        TextureAtlas walkatlas;
        walkatlas = new TextureAtlas(Gdx.files.internal("figur_2_laufen/figur2_laufen.atlas"));
        Array<TextureAtlas.AtlasRegion> walkrunningFrames = walkatlas.getRegions();

        player1_walkAnimation = new com.badlogic.gdx.graphics.g2d.Animation(player1_walkDuration, walkrunningFrames, com.badlogic.gdx.graphics.g2d.Animation.PlayMode.LOOP);
        player1_walkStateTime = 10f;
        player1_walkCurrentFrame = (TextureRegion) player1_walkAnimation.getKeyFrame(0);

        TextureAtlas schlagatlas;
        schlagatlas = new TextureAtlas(Gdx.files.internal("figur2_schlagen/figur2_schlagen.atlas"));
        Array<TextureAtlas.AtlasRegion> schlagrunningFrames = schlagatlas.getRegions();

        player1_schlagAnimation = new com.badlogic.gdx.graphics.g2d.Animation(player1_schlagDuration, schlagrunningFrames);
        player1_schlagStateTime = 10f;



        texture_player1_still = new Texture(Gdx.files.internal("player2_standing.png"));
        sprite_player1_still = new Sprite(texture_player1_still, 225, 668);
    }



    static com.badlogic.gdx.graphics.g2d.Animation player2_jumpAnimation;
    static float player2_jumpDuration = 0.25f;
    static float player2_jumpStateTime;
    static TextureRegion player2_jumpCurrentFrame;
    static float player2_jump_elapsed_time = 0f;

    static  com.badlogic.gdx.graphics.g2d.Animation player2_walkAnimation;
    static float player2_walkDuration = 0.1f;
    static float player2_walkStateTime;
    static TextureRegion player2_walkCurrentFrame;
    static float player2_walk_elapsed_time = 0f;

    static com.badlogic.gdx.graphics.g2d.Animation player2_schlagAnimation;
    static float player2_schlagDuration = 0.11f;
    static float player2_schlagStateTime;
    static TextureRegion player2_schlagCurrentFrame;
    static float player2_schlag_elapsed_time = 0f;

    static Texture texture_player2_still;
    static Sprite sprite_player2_still;

    public static void initPlayer2() // Initialisiere Animation von Spieler 2
    {
        TextureAtlas atlas;
        atlas = new TextureAtlas(Gdx.files.internal("figur2_sprung_animation/figur2_sprung.atlas"));
        Array<TextureAtlas.AtlasRegion> jumpFrames = atlas.getRegions();

        player2_jumpAnimation = new com.badlogic.gdx.graphics.g2d.Animation(player2_jumpDuration, jumpFrames, com.badlogic.gdx.graphics.g2d.Animation.PlayMode.LOOP);
        player2_jumpStateTime = 10f;

        TextureAtlas walkatlas;
        walkatlas = new TextureAtlas(Gdx.files.internal("figur_2_laufen/figur2_laufen.atlas"));
        Array<TextureAtlas.AtlasRegion> walkrunningFrames = walkatlas.getRegions();

        player2_walkAnimation = new com.badlogic.gdx.graphics.g2d.Animation(player2_walkDuration, walkrunningFrames, com.badlogic.gdx.graphics.g2d.Animation.PlayMode.LOOP);
        player2_walkStateTime = 10f;
        player2_walkCurrentFrame = (TextureRegion) player2_walkAnimation.getKeyFrame(0);

        TextureAtlas schlagatlas;
        schlagatlas = new TextureAtlas(Gdx.files.internal("figur2_schlagen/figur2_schlagen.atlas"));
        Array<TextureAtlas.AtlasRegion> schlagrunningFrames = schlagatlas.getRegions();

        player2_schlagAnimation = new com.badlogic.gdx.graphics.g2d.Animation(player2_schlagDuration, schlagrunningFrames);
        player2_schlagStateTime = 10f;



        texture_player2_still = new Texture(Gdx.files.internal("player2_standing.png"));
        sprite_player2_still = new Sprite(texture_player2_still, 225, 668);
    }

}
