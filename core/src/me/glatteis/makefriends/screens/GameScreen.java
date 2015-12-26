package me.glatteis.makefriends.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import me.glatteis.makefriends.gui.Book;
import me.glatteis.makefriends.gui.CodeWindow;
import me.glatteis.makefriends.logic.command.event.CommandCollisionEvent;
import me.glatteis.makefriends.objects.Alien;
import me.glatteis.makefriends.objects.BackgroundRenderer;
import me.glatteis.makefriends.objects.Robot;
import me.glatteis.makefriends.objects.WorldCreator;

/**
 * Created by Linus on 21.12.2015.
 */
public class GameScreen implements Screen, ContactListener {

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Viewport viewport;
    private CodeWindow codeWindow;
    private Book book;
    private World world;
    private Robot robot;
    private Box2DDebugRenderer debugRenderer;
    private BackgroundRenderer backgroundRenderer;
    private Alien alien;

    private Stage uiStage;
    private Button edit;
    private Button playPause;
    private Button bookButton;
    private Button.ButtonStyle playStyle;
    private Button.ButtonStyle pauseStyle;
    private Button.ButtonStyle bookStyle;
    private Button.ButtonStyle editStyle;

    public Alien getAlien() {
        return alien;
    }

    public Stage getUiStage() {
        return uiStage;
    }

    public Viewport getViewport() {
        return viewport;
    }

    @Override
    public void show() {
        camera = new OrthographicCamera(48.0f, 32.0f);
        camera.zoom = 0.1f;
        camera.position.set(0, 8, 0);
        camera.update();
        viewport = new FitViewport(480, 320, camera);
        codeWindow = new CodeWindow(this);
        book = new Book(this);
        world = WorldCreator.createWorld();
        world.setContactListener(this);
        robot = new Robot(world, this);
        alien = new Alien();
        debugRenderer = new Box2DDebugRenderer();
        uiStage = new Stage(new FitViewport(480, 320));
        batch = new SpriteBatch();
        backgroundRenderer = new BackgroundRenderer();
        createStage();
        Gdx.input.setInputProcessor(uiStage);
    }

    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(camera.combined);
        world.step(delta, 8, 2);
        robot.getInterpreter().tick(delta);
        backgroundRenderer.render(delta);
        //debugRenderer.render(world, camera.combined);
        alien.render(batch);
        robot.render(batch);
        uiStage.act(delta);
        uiStage.draw();
        codeWindow.render(delta);
        book.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        codeWindow.update(width, height);
        book.update(width, height);
        viewport.update(width, height);
        uiStage.getViewport().update(width, height);
        backgroundRenderer.update(width, height);
    }

    public void programFinished(boolean failure) {
        playPause.setStyle(playStyle);
        edit.setVisible(true);
        bookButton.setVisible(true);
        if (!failure) alien.makeDecision();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    private void createStage() {
        editStyle = new Button.ButtonStyle();
        editStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("textures/gui/code.png"))));
        bookStyle = new Button.ButtonStyle();
        bookStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("textures/gui/book.png"))));
        edit = new Button(editStyle);
        edit.setBounds(400, 50, 50, 50);
        edit.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                if (event instanceof ChangeListener.ChangeEvent) {
                    codeWindow.setOpen(true);
                    Gdx.input.setInputProcessor(codeWindow.getStage());
                }
                return true;
            }
        });
        uiStage.addActor(edit);
        bookButton = new Button(bookStyle);
        bookButton.setBounds(400, 250, 50, 50);
        bookButton.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                if (event instanceof ChangeListener.ChangeEvent) {
                    book.setOpen(true);
                    Gdx.input.setInputProcessor(book.getStage());
                }
                return true;
            }
        });
        uiStage.addActor(bookButton);
        pauseStyle = new Button.ButtonStyle();
        pauseStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("textures/gui/pause.png"))));
        playStyle = new Button.ButtonStyle();
        playStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("textures/gui/start.png"))));
        playPause = new Button(playStyle);
        playPause.setBounds(400, 150, 50, 50);
        playPause.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                if (event instanceof ChangeListener.ChangeEvent) {
                    if (robot.getInterpreter().isRunning()) {
                        robot.getInterpreter().finish(true);
                    } else {
                        robot.getInterpreter().interpretThis(codeWindow.getArea().getText());
                        playPause.setStyle(pauseStyle);
                        edit.setVisible(false);
                        bookButton.setVisible(false);
                        alien.start();
                    }
                }
                return true;
            }
        });
        uiStage.addActor(playPause);

    }

    @Override
    public void beginContact(Contact contact) {
        robot.getInterpreter().handleEvent(new CommandCollisionEvent(contact.getFixtureA(), contact.getFixtureB()));
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
