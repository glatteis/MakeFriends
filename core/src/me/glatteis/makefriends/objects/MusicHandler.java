package me.glatteis.makefriends.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

/**
 * Created by Linus on 30.12.2015.
 */
public class MusicHandler {

    private Music music;

    public MusicHandler() {
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music/alien.mp3"));
        music.setLooping(true);
    }

    public void play() {
        music.play();
    }

    public Music getMusic() {
        return music;
    }


}
