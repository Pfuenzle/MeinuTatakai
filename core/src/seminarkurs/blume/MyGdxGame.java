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
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class MyGdxGame extends Game {

	private Stage stage;

	private Skin uiSkin;

	private BitmapFont font;
	public BitmapFont font_yellow;

	private int screen_width;
	private int screen_height;

	private static Texture backgroundTexture;
	private static Sprite backgroundSprite;

	private String deviceName;
	@Override
	public void create () {
		stage = new Stage();

		uiSkin = new Skin(Gdx.files.internal("skin/skin/comic-ui.json"));
		font = uiSkin.getFont("font");
		uiSkin.getFont("font").getData().setScale(3f);

		Gdx.app.setLogLevel(Application.LOG_ERROR);

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
		final String FONT_PATH = "OpenSans.ttf";
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(FONT_PATH));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 65;
		parameter.color = Color.YELLOW;
		font_yellow = generator.generateFont(parameter);
		generator.dispose();
	}

	private void renderFPS()
	{
		if(!deviceName.equals("nicisilver")) {
			font_yellow.draw(stage.getBatch(), "FPS: " + String.valueOf(Gdx.graphics.getFramesPerSecond()), screen_width / 20 * 17, screen_height / 20 * 19);
			font_yellow.draw(stage.getBatch(), "SESSION: " + NetworkPlayer.getSESSION(), screen_width / 20 * 10, screen_height / 20 * 19);
		}
	}

	public MyGdxGame getGame()
	{
		return this;
	}

	public Stage getStage()
	{
		return this.stage;
	}

	public Skin getSkin()
	{
		return this.uiSkin;
	}

	public MyGdxGame(String HWID, String deviceName)
	{
		NetworkPlayer.setHWID(HWID);
		this.deviceName = deviceName;
	}

}