package me.glatteis.makefriends.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Timer;
import me.glatteis.makefriends.logic.CodeInterpreter;
import me.glatteis.makefriends.screens.GameScreen;

import java.util.ArrayList;

/**
 * Created by Linus on 22.12.2015.
 */
public class Robot {

    private CodeInterpreter interpreter;
    private Body body;
    private Fixture fixture;
    private TextureRegion[] textures;
    private Sprite sprite;
    private Direction direction = Direction.RIGHT;
    private ArrayList<SpeakingBubble> bubbles;
    private GameScreen gameScreen;

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public CodeInterpreter getInterpreter() {
        return interpreter;
    }

    public GameScreen getGameScreen() {
        return gameScreen;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public Robot(World world, GameScreen gameScreen) {
        this.gameScreen = gameScreen;

        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(0, 7f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.friction = 0;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(2, 2);

        fixtureDef.shape = shape;

        body = world.createBody(def);

        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);

        interpreter = new CodeInterpreter(this, gameScreen);

        Texture texture = new Texture(Gdx.files.internal("textures/sprites/robotsprite.png"));
        textures = TextureRegion.split(texture, 32, 32)[0];

        sprite = new Sprite(textures[0]);

        sprite.setSize(4, 4);

        bubbles = new ArrayList<SpeakingBubble>();
    }

    public void render(SpriteBatch batch) {
        sprite.setRegion(textures[direction == Direction.LEFT ? 1 : 0]);
        sprite.setPosition(body.getWorldCenter().x - 2, body.getWorldCenter().y - 2);
        sprite.setRotation(body.getAngle() * MathUtils.radDeg);
        batch.begin();
        sprite.draw(batch);
        batch.end();
        for (SpeakingBubble b : bubbles) b.render();
    }

    public void saySomething(String text, boolean error) {
        Vector2 position;
        if (error){
            position = new Vector2(180, 240);
        } else {
            position = interpreter.getGameScreen().getViewport().project(body.getPosition().add(-3, -3));
        }
        final SpeakingBubble speechBubble = new SpeakingBubble(text, position, true, false);
        bubbles.add(speechBubble);
        new Timer().scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                speechBubble.dispose();
                bubbles.remove(speechBubble);
            }
        }, 3);
    }

    public Body getBody() {
        return body;
    }

    public Fixture getFixture() {
        return fixture;
    }

    public enum Direction {
        LEFT, RIGHT;
    }

    public void update(int width, int height) {
        for (SpeakingBubble b : bubbles) {
            b.update(width, height);
        }
        interpreter.update(width, height);
    }

}
