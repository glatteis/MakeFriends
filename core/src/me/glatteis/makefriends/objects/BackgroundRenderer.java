package me.glatteis.makefriends.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Linus on 24.12.2015.
 */
public class BackgroundRenderer {

    private Texture background;
    private SpriteBatch batch;

    public BackgroundRenderer() {
        batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("textures/stages/stage1.png"));
    }

    public void render() {
        batch.begin();
        batch.draw(background, 0, 0, 480 * 1.5f, 320 * 1.5f);
        batch.end();
    }

}
