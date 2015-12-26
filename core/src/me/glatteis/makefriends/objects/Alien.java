package me.glatteis.makefriends.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import me.glatteis.makefriends.logic.command.Command;

import java.util.ArrayList;

/**
 * Created by Linus on 24.12.2015.
 */
public class Alien {

    private int responseCode;

    private ArrayList<String> seenCommands;

    private TextureRegion texture;

    private ArrayList<SpeakingBubble> bubbles;

    private String[] responses;

    public Alien() {
        Texture alienTexture = new Texture(Gdx.files.internal("textures/sprites/aliens.png"));
        TextureRegion[] textures = TextureRegion.split(alienTexture, 32, 32)[0];
        texture = textures[MathUtils.random(textures.length - 1)];
        responses = Gdx.files.internal("texts/alienResponses.txt").readString().split("\n");
        bubbles = new ArrayList<SpeakingBubble>();
    }

    public void start() {
        bubbles.clear();
        responseCode = 0;
        seenCommands = new ArrayList<String>();
    }

    public void processCommand(Command command) {
        responseCode += command.getFullCommand().hashCode();
    }

    public void makeDecision() {
        String s = responses[responseCode % responses.length];
        final SpeakingBubble b = new SpeakingBubble(s, new Vector2(180, 150), false);
        bubbles.add(b);
        Timer t = new Timer();
        t.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                b.dispose();
                bubbles.remove(b);
            }
        }, 5);
    }

    public void render(SpriteBatch batch) {
        for (SpeakingBubble b : bubbles) {
            b.render();
        }
        batch.begin();
        batch.draw(texture, 14, 3, 4, 4);
        batch.end();
    }

    public void update(int width, int height) {
        for (SpeakingBubble b : bubbles) {
            b.update(width, height);
        }
    }



}
