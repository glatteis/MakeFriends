package me.glatteis.makefriends.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by Linus on 26.12.2015.
 */
public class Star extends Image {

    private Vector2 velocity;
    private boolean temporary;
    private float boundsDownY;

    public Star(Vector2 velocity, boolean temporary, float boundsDownY) {
        this.velocity = velocity;
        this.temporary = temporary;
        this.boundsDownY = boundsDownY;
    }

    public void act(float delta) {
        setPosition(getX() + velocity.x * delta, getY() + velocity.y * delta);
        if (temporary)  {
            if (getX() < -(getWidth() * 1.1f) || getX() > getStage().getWidth() + (getWidth() * 1.1f) ||
                    getY() < boundsDownY - (getHeight() * 1.1f) || getY() > getStage().getHeight() + (getHeight() * 1.1f)) {
                remove();
            }
        } else {
            if (getX() < -(getWidth() * 1.1f)) setX(getStage().getWidth() + (getWidth() * 1.1f));
            if (getX() > getStage().getWidth() + (getWidth() * 1.1f)) setX(-(getWidth() * 1.1f));
            if (getY() < boundsDownY - (getHeight() * 1.1f)) setY(getStage().getHeight() + (getHeight() * 1.1f));
            if (getY() > getStage().getHeight() + (getHeight() * 1.1f)) setY(boundsDownY - (getHeight() * 1.1f));
        }

    }

}
