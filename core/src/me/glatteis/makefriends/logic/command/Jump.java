package me.glatteis.makefriends.logic.command;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Timer;
import me.glatteis.makefriends.logic.ErrorThrower;
import me.glatteis.makefriends.logic.command.event.CommandCollisionEvent;
import me.glatteis.makefriends.logic.command.event.CommandEvent;
import me.glatteis.makefriends.objects.Robot;

/**
 * Created by Linus on 23.12.2015.
 */
public class Jump extends Command {

    public Jump(String args, Robot robot, ErrorThrower errorThrower) {
        super(args, robot, errorThrower);
    }

    @Override
    public void handleEvent(CommandEvent c) {
        if (!(c instanceof CommandCollisionEvent)) return;
        CommandCollisionEvent commandCollisionEvent = (CommandCollisionEvent) c;
        Fixture[] fixtures = {commandCollisionEvent.f1, commandCollisionEvent.f2};
        for (int i = 0; i < 2; i++) {
            Fixture f1 = fixtures[i];
            Fixture f2 = fixtures[i == 0 ? 1 : 0];
            if (f1.getUserData() == null || f2.getUserData() == null) return;
            if (f1.getUserData() instanceof Robot && f2.getUserData().equals("ground")) {
                suggestFinish();
            }
        }
    }

    @Override
    public void execute() {
        String[] arguments = args.split(" ");
        float force;
        boolean c = false;
        if (arguments.length >= 1) {
            try {
                force = Float.valueOf(arguments[0]);
            } catch (Exception e) {
                force = 500;
            }
        } else {
            force = 500;
        }
        robot.getBody().applyForceToCenter(new Vector2(0, force), true);
        final float y = robot.getBody().getPosition().y;
        Timer t = new Timer();
        t.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                if (robot.getBody().getPosition().y <= y) {
                    suggestFinish();
                }
            }
        }, 1);
    }
}
