package me.glatteis.makefriends.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

/**
 * Created by Linus on 26.12.2015.
 */
public class ScreenDim {

    public static void renderDim() {
        Gdx.gl.glEnable (GL20.GL_BLEND);
        Gdx.gl.glBlendFunc (GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_CLEAR_VALUE);
    }

}
