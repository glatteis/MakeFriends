package me.glatteis.makefriends.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import me.glatteis.makefriends.MakeFriends;

/**
 * Created by Linus on 23.12.2015.
 */
public class Splash implements Screen {

    private SpriteBatch batch;
    private Sprite splash;
    private MakeFriends game;
    private float time;

    private int width = 480;
    private int height = 320;

    public Splash(MakeFriends game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        splash = new Sprite(new Texture(Gdx.files.internal("textures/gui/splash.png")));
        splash.flip(true, false);
        time = 0;
    }

    @Override
    public void render(float delta) {
        batch.begin();
        batch.draw(splash, 0, 0, width, height);
        batch.end();

        time += delta;
        if (time > 1) game.setScreen(new GameScreen());
    }

    @Override
    public void resize(int width, int height) {
        this.width = width;
        this.height = height;
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
