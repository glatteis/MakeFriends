package me.glatteis.makefriends.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;

/**
 * Created by Linus on 30.12.2015.
 */
public class SoundHandler {

    public static final Sound PAGE_TURN = Gdx.audio.newSound(Gdx.files.internal("sounds/fx/page.ogg"));
    public static final Sound RUN = Gdx.audio.newSound(Gdx.files.internal("sounds/fx/run.wav"));
    public static final Sound STOP = Gdx.audio.newSound(Gdx.files.internal("sounds/fx/stop.wav"));

    public static final Sound[] KEY_SOUNDS = new Sound[Gdx.files.internal("sounds/fx/keysounds/").list().length];

    static {
        FileHandle[] keyFiles = new FileHandle[6];
        for (int i = 0; i < 6; i++) {
            keyFiles[i] = Gdx.files.internal("sounds/fx/keysounds/key" + (i + 1) + ".ogg");
        }
        for (int i = 0; i < keyFiles.length; i++) {
            KEY_SOUNDS[i] = Gdx.audio.newSound(keyFiles[i]);
        }
    }
}
