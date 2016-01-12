package me.glatteis.makefriends.logic.command;

import me.glatteis.makefriends.gui.ScreenDim;
import me.glatteis.makefriends.logic.ErrorThrower;
import me.glatteis.makefriends.logic.command.event.CommandEvent;
import me.glatteis.makefriends.logic.command.event.CommandRenderEvent;
import me.glatteis.makefriends.objects.Robot;

/**
 * Created by Linus on 05.01.2016.
 */
public class DimScreen extends Command {

    ScreenDim dim;
    float dimLevel;

    public DimScreen(String args, Robot robot, ErrorThrower errorThrower) {
        super(args, robot, errorThrower);
    }

    @Override
    public void handleEvent(CommandEvent c) {
        if (c instanceof CommandRenderEvent) {
            if (dim != null) dim.renderDim(dimLevel);
        }
    }

    @Override
    public void execute() {
        String[] allArgs = args.split(" ");
        try {
            dimLevel = Float.valueOf(allArgs[0]);
            dim = new ScreenDim();
            suggestFinish();
        } catch (Exception e) {
            e.printStackTrace();
            errorThrower.throwError("Wrong DIMSCREEN usage: " + args);
        }
    }
}
