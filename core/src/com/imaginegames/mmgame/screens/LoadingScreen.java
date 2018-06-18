package com.imaginegames.mmgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.imaginegames.mmgame.GameControl;

public class LoadingScreen implements Screen {

    private final String RUS_CHARS = "йцукенгшщзхъфывапролджэячсмитьбюЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮёЁ";
    private final String ENG_CHARS = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
    private final String SYMBOLS = "1234567890!@#$%^&*()_-=+~`{}|/<>[],.:;'?";

    private GameControl game;
    private float loading_progress_width;
    private float logo_scale = 1.0f;
    private float logo_width = 512 * logo_scale;
    private float logo_height = 256 * logo_scale;
    private float logo_alpha = 0f;

    private float red_color, green_color;

    private float shadow_scale;
    private float shadow_height;

    public LoadingScreen(GameControl game) {
        this.game = game;
        red_color = 1f;
        green_color = 0f;
    }

    @Override
    public void show() {
        game.assetManager.load("blank.png", Texture.class);
        game.assetManager.load("loading_screen_logo.png", Texture.class);
        game.assetManager.load("shadow.png", Texture.class);
        game.assetManager.load("bullet.png", Texture.class);
        game.assetManager.load("explosion_sheet.png", Texture.class);
        game.assetManager.load("fireball_sheet.png", Texture.class);
        game.assetManager.load("gamepause.png", Texture.class);
        game.assetManager.load("hexagonal_sheet.png", Texture.class);
        game.assetManager.load("move_button.png", Texture.class);
        game.assetManager.load("player_normal_sheet.png", Texture.class);
        game.assetManager.load("rocket_sheet.png", Texture.class);
        game.assetManager.load("shoot_button.png", Texture.class);
        game.assetManager.load("stat_button.png", Texture.class);
        game.assetManager.load("x_line.png", Texture.class);
        game.assetManager.load("y_line.png", Texture.class);
        game.assetManager.load("events/flying_rocket.png", Texture.class);
        game.assetManager.load("events/lion_stars.png", Texture.class);
        game.assetManager.load("events/space_light_back.png", Texture.class);
        game.assetManager.load("events/space_light_front.png", Texture.class);
        game.assetManager.load("events/stars1.png", Texture.class);
        game.assetManager.load("events/stars2.png", Texture.class);
        game.assetManager.load("smoke_sheet.png", Texture.class);

        FileHandleResolver resolver = new InternalFileHandleResolver();
        game.assetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        game.assetManager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

        //Font
        FreetypeFontLoader.FreeTypeFontLoaderParameter font = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        font.fontFileName = "fonts/Play-Bold.ttf";
        font.fontParameters.characters = RUS_CHARS + ENG_CHARS + SYMBOLS;
        font.fontParameters.size = 64;
        font.fontParameters.spaceX = -3;
        font.fontParameters.borderWidth = 3;

        font.fontParameters.borderColor = new com.badlogic.gdx.graphics.Color(0f, 0f, 0f, 0f);
        font.fontParameters.color = new com.badlogic.gdx.graphics.Color(0f, 0f, 0f, 1f);
        game.assetManager.load("fonts/Play-Bold.ttf", BitmapFont.class, font);

        //Font selected
        FreetypeFontLoader.FreeTypeFontLoaderParameter font_s = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        font_s.fontFileName = "fonts/Play-Bold_S.ttf";
        font_s.fontParameters.characters = RUS_CHARS + ENG_CHARS + SYMBOLS;
        font_s.fontParameters.size = 64;
        font_s.fontParameters.spaceX = -3;
        font_s.fontParameters.borderWidth = 3;

        font_s.fontParameters.borderColor = new com.badlogic.gdx.graphics.Color(0f, 1f, 0f, 1f);
        font_s.fontParameters.color = new com.badlogic.gdx.graphics.Color(0f, 0f, 0f, 1f);
        game.assetManager.load("fonts/Play-Bold_S.ttf", BitmapFont.class, font_s);

        //Font for chosen
        FreetypeFontLoader.FreeTypeFontLoaderParameter font_c = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        font_c.fontFileName = "fonts/Play-Regular.ttf";
        font_c.fontParameters.characters = RUS_CHARS + ENG_CHARS + SYMBOLS;
        font_c.fontParameters.size = 40;

        font_c.fontParameters.color = new com.badlogic.gdx.graphics.Color(0.5f, 0.5f, 0.5f, 1f);
        game.assetManager.load("fonts/Play-Regular.ttf", BitmapFont.class, font_c);

        //Font for information (Fully black)
        FreetypeFontLoader.FreeTypeFontLoaderParameter font_info = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        font_info.fontFileName = "fonts/Play-Regular_Info.ttf";
        font_info.fontParameters.characters = RUS_CHARS + ENG_CHARS + SYMBOLS;
        font_info.fontParameters.size = 40;

        font_info.fontParameters.color = new com.badlogic.gdx.graphics.Color(0f, 0f, 0f, 1f);
        game.assetManager.load("fonts/Play-Regular_Info.ttf", BitmapFont.class, font_info);

        //Font for events message (Font colored)
        FreetypeFontLoader.FreeTypeFontLoaderParameter font_colored = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        font_colored.fontFileName = "fonts/Play-Regular.ttf";
        font_colored.fontParameters.characters = RUS_CHARS + ENG_CHARS + SYMBOLS;
        font_colored.fontParameters.size = 40;

        font_colored.fontParameters.color = new com.badlogic.gdx.graphics.Color(0.565f, 0.933f, 0.565f, 1f);
        game.assetManager.load("fonts/Play-Regular_Colored.ttf", BitmapFont.class, font_colored);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.412f, 0.412f, 0.412f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.assetManager.update();
        loading_progress_width = Gdx.graphics.getWidth() * game.assetManager.getProgress();
        red_color = 1f - game.assetManager.getProgress();
        green_color = game.assetManager.getProgress();

        game.batch.begin();

        logo_alpha = game.assetManager.getProgress();
        shadow_scale = game.assetManager.getProgress();
        shadow_height = 256 * shadow_scale;

        //Shadow
        if (game.assetManager.isLoaded("shadow.png", Texture.class)) {
            game.batch.setColor(Color.BLACK);
            game.batch.draw(game.assetManager.get("shadow.png", Texture.class), 0,0, Gdx.graphics.getWidth(), shadow_height);

            game.batch.draw(game.assetManager.get("shadow.png", Texture.class), 0,Gdx.graphics.getHeight(), Gdx.graphics.getWidth(), -shadow_height);
            game.batch.setColor(Color.WHITE);
        }

        //Blank (Progress bar)
        if (game.assetManager.isLoaded("blank.png", Texture.class)) {
            game.batch.setColor(red_color, green_color, 0f, 1f);
            game.batch.draw(game.assetManager.get("blank.png", Texture.class), 0, 0, loading_progress_width, 15);
            game.batch.setColor(com.badlogic.gdx.graphics.Color.WHITE);
        }

        //Logo
        if (game.assetManager.isLoaded("loading_screen_logo.png", Texture.class)) {
            game.batch.setColor(1f, 1f, 1f, logo_alpha);
            game.batch.draw(game.assetManager.get("loading_screen_logo.png", Texture.class), (Gdx.graphics.getWidth() - logo_width) / 2,
                    (Gdx.graphics.getHeight() - logo_height) / 2, logo_width, logo_height);
            game.batch.setColor(1f, 1f, 1f, 1f);
        }

        game.batch.end();

        if (game.assetManager.update()) {
            game.assetManager.finishLoading();
            game.setScreen(new MainMenuScreen(game));
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

    }
}
