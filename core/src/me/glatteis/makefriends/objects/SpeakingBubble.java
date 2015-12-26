package me.glatteis.makefriends.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by Linus on 24.12.2015.
 */
public class SpeakingBubble {

    private Stage stage;
    private Label bubble;

    public SpeakingBubble(String text, Vector2 position, boolean left) {
        stage = new Stage(new FitViewport(480, 320));

        Label.LabelStyle bubbleStyle = new Label.LabelStyle();
        bubbleStyle.background = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(
                left ? "textures/gui/bubble.png" : "textures/gui/bubble2.png"))));
        bubbleStyle.font = new BitmapFont(Gdx.files.internal("textures/fonts/couriernew/courier.fnt"));
        bubbleStyle.fontColor = Color.BLACK;

        bubble = new Label(text, bubbleStyle);

        bubble.setWrap(true);
        bubble.setAlignment(Align.center);
        bubble.setRotation(MathUtils.random(-40, 40));

        bubble.setBounds(position.x, position.y, 200, 100);

        stage.addActor(bubble);
    }

    public void setPosition(Vector2 pixelPosition) {
        bubble.setPosition(pixelPosition.x, pixelPosition.y);
    }

    public void render() {
        stage.act();
        stage.draw();
    }

    public void dispose() {
        stage.dispose();
    }

    public void update(int width, int height) {

    }

}
