package me.glatteis.makefriends.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Linus on 24.12.2015.
 */
public class BackgroundRenderer {

    private Texture background;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Viewport viewport;

    private float shootingStarDelay;

    private Stage starStage;

    public BackgroundRenderer() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        viewport = new FitViewport(480, 320);
        viewport.setCamera(camera);
        background = new Texture(Gdx.files.internal("textures/stages/background.png"));

        starStage = new Stage(viewport);

        for (int i = 0; i < 50; i++) {
            Star s = new Star(new Vector2(MathUtils.random(-0.3f, 0.3f), MathUtils.random(-0.3f, 0.3f)), false, 180);
            s.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("textures/stars/star.png")))));
            s.setColor(MathUtils.random(0.95f, 1), MathUtils.random(0.95f, 1), MathUtils.random(0.95f, 1), MathUtils.random(0.7f, 1));
            int radius = MathUtils.random(2, 6);
            s.setBounds(MathUtils.random(480), MathUtils.random(180, 320), radius, radius);
            starStage.addActor(s);
        }

        for (int i = 0; i < 6; i++) {
            Star cloud = new Star(new Vector2(MathUtils.random(-7, 3) * (MathUtils.randomBoolean() ? -1 : 1), 0), false, 180);
            cloud.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("textures/stars/cloud.png")))));
            cloud.setColor(MathUtils.random(), MathUtils.random(), MathUtils.random(), 0.4f);
            float width = MathUtils.random(50, 150);
            cloud.setBounds(MathUtils.random(480), MathUtils.random(180, 320),width, width * 0.6f);
            starStage.addActor(cloud);
        }

        shootingStarDelay = 5;

    }

    public void render(float delta) {
        shootingStarDelay -= delta;
        if (shootingStarDelay <= 0) {
            makeShootingStar();
            shootingStarDelay = MathUtils.random(0.1f, 15);
        }

        starStage.act(delta);
        starStage.draw();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(background, 0, 0, 480, 320);
        batch.end();
    }

    public void update(int width, int height) {
        viewport.update(width, height, true);
    }

    public void makeShootingStar() {
        Vector2 velocity = new Vector2(MathUtils.random(25, 100) * (MathUtils.randomBoolean() ? -1 : 1), MathUtils.random(20, 40) * -1);
        Star shootingStar = new Star(velocity, true, 180);
        shootingStar.setBounds(MathUtils.random(0, 480), 320, 20, 10);
        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("textures/stars/shootingstar.png"))));
        shootingStar.setColor(MathUtils.random(), MathUtils.random(), MathUtils.random(), 1);
        if (velocity.x > 0) {
            drawable.getRegion().flip(true, false);
        }
        shootingStar.setDrawable(drawable);
        starStage.addActor(shootingStar);
    }

}
