import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;

public class MainMenuScreen implements Screen {
    
    private OrthographicCamera camera; //the camera to our world
    private Viewport viewport; //maintains the ratios of your world
    private SpriteBatch batch; //used to draw textures and fonts 
    private BitmapFont font; //used to draw fonts

    public static float WORLD_WIDTH = 800; //world units for width 
    public static float WORLD_HEIGHT = 480; //world units for height
    //keep the ratio of your screen and your world the same
    
    private DropGame game; //since it extends Game we can call methods from the Game class
    //such as setScreen, remember ctrl + space will show you all the methods you can call
    //also get used to looking up the libgdx API for classes
    
    public MainMenuScreen(DropGame game)
    {
        this.game = game; 
    }
    
    //show is called when the screen is set, initialize most objects here
    @Override
    public void show() {
        camera = new OrthographicCamera();//create our camera
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera); //tell our viewport about
        //the camera and the world's dimensions so it can maintain the aspect ratio
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("capsmall40.fnt"));//pass the name of your font
        //that exists in your project folder
        font.setColor(Color.RED); 
    }

    //the is being called 60 times a second, delta is 1.0 / 60.0
    @Override
    public void render(float delta) {
        viewport.apply(); //always apply your viewport
        
        //these two lines are needed to reset your screen every frame
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

       
        batch.setProjectionMatrix(viewport.getCamera().combined);//tell the spritebatch about 
        //the camera. So coordinates from the screen get mapped correctly to the world

        //whenever using SpriteBatch to draw, everything needs to be between a begin and
        //end call. This is to maximize efficiency to the GPU
        batch.begin();
        font.draw(batch, "Welcome to Drop!!! ", 100, 150);
        font.draw(batch, "Tap anywhere to begin", 100, 100);
        batch.end();
        
        
        //This is a call to check if the mouse is clicked, there is also
        //a method called Gdx.input.justTouched(), hit ctrl space to see all the others
        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen());
        }
    }

    //this is called everytime the window is resized
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);//adding true makes (0,0) the bottom left corner 
        //of the window
    }

    //this disposes your assets and get called when a new screen is set
    //this is done to prevent memory leaks and not rely on the garbage collector
    @Override
    public void hide() {
        batch.dispose();
        font.dispose();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        
    }
}