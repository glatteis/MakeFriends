package me.glatteis.makefriends.logic.command;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Timer;
import me.glatteis.makefriends.logic.ErrorThrower;
import me.glatteis.makefriends.logic.command.event.CommandEvent;
import me.glatteis.makefriends.logic.command.event.CommandStopEvent;
import me.glatteis.makefriends.objects.Robot;

/**
 * Created by Linus on 01.01.2016.
 */
public class JohnCena extends Command {

    private Music cenaSound = Gdx.audio.newMusic(Gdx.files.internal("sounds/music/cena.mp3"));
    private Timer timer;

    public JohnCena(String args, Robot robot, ErrorThrower errorThrower) {
        super(args, robot, errorThrower);
    }

    @Override
    public void handleEvent(CommandEvent c) {
        if (c instanceof CommandStopEvent) {
            stop();
        }
    }

    private void stop() {
        cenaSound.stop();
        robot.getGameScreen().getMusicHandler().getMusic().play();
        timer.stop();
        robot.getSprite().setColor(1, 1, 1, 1);
    }

    @Override
    public void execute() {
        robot.getGameScreen().getMusicHandler().getMusic().pause();
        timer = new Timer();
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                robot.getSprite().setColor(MathUtils.random(), MathUtils.random(), MathUtils.random(), 1);
            }
        }, 0, 0.05f);
        cenaSound.setVolume(0.8f);
        cenaSound.play();
        cenaSound.setOnCompletionListener(new Music.OnCompletionListener() {
            @Override
            public void onCompletion(Music music) {
                stop();
                suggestFinish();
            }
        });
    }
}
