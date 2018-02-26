package seminarkurs.blume;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class MyGdxGame extends Game {

	Stage stage;

	Skin uiSkin;

	BitmapFont font;
	BitmapFont font_yellow;

	private int screen_width;
	private int screen_height;

	public static Texture backgroundTexture;
	public static Sprite backgroundSprite;

	private boolean butBack_old = false;

	Screen sOldScreen;
	Screen sNewScreen;

	MyGdxGame gThis;

	@Override
	public void create () {
		stage = new Stage();

		uiSkin = new Skin(Gdx.files.internal("skin/skin/comic-ui.json"));
		font = uiSkin.getFont("font");
		uiSkin.getFont("font").getData().setScale(3f);

		Gdx.app.setLogLevel(Application.LOG_ERROR);

		gThis = this;

		initBackground();

		initFPS();

		this.screen_width = Gdx.graphics.getWidth();
		this.screen_height = Gdx.graphics.getHeight();

		Settings.init();

		this.setScreen(new LoginScreen(this));
	}

	@Override
	public void render () {
		stage.getBatch().begin();

		renderBackground();

		if(GameSettings.drawFPS)
			renderFPS();

		stage.getBatch().end();
		super.render();
	}

	@Override
	public void dispose () {
		stage.dispose();
		uiSkin.dispose();
		font.dispose();
	}

	public int getScreenX()
	{
		return this.screen_width;
	}

	public int getScreenY()
	{
		return this.screen_height;
	}

	private void initBackground()
	{
		backgroundTexture = new Texture("background/background.png");
		backgroundSprite = new Sprite(backgroundTexture);
		backgroundSprite.setScale(1.5f);
		float background_origin_x = 0;
		float background_origin_y = 0;
		backgroundSprite.setOrigin(background_origin_x, background_origin_y);
	}

	private void renderBackground()
	{
		if(!LocalPlayer.isIngame() || LocalPlayer.getMap() == null)
			backgroundSprite.draw(stage.getBatch());
		else
			LocalPlayer.getMap().drawMap(stage);
	}

	private void initFPS()
	{
		font_yellow = new BitmapFont();
		font_yellow.getData().setScale(4f);
		font_yellow.setColor(Color.YELLOW);
	}

	private void renderFPS()
	{
		font_yellow.draw(stage.getBatch(), "FPS: " + String.valueOf(Gdx.graphics.getFramesPerSecond()), screen_width/20*18, screen_height/20*19);
	}

	public MyGdxGame getGame()
	{
		return this;
	}


}