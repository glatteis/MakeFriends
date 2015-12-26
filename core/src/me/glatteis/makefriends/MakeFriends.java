package me.glatteis.makefriends;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import me.glatteis.makefriends.screens.Splash;

public class MakeFriends extends Game {
	
	@Override
	public void create () {
		setScreen(new Splash(this));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		getScreen().render(Gdx.graphics.getDeltaTime());
	}
}
