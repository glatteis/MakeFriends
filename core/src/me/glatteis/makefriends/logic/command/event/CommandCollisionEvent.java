package me.glatteis.makefriends.logic.command.event;

import com.badlogic.gdx.physics.box2d.Fixture;

/**
 * Created by Linus on 23.12.2015.
 */
public class CommandCollisionEvent extends CommandEvent {

    public Fixture f1;
    public Fixture f2;

    public CommandCollisionEvent(Fixture f1, Fixture f2) {
        this.f1 = f1;
        this.f2 = f2;
    }

}
