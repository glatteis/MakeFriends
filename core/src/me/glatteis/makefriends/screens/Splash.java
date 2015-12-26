package me.glatteis.makefriends.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import me.glatteis.makefriends.MakeFriends;

/**
 * Created by Linus on 23.12.2015.
 */
public class Splash implements Screen {

    private Stage stage;
    private Image splash;

    private MakeFriends game;
    private float time;

    private int width = 480;
    private int height = 320;

    public Splash(MakeFriends game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(480, 320));
        splash = new Image(new Texture(Gdx.files.internal("textures/gui/splash.png")));
        splash.setBounds(0, 0, 480, 320);
        stage.addActor(splash);
        time = 0;
        stage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(2), Actions.fadeOut(2)));
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
        time += delta;
        if (time > 4) game.setScreen(new GameScreen());
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
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
