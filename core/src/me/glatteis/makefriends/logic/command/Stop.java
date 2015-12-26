package me.glatteis.makefriends.logic.command;

import com.badlogic.gdx.utils.Timer;
import me.glatteis.makefriends.logic.ErrorThrower;
import me.glatteis.makefriends.logic.command.event.CommandEvent;
import me.glatteis.makefriends.objects.Robot;

/**
 * Created by Linus on 23.12.2015.
 */
public class Stop extends Command {

    public Stop(String args, Robot robot, ErrorThrower errorThrower) {
        super(args, robot, errorThrower);
    }

    @Override
    public void handleEvent(CommandEvent c) {

    }

    @Override
    public void execute() {
        robot.getBody().setLinearVelocity(0, 0);
        new Timer().postTask(new Timer.Task() {
            @Override
            public void run() {
                suggestFinish();
            }
        });
    }
}
