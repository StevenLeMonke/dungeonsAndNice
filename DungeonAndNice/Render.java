import com.badlogic.gdx.ApplicationAdapter; 
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer; 
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle; 

import com.badlogic.gdx.Input.Keys; 
import com.badlogic.gdx.math.Vector2; 
import com.badlogic.gdx.math.MathUtils; 
import com.badlogic.gdx.math.Intersector; 
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.*; 
import java.util.*; 

public class Render extends ApplicationAdapter
{
    private OrthographicCamera camera; //the camera to our world
    private Viewport viewport; //maintains the ratios of your world
    private ShapeRenderer renderer; //used to draw textures and fonts 

    private GameState gamestate;
    private Player player;

    private int gameTime;
    private Tile[][] map;

    @Override//called once when we start the game
    public void create()
    {
        camera = new OrthographicCamera(); 
        viewport = new FitViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, camera); 
        renderer = new ShapeRenderer(); 
        gamestate = GameState.MENU;

        player = new Player();
        gameTime = 0;
        map = new Tile[15][15];
    }

    @Override//game loop - gets called 60 times a second
    public void render()
    {
        viewport.apply();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        int x = player.getX();
        int y = player.getY();

        if(gamestate == GameState.GAME)
        {
            if(gameTime > map[x][y].getTime())
            {
                if(Gdx.input.isKeyJustPressed(Keys.W) && map[x][y+1].isTraversable())
                {player.moveY(1);}
                if(Gdx.input.isKeyJustPressed(Keys.A) && map[x-1][y].isTraversable())
                {player.moveX(-1);}
                if(Gdx.input.isKeyJustPressed(Keys.S) && map[x][y-1].isTraversable())
                {player.moveY(-1);}
                if(Gdx.input.isKeyJustPressed(Keys.D) && map[x+1][y].isTraversable())
                {player.moveX(1);}
            }
        }

        renderer.begin();
        if(gamestate == GameState.GAME)
        {
            for(int r = 0; r < map.length ; r ++)
            {
                for(int c = 0; c < map[r].length ; c ++)
                {
                    
                }
            }
        }
        renderer.end();
        gameTime ++;
    }

    @Override
    public void dispose()
    {
        renderer.dispose(); 
    }

    private void genMap()
    {
        int randT = 0;
        for(int r = 0; r < map.length ; r ++)
        {
            for(int c = 0; c < map[r].length; c++)
            {
                if(r == 0 || c == 0 || r == map.length - 1 || c == map[r].length - 1)
                    map[r][c] = Constants.BARRIER;
                else
                {
                    randT = (int) (Math.random() * 3 + 1);
                    if(randT == 1)
                        map[r][c] = Constants.STONE;
                    if(randT == 2)
                        map[r][c] = Constants.SAND;
                    if(randT == 3)
                        map[r][c] = Constants.GRASS;
                }
            }
        }
    }
}
