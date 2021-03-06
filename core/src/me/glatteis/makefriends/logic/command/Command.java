package me.glatteis.makefriends.logic.command;

import com.badlogic.gdx.utils.Timer;
import me.glatteis.makefriends.logic.ErrorThrower;
import me.glatteis.makefriends.logic.command.event.CommandEvent;
import me.glatteis.makefriends.objects.Robot;

/**
 * Created by Linus on 21.12.2015.
 */
public abstract class Command {

    protected Float delay;
    protected Float currentDelay = null;
    protected String args;
    protected Robot robot;
    protected ErrorThrower errorThrower;
    private boolean until;
    private CommandFinishEvent e;

    public Command(String args, Robot robot, ErrorThrower errorThrower) {
        this.args = args;
        delay = null;
        this.robot = robot;
        this.errorThrower = errorThrower;
    }

    public Float getDelay() {
        return delay;
    }

    public void setDelay(float delay) {
        this.delay = delay;
    }

    public void setFinishEvent(CommandFinishEvent e) {
        this.e = e;
    }

    private void finish() {
        currentDelay = null;
        e.commandFinished();
    }

    protected void suggestFinish() {
        if (delay == null) Timer.post(new Timer.Task() {
            @Override
            public void run() {
                finish();
            }
        });
    }


    public void tick(float delta) {
        if (delay != null) {
            if (currentDelay == null) currentDelay = delay;
            currentDelay -= delta;
            if (currentDelay <= 0) {
                finish();
            }
        }
    }

    public String getCommandName() {
        return args.split(" ")[0];
    }

    public String getFullCommand() {
        return args;
    }

    public abstract void handleEvent(CommandEvent c);

    public abstract void execute();

}
