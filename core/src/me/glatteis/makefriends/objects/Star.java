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
            if (getX() < -10 || getX() > getStage().getWidth() + 10 || getY() < boundsDownY - 10 || getY() > getStage().getHeight() + 10) {
                remove();
            }
        } else {
            if (getX() < -10) setX(getStage().getWidth() + 10);
            if (getX() > getStage().getWidth() + 10) setX(-10);
            if (getY() < boundsDownY - 10) setY(getStage().getHeight() + 10);
            if (getY() > getStage().getHeight() + 10) setY(boundsDownY - 10);
        }

    }

}
