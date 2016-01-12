package me.glatteis.makefriends.logic.command;

import me.glatteis.makefriends.logic.ErrorThrower;
import me.glatteis.makefriends.logic.command.event.CommandEvent;
import me.glatteis.makefriends.objects.Robot;

/**
 * Created by Linus on 31.12.2015.
 */
public class Repeat extends WrapperCommand {

    private int timesExecuted = 0;
    private int repeatTimes = -1;
    private int commandIndex;
    private boolean stopped = false;

    public Repeat(String args, Robot robot, ErrorThrower errorThrower, int level) {
        super(args, robot, errorThrower, level);
    }

    @Override
    public void stop() {
        stopped = true;
    }

    @Override
    public void tick(float delta) {
        if (commands.size() > commandIndex) commands.get(commandIndex).tick(delta);
    }

    @Override
    public void handleEvent(CommandEvent c) {
        if (commands.size() > commandIndex) commands.get(commandIndex).handleEvent(c);
    }

    @Override
    public void execute() {
        if (commands == null) {
            errorThrower.throwError("No ENDREPEAT called.");
            return;
        }
        timesExecuted = 0;
        try {
            repeatTimes = Integer.valueOf(args.split(" ")[0]);
        } catch (NumberFormatException e) {
            errorThrower.throwError("Wrong REPEAT statement: ");
            return;
        }
        if (repeatTimes == -1) {
            errorThrower.throwError("Wrong REPEAT statement: ");
            return;
        }
        commandIndex = 0;
        next();
    }

    private void executeNext() {
        timesExecuted++;
        if (timesExecuted >= repeatTimes || commands.size() == 0) {
            suggestFinish();
            return;
        }
        commandIndex = 0;
        next();
    }

    private void next() {
        if (commands.size() <= commandIndex) executeNext();
        final Command command = commands.get(commandIndex);
        command.setFinishEvent(new CommandFinishEvent() {
            @Override
            public void commandFinished() {
                if (stopped) {
                    suggestFinish();
                    return;
                }
                commandIndex++;
                if (commandIndex >= commands.size()) executeNext();
                else next();
            }
        });
        command.execute();
        robot.getInterpreter().getGameScreen().getAlien1().processCommand(command);
        robot.getInterpreter().getGameScreen().getAlien2().processCommand(command);
    }
}
