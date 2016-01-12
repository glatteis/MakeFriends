package me.glatteis.makefriends.logic;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import me.glatteis.makefriends.logic.command.Command;
import me.glatteis.makefriends.logic.command.CommandFinishEvent;
import me.glatteis.makefriends.logic.command.CommandHandler;
import me.glatteis.makefriends.logic.command.WrapperCommand;
import me.glatteis.makefriends.logic.command.event.CommandEvent;
import me.glatteis.makefriends.logic.command.event.CommandRenderEvent;
import me.glatteis.makefriends.logic.command.event.CommandStopEvent;
import me.glatteis.makefriends.objects.Robot;
import me.glatteis.makefriends.screens.GameScreen;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Linus on 21.12.2015.
 */
public class CodeInterpreter {

    private ErrorThrower thrower;
    private CommandHandler commandHandler;
    private Robot robot;
    private GameScreen gameScreen;
    protected Stage commandStage;

    private List<Command> commands;
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
        commandStage = new Stage(new FitViewport(480, 320));
    }

    public void tick(float delta) {
        if (running) commands.get(currentCommand).tick(delta);
    }


    public void interpretThis(String code) {
        code = code.toUpperCase();
        commandStrings = code.split("\n");
        commands = new ArrayList<Command>();

        currentCommand = 0;

        for (int i = 0; i < commandStrings.length; i++) {
            thrower.setLine(i);
            Command c = commandHandler.getNewCommand(commandStrings[i], thrower, robot);
            if (c == null) continue;
            System.out.println("Adding " + c.getFullCommand());
            commands.add(c);
        }
        if (commands.size() == 0) {
            finish(false);
            return;
        }
        next();

    }

    public void handleEvent(CommandEvent e) {
        if (running) {
            commands.get(currentCommand).handleEvent(e);
        }
    }

    private void next() {
        running = true;

        if (currentCommand >= commands.size()) {
            Timer.post(new Timer.Task() {
                @Override
                public void run() {
                    finish(false);
                }
            });
            return;
        }

        System.out.println(commands.toString());

        Command c = commands.get(currentCommand);
        c.setFinishEvent(new CommandFinishEvent() {
            @Override
            public void commandFinished() {
                currentCommand++;
                if (currentCommand >= commands.size()) {
                    finish(false);
                    return;
                }
                next();
            }
        });
        gameScreen.getAlien1().processCommand(c);
        gameScreen.getAlien2().processCommand(c);
        c.execute();


        if (currentCommand == commands.size()) {
            finish(false);
        }
    }

    public void finish(final boolean failure) {
        running = false;
        Timer.post(new Timer.Task() {
            @Override
            public void run() {
                if (currentCommand < commands.size()) {
                    if (commands.get(currentCommand) instanceof WrapperCommand) {
                        ((WrapperCommand)commands.get(currentCommand)).stop();
                    }
                }
                for (Command c : commands) {
                    c.handleEvent(new CommandStopEvent());
                }
                robot.getBody().setTransform(new Vector2(0, 7), 0);
                robot.getBody().setLinearVelocity(0, 0);
                gameScreen.programFinished(failure);
                commandStage.clear();
            }
        });

    }

    public GameScreen getGameScreen() {
        return gameScreen;
    }

    public void update(int width, int height) {
        commandStage.getViewport().update(width, height);
    }

    public void renderStage(float delta) {
        commandStage.act(delta);
        commandStage.draw();
        if (commands != null && isRunning()) {
            CommandRenderEvent renderEvent = new CommandRenderEvent(delta);
            for (Command c : commands) {
                c.handleEvent(renderEvent);
            }
        }

    }

}
