package me.glatteis.makefriends.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by Linus on 23.12.2015.
 */
public class WorldCreator {

    public static World createWorld() {
        World w = new World(new Vector2(0, -9.81f), true);

        BodyDef groundDef = new BodyDef();
        groundDef.type = BodyDef.BodyType.StaticBody;
        FixtureDef groundFixture = new FixtureDef();
        PolygonShape groundShape = new PolygonShape();
        groundShape.setAsBox(100, 1);
        groundFixture.shape = groundShape;
        groundFixture.friction = 0;
        groundDef.position.set(new Vector2(0, 4));
        Body ground = w.createBody(groundDef);
        ground.createFixture(groundFixture).setUserData("ground");

        return w;
    }

}
