package me.glatteis.makefriends.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
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

    private Pixmap meter;
    private Texture meterTexture;

    private int responseCode;

    private int happiness;

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
        meter = new Pixmap(Gdx.files.internal("textures/gui/happiness_meter.png"));
        meterTexture = new Texture(meter);
        seenCommands = new ArrayList<String>();
        updateMeter();
    }

    public void start() {
        bubbles.clear();
        responseCode = 0;
        seenCommands.clear();
        happiness = 0;
        updateMeter();
        System.out.println("Alien started!");
    }

    public void processCommand(Command command) {
        responseCode += command.getFullCommand().hashCode();
        if (! seenCommands.contains(command)) {
            happiness++;
        }
        updateMeter();
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
        batch.draw(meterTexture, 0, 0, 12.5f, 3.75f);
        batch.end();
    }

    public void update(int width, int height) {
        for (SpeakingBubble b : bubbles) {
            b.update(width, height);
        }
    }

    public void updateMeter() {
        for (int i = 0; i < 10; i++) {
            if (happiness >= i) {
                meter.setColor(0.1f, 1, 0.1f, 1);
            } else {
                meter.setColor(0, 0, 0, 1);
            }
            meter.fillRectangle(i * 10 + 2, 22, 6, 6);
        }
        meterTexture.draw(meter, 0, 0);
    }



}
