package me.glatteis.makefriends.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import me.glatteis.makefriends.objects.SoundHandler;
import me.glatteis.makefriends.screens.GameScreen;

/**
 * Created by Linus on 21.12.2015.
 */
public class CodeWindow {

    private boolean open;
    private Stage stage;
    private OrthographicCamera camera;
    private GameScreen gameScreen;

    private ScreenDim screenDim;

    private TextArea area;

    public CodeWindow(final GameScreen gameScreen) {
        this.gameScreen = gameScreen;

        stage = new Stage(new FitViewport(480, 320));
        camera = new OrthographicCamera(480, 320);
        camera.translate(480 / 2, 320 / 2);
        camera.update();
        //stage.getViewport().setCamera(camera);
        open = false;

        screenDim = new ScreenDim();

        Label.LabelStyle screenStyle = new Label.LabelStyle();
        screenStyle.font = new BitmapFont();
        screenStyle.background = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("textures/gui/screen.png"))));
        Label screen = new Label("", screenStyle);
        screen.setBounds(10, 0, 400, 300);
        stage.addActor(screen);

        TextField.TextFieldStyle style = new TextField.TextFieldStyle();

        BitmapFont font = new BitmapFont(Gdx.files.internal("textures/fonts/computerfont/computer.fnt"));
        //BitmapFont font = new BitmapFont(Gdx.files.internal("textures/fonts/couriernew/couriernew.fnt"));

        style.font = font;
        style.disabledFontColor = Color.GREEN;
        style.focusedFontColor = Color.GREEN;
        style.messageFontColor = Color.GREEN;
        style.fontColor = Color.GREEN;;

        System.out.println(Color.GREEN.toString());

        style.cursor = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("textures/fonts/computerfont/cursor.png"))));

        area = new TextArea("", style);
        area.setBounds(25, -10, 400, 300);
        area.setBlinkTime(0.8f);

        area.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                if (event instanceof ChangeListener.ChangeEvent) {
                    //ChangeListener.ChangeEvent e = (ChangeListener.ChangeEvent) event;
                    SoundHandler.KEY_SOUNDS[MathUtils.random(SoundHandler.KEY_SOUNDS.length - 1)].play();
                }
                return true;
            }
        });

        stage.setKeyboardFocus(area);

        stage.addActor(area);

        Button.ButtonStyle backStyle = new Button.ButtonStyle();
        backStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("textures/gui/done.png"))));

        Button back = new Button(backStyle);
        back.setBounds(430, 50, 50 ,50);
        back.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                if (event instanceof ChangeListener.ChangeEvent) {
                    open = false;
                    Gdx.input.setInputProcessor(gameScreen.getUiStage());
                }
                return true;
            }
        });


        stage.addActor(back);


    }

    public TextArea getArea() {
        return area;
    }

    public Stage getStage() {
        return stage;
    }

    public void setOpen(final boolean open1) {
        this.open = open1;
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
