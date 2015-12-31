package me.glatteis.makefriends.logic;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import me.glatteis.makefriends.logic.command.Command;
import me.glatteis.makefriends.logic.command.CommandFinishEvent;
import me.glatteis.makefriends.logic.command.CommandHandler;
import me.glatteis.makefriends.logic.command.event.CommandEvent;
import me.glatteis.makefriends.objects.Robot;
import me.glatteis.makefriends.screens.GameScreen;

/**
 * Created by Linus on 21.12.2015.
 */
public class CodeInterpreter {

    private ErrorThrower thrower;
    private CommandHandler commandHandler;
    private Robot robot;
    private GameScreen gameScreen;

    private Command[] commands;
    String[] commandStrings;
    private int currentCommand;

    private boolean running;

    public boolean isRunning() {
        return running;
    }

    public CodeInterpreter(Robot robot, GameScreen gameScreen) {
        commandHandler = new CommandHandler();
        thrower = new ErrorThrower(this, robot);
        this.robot = robot;
        this.gameScreen = gameScreen;
    }

    public void tick(float delta) {
        if (running) commands[currentCommand].tick(delta);
    }


    public void interpretThis(String code) {
        code = code.toUpperCase();
        commandStrings = code.split("\n");
        commands = new Command[commandStrings.length];

        currentCommand = 0;

        for (int i = 0; i < commandStrings.length; i++) {
            thrower.setLine(i);
            Command c = commandHandler.getNewCommand(commandStrings[i], thrower, robot);
            if (c == null) {
                thrower.throwError("I don't know that command : " + commandStrings[i]);
                return;
            }
            commands[i] = c;
        }

        next();

    }

    public void handleEvent(CommandEvent e) {
        if (running) {
            commands[currentCommand].handleEvent(e);
        }
    }

    private void next() {
        System.out.println(currentCommand);

        running = true;

        if (currentCommand >= commands.length) {
            Timer.post(new Timer.Task() {
                @Override
                public void run() {
                    finish(false);
                }
            });
            return;
        }

        Command c = commands[currentCommand];
        System.out.println(c);
        c.setFinishEvent(new CommandFinishEvent() {
            @Override
            public void commandFinished() {
                currentCommand++;
                if (currentCommand == commands.length) {
                    finish(false);
                    return;
                }
                next();
            }
        });
        gameScreen.getAlien().processCommand(c);
        c.execute();


        if (currentCommand == commands.length) {
            return;
        }
    }

    public void finish(final boolean failure) {
        running = false;
        Timer.post(new Timer.Task() {
            @Override
            public void run() {
                robot.getBody().setTransform(new Vector2(0, 7), 0);
                robot.getBody().setLinearVelocity(0, 0);
                gameScreen.programFinished(failure);
            }
        });

    }

    public GameScreen getGameScreen() {
        return gameScreen;
    }

}
