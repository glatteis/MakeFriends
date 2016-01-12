package me.glatteis.makefriends.logic.command.event;

/**
 * Created by Linus on 05.01.2016.
 */
public class CommandRenderEvent extends CommandEvent {
    public final float delta;
    public CommandRenderEvent(float delta) {
        this.delta = delta;
    }
}
