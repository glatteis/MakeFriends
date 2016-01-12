package me.glatteis.makefriends.logic.command;

import me.glatteis.makefriends.logic.ErrorThrower;
import me.glatteis.makefriends.logic.command.event.CommandEvent;
import me.glatteis.makefriends.objects.Robot;

/**
 * Created by Linus on 21.12.2015.
 */
public class Move extends Command {

    public Move(String args, Robot robot, ErrorThrower errorThrower) {
        super(args, robot, errorThrower);
    }

    @Override
    public void handleEvent(CommandEvent c) {

    }

    public void execute() {
        String[] allArgs = args.split(" ");
        try {
            float f = Float.valueOf(allArgs[0]);
            if (f > 0) {
                robot.setDirection(Robot.Direction.RIGHT);
            } else if (f < 0) {
                robot.setDirection(Robot.Direction.LEFT);
            }
            robot.getBody().setLinearVelocity(f, robot.getBody().getLinearVelocity().y);
            suggestFinish();
        } catch (Exception e) {
            errorThrower.throwError("Wrong move statement: " + args);
        }
    }
}