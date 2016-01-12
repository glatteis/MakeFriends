package me.glatteis.makefriends.logic.command;

import me.glatteis.makefriends.logic.ErrorThrower;
import me.glatteis.makefriends.objects.Robot;

import java.util.List;

/**
 * Created by Linus on 31.12.2015.
 */
public abstract class WrapperCommand extends Command {

    protected List<Command> commands;
    private int level;

    public WrapperCommand(String args, Robot robot, ErrorThrower errorThrower, int level) {
        super(args, robot, errorThrower);
        this.level = level;
    }

    public void setCommands(List<Command> commands) {
        this.commands = commands;
    }

    public int getLevel() {
        return level;
    }

    public abstract void stop();

}
