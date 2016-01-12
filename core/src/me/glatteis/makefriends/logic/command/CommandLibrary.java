package me.glatteis.makefriends.logic.command;

import me.glatteis.makefriends.logic.ErrorThrower;
import me.glatteis.makefriends.objects.Robot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Linus on 22.12.2015.
 */
public class CommandLibrary {

    public static Command getInstanceOfCommand(String command, String args, Robot robot, ErrorThrower errorThrower, HashMap<WrapperCommand, List<Command>> wrappedCommands, Float delay) {
        boolean ignoreNull = false;
        Command commandToReturn = null;
        if (command.equals("MOVE")) {
            commandToReturn = new Move(args, robot, errorThrower);
        } else if (command.equals("JUMP")) {
            commandToReturn = new Jump(args, robot, errorThrower);
        } else if (command.equals("STOP")) {
            commandToReturn = new Stop(args, robot, errorThrower);
        } else if (command.equals("DIMSCREEN")) {
            commandToReturn = new DimScreen(args, robot, errorThrower);
        } else if (command.equals("JOHNCENA")) {
            commandToReturn = new JohnCena(args,  robot, errorThrower);
        } else if (command.equals("TURNALLALIENSINTOPIXELARTSOFJONTRON")) {
            commandToReturn = new TurnAllAliensIntoPixelartsOfJontron(args, robot, errorThrower);
        } else if (command.equals("REPEAT")) {
            Repeat repeat = new Repeat(args, robot, errorThrower, wrappedCommands.size());
            wrappedCommands.put(repeat, new ArrayList<Command>());
            commandToReturn = null;
            ignoreNull = true;
        } else if (command.equals("END")) {
            int biggestSize = -1;
            WrapperCommand wrapperCommand = null;
            for (WrapperCommand w : wrappedCommands.keySet()) {
                if (w instanceof Repeat && w.getLevel() > biggestSize) {
                    wrapperCommand = w;
                    biggestSize = w.getLevel();
                }
            }
            if (wrapperCommand == null) {
                errorThrower.throwError("You called END, but you didn't call a wrapper command beforehand.");
                return null;
            }
            wrapperCommand.setCommands(wrappedCommands.get(wrapperCommand));
            wrappedCommands.remove(wrapperCommand);
            commandToReturn = wrapperCommand;
        }
        if (commandToReturn == null && !ignoreNull) {
            errorThrower.throwError("Robot doesn't know that command: " + command);
            return null;
        }
        if (delay != null && !(commandToReturn instanceof WrapperCommand) && !ignoreNull) {
            commandToReturn.setDelay(delay);
        }
        if (!wrappedCommands.isEmpty() && !ignoreNull) {
            WrapperCommand highestWrapper = null;
            int highestLevel = -1;
            for (WrapperCommand w : wrappedCommands.keySet()) {
                if (w.getLevel() > highestLevel) highestWrapper = w;
            }
            if (!commandToReturn.equals(highestWrapper)) wrappedCommands.get(highestWrapper).add(commandToReturn);
        }
        if (wrappedCommands.isEmpty()) return commandToReturn;
        else return null;
    }

}
