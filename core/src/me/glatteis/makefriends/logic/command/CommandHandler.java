package me.glatteis.makefriends.logic.command;

import me.glatteis.makefriends.logic.ErrorThrower;
import me.glatteis.makefriends.objects.Robot;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Linus on 21.12.2015.
 */
public class CommandHandler {

    private HashMap<WrapperCommand, List<Command>> wrappedCommands = new HashMap<WrapperCommand, List<Command>>();

    public Command getNewCommand(String key, ErrorThrower thrower, Robot robot) {
        Float delay = null;
        if (key.contains("FOR ")) {
            int index = key.indexOf("FOR ") + 4;
            try {
                delay = Float.valueOf(key.substring(index));
            } catch (Exception e) {
                thrower.throwError("That delay is not a number.");
            }
            key = key.substring(0, key.indexOf("FOR ") - 1);
        }
        try {
            final Command c;
            c = CommandLibrary.getInstanceOfCommand(key.split(" ")[0], key.substring(key.indexOf(" ") + 1), robot, thrower, wrappedCommands, delay);
            return c;
        } catch (Exception e) {
            return null;
        }
    }

}
