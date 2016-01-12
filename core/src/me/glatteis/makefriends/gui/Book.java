package me.glatteis.makefriends.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import me.glatteis.makefriends.objects.SoundHandler;
import me.glatteis.makefriends.screens.GameScreen;

/**
 * Created by Linus on 24.12.2015.
 */
public class Book {

    private boolean open;
    private Stage stage;
    private OrthographicCamera camera;

    private String[] pages;
    private Label label;
    private int current;

    private ScreenDim screenDim;


    public Book(final GameScreen gameScreen) {
        stage = new Stage(new FitViewport(480, 320));
        camera = new OrthographicCamera(480, 320);
        camera.translate(480 / 2, 320 / 2);
        camera.update();
        //stage.getViewport().setCamera(camera);
        open = false;

        screenDim = new ScreenDim();

        loadDocs();

        current = 0;

        final Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont(Gdx.files.internal("textures/fonts/couriernew/courier.fnt"));
        labelStyle.background = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("textures/gui/page.png"))));
        labelStyle.fontColor = Color.BLACK;

        label = new Label(pages[0], labelStyle);
        label.setBounds(90, 10, 300, 300);
        label.setAlignment(Align.center);
        label.setWrap(true);

        stage.addActor(label);

        Button.ButtonStyle forwardStyle = new Button.ButtonStyle();
        forwardStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("textures/gui/forward.png"))));
        Button.ButtonStyle backwardStyle = new Button.ButtonStyle();
        backwardStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("textures/gui/back.png"))));

        Button forward = new Button(forwardStyle);
        forward.setBounds(430, 40, 50, 50);
        forward.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                if (event instanceof ChangeListener.ChangeEvent) {
                    SoundHandler.PAGE_TURN.play(1);
                    current++;
                    if (current == pages.length) {
                        current = 0;
                    }
                    label.setText(pages[current]);
                }
                return true;
            }
        });
        stage.addActor(forward);

        Button back = new Button(backwardStyle);
        back.setBounds(0, 40, 50, 50);
        back.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                if (event instanceof ChangeListener.ChangeEvent) {
                    SoundHandler.PAGE_TURN.play(1);
                    current--;
                    if (current == -1) {
                        current = pages.length - 1;
                    }
                    label.setText(pages[current]);
                }
                return true;
            }
        });
        stage.addActor(back);

        Button.ButtonStyle backStyle = new Button.ButtonStyle();
        backStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("textures/gui/done.png"))));

        Button backButton = new Button(backStyle);
        backButton.setBounds(430, 150, 50 ,50);
        backButton.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                if (event instanceof ChangeListener.ChangeEvent) {
                    open = false;
                    Gdx.input.setInputProcessor(gameScreen.getUiStage());
                }
                return true;
            }
        });
        stage.addActor(backButton);
    }

    private void loadDocs() {
        String s = Gdx.files.internal("texts/docs.txt").readString();
        System.out.println(s);
        pages = s.split("/s");
        for (String string : pages) {
            System.out.println(string);
        }
        System.out.println(pages.length);
    }

    public Stage getStage() {
        return stage;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isOpen() {
        return open;
    }

    public void render(float delta) {
        if (!open) return;
        screenDim.renderDim(0.7f);
        stage.act(delta);
        stage.draw();
    }

    public void update(int screenWidth, int screenHeight) {
        stage.getViewport().update(screenWidth, screenHeight);
    }


}
