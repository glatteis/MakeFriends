package me.glatteis.makefriends.logic.command;

import me.glatteis.makefriends.logic.ErrorThrower;
import me.glatteis.makefriends.logic.command.event.CommandEvent;
import me.glatteis.makefriends.objects.Robot;

/**
 * Created by Linus on 05.01.2016.
 */
public class TurnAllAliensIntoPixelartsOfJontron extends Command {

    public TurnAllAliensIntoPixelartsOfJontron(String args, Robot robot, ErrorThrower errorThrower) {
        super(args, robot, errorThrower);
    }

    @Override
    public void handleEvent(CommandEvent c) {

    }

    @Override
    public void execute() {
        robot.getGameScreen().getAlien1().turnIntoPixelartOfJontron();
        robot.getGameScreen().getAlien2().turnIntoPixelartOfJontron();
        suggestFinish();
    }
}
