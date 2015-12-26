package me.glatteis.makefriends.logic.command;

import me.glatteis.makefriends.logic.ErrorThrower;
import me.glatteis.makefriends.objects.Robot;

/**
 * Created by Linus on 22.12.2015.
 */
public class CommandLibrary {

    public static Command getInstanceOfCommand(String command, String args, Robot robot, ErrorThrower errorThrower) {
        if (command.equals("MOVE")) {
            return new Move(args, robot, errorThrower);
        } else if (command.equals("JUMP")) {
            return  new Jump(args, robot, errorThrower);
        } else if (command.equals("STOP")) {
            return new Stop(args, robot, errorThrower);
        }
        return null;
    }

}
