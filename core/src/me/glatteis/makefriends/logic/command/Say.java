package me.glatteis.makefriends.logic.command;

import me.glatteis.makefriends.logic.ErrorThrower;
import me.glatteis.makefriends.logic.command.event.CommandEvent;
import me.glatteis.makefriends.objects.Robot;

/**
 * Created by Linus on 25.12.2015.
 */
public class Say extends Command {

    public Say(String args, Robot robot, ErrorThrower errorThrower) {
        super(args, robot, errorThrower);
    }

    @Override
    public void handleEvent(CommandEvent c) {

    }

    @Override
    public void execute() {
        robot.saySomething(args, false);
        suggestFinish();
    }
}
