package me.glatteis.makefriends.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import me.glatteis.makefriends.logic.command.Command;
import me.glatteis.makefriends.logic.command.Jump;
import me.glatteis.makefriends.logic.command.Move;
import me.glatteis.makefriends.logic.command.Repeat;

import java.util.ArrayList;

/**
 * Created by Linus on 24.12.2015.
 */
public class Alien {

    private Pixmap meter;
    private Texture meterTexture;

    private Texture texture;

    private int happiness;

    private ArrayList<SpeakingBubble> bubbles;

    private AlienType type;

    private ArrayList<String> seenCommands;

    private String[] responses;

    public void turnIntoPixelartOfJontron() {
        texture = new Texture("textures/sprites/jontron" + (type.equals(AlienType.ART_ALIEN) ? "2" : "1") + ".png");
    }

    public Alien(AlienType type) {
        this.type = type;
        texture = new Texture(Gdx.files.internal("textures/sprites/alien" + (type.equals(AlienType.ART_ALIEN) ? "1" : "2") + ".png"));
        responses = type.equals(AlienType.ART_ALIEN) ? Gdx.files.internal("texts/alien.txt").readString().split("\n") : Gdx.files.internal("texts/ign.txt").readString().split("\n");

        bubbles = new ArrayList<SpeakingBubble>();
        meter = new Pixmap(Gdx.files.internal("textures/gui/happiness_meter.png"));
        meterTexture = new Texture(meter);
        updateMeter();
        seenCommands = new ArrayList<String>();
    }

    public void start() {
        bubbles.clear();
        happiness = 0;
        seenCommands.clear();
        updateMeter();
    }

    public void processCommand(Command command) {
        int multiple = (type.equals(AlienType.IGN_ALIEN)) ? -1 : 1;
        int commandScore = 0;
        if (command instanceof Repeat) {
            commandScore -= 2;
        } else if (command instanceof Move) {
            commandScore -= 1;
        } else if (command instanceof Jump) {
            commandScore += 2;
        } else {
            commandScore += 3;
        }
        if (seenCommands.contains(command.getFullCommand())) {
            if (type.equals(AlienType.ART_ALIEN)) commandScore -= (commandScore % 2) + 1;
        }
        commandScore *= multiple;
        happiness += commandScore;
        seenCommands.add(command.getFullCommand());
        System.out.println(type.toString() + " " + commandScore);
        updateMeter();
    }

    public void makeDecision() {
        final SpeakingBubble b;
        System.out.println(type);
        String review = responses[happiness > 10 ? 10 : happiness < 1 ? 0 : happiness - 1];
        if (type.equals(AlienType.IGN_ALIEN)) {
            b = new SpeakingBubble(review + "\n" + happiness + " / 10\nIGN", new Vector2(278, 150), false, true);
        } else {
            b = new SpeakingBubble(review, new Vector2(60, 150), true, true);
        }
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
        if (type.equals(AlienType.IGN_ALIEN)) {
            batch.draw(texture, 14, 3, 4, 4);
            batch.draw(meterTexture, 0, 0, 12.5f, 3.75f);
        } else {
            batch.draw(texture, -20, 3, 4, 4);
            batch.draw(meterTexture, -20, 0, 12.5f, 3.75f);
        }
        batch.end();
    }

    public void update(int width, int height) {
        for (SpeakingBubble b : bubbles) {
            b.update(width, height);
        }
    }

    public void updateMeter() {
        for (int i = 0; i < 10; i++) {
            if (happiness > i) {
                meter.setColor(0.1f, 1, 0.1f, 1);
            } else {
                meter.setColor(0, 0, 0, 1);
            }
            meter.fillRectangle(i * 10 + 2, 22, 6, 6);
        }
        meterTexture.draw(meter, 0, 0);
    }

    public enum AlienType {
        IGN_ALIEN, ART_ALIEN;
    }

}
