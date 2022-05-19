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
    private SpriteBatch batch;

    private GameState gamestate;
    private Player player;

    private double gameTime;
    private double attackCooldownTime;
    private Tile[][] map;

    private ArrayList<Enemy> enemies; 

    @Override//called once when we start the game
    public void create()
    {
        camera = new OrthographicCamera(); 
        viewport = new FitViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, camera); 
        renderer = new ShapeRenderer(); 
        gamestate = GameState.GAME;
        batch = new SpriteBatch();
        camera.viewportWidth = Constants.TILE_WIDTH * 5;
        camera.viewportHeight = Constants.TILE_HEIGHT * 5;

        player = new Player();
        gameTime = 0;
        attackCooldownTime = 0;
        map = new Tile[50][50];

        enemies = new ArrayList<Enemy>();

        genMap();
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
            if(gameTime * Item.moveSpeed > map[x][y].getTime())
            {
                if(Gdx.input.isKeyJustPressed(Keys.W) && map[x][y+1].isTraversable())
                {player.moveY(1); gameTime = 0; player.setDirection(1);}
                if(Gdx.input.isKeyJustPressed(Keys.A) && map[x-1][y].isTraversable())
                {player.moveX(-1); gameTime = 0; player.setDirection(4);}
                if(Gdx.input.isKeyJustPressed(Keys.S) && map[x][y-1].isTraversable())
                {player.moveY(-1); gameTime = 0; player.setDirection(3);}      
                if(Gdx.input.isKeyJustPressed(Keys.D) && map[x+1][y].isTraversable())
                {player.moveX(1); gameTime = 0; player.setDirection(2);}
            }
            if(attackCooldownTime * Item.attackSpeed > 30 && Gdx.input.isKeyJustPressed(Keys.SPACE))
            {
                for(int i = 0; i < enemies.size(); i++)
                {
                    Enemy temp = enemies.get(i);
                    if(temp.getX() == x && temp.getY() == y + 1)
                    {player.setDirection(1); player.attack(temp);}
                    if(temp.getX() == x && temp.getY() == y - 1)
                    {player.setDirection(3); player.attack(temp);}
                    if(temp.getX() == x + 1 && temp.getY() == y)
                    {player.setDirection(2); player.attack(temp);}
                    if(temp.getX() == x - 1 && temp.getY() == y)
                    {player.setDirection(4); player.attack(temp);}
                }
                
            }
        }
        player.update();

        batch.begin();
        if(gamestate == GameState.GAME)
        {
            if(x > 2 && y > 2 && x < map[0].length - 3 && y < map.length - 3)
            {
                for(int r = y - 3 ; r <= y + 3 ; r ++)
                {
                    for(int c = x - 3; c <= x + 3 ; c ++)
                    {
                        batch.draw(map[r][c].getTexture() ,(float) (c - (x - 3)) * Constants.TILE_WIDTH, (float) (r - (y - 3)) * Constants.TILE_HEIGHT);
                        if(r == y && c == x)
                            batch.draw(player.texture(),(float) (c - (x - 3)) * Constants.TILE_WIDTH, (float) (r - (y - 3)) * Constants.TILE_HEIGHT, Constants.TILE_WIDTH, Constants.TILE_HEIGHT); 
                    }
                }
            }
            else if(x <= 3 && y <= 3)
            {
                for(int r = 0 ; r < 7 ; r ++)
                {
                    for(int c = 0 ; c < 7 ; c ++)
                    {
                        batch.draw(map[r][c].getTexture(),(float) c * Constants.TILE_WIDTH, (float) r * Constants.TILE_HEIGHT);
                        if(r == y && c == x)
                            batch.draw(player.texture(),(float) c * Constants.TILE_WIDTH, (float) r * Constants.TILE_HEIGHT, Constants.TILE_WIDTH, Constants.TILE_HEIGHT);
                    }
                }
            }
            else if(x >= map[0].length - 4 && y <= 3)
            {
                for(int r = 0 ; r < 7 ; r ++)
                {
                    for(int c = map[0].length - 7; c < map[0].length ; c ++)
                    {
                        batch.draw(map[r][c].getTexture(),(float) (c - (map[0].length - 7)) * Constants.TILE_WIDTH, (float) r * Constants.TILE_HEIGHT);
                        if(r == y && c == x)
                            batch.draw(player.texture(),(float) (c - (map[0].length - 7)) * Constants.TILE_WIDTH, (float) r * Constants.TILE_HEIGHT, Constants.TILE_WIDTH, Constants.TILE_HEIGHT);
                    }
                }
            }
            else if(x <= 3 && y >= map.length - 4)
            {
                for(int r = map.length - 7; r < map.length ; r ++)
                {
                    for(int c = 0 ; c < 7 ; c ++)
                    {
                        batch.draw(map[r][c].getTexture(),(float) c * Constants.TILE_WIDTH, (float) (r - (map[0].length - 7)) * Constants.TILE_HEIGHT);
                        if(r == y && c == x)
                            batch.draw(player.texture(),(float) c * Constants.TILE_WIDTH, (float) (r - (map[0].length - 7)) * Constants.TILE_HEIGHT, Constants.TILE_WIDTH, Constants.TILE_HEIGHT);
                    }
                }
            }
            else if(x >= map[0].length && y >= map.length - 4)
            {
                for(int r = map.length - 7; r < map.length ; r ++)
                {
                    for(int c = map[0].length - 7; c < map[0].length ; c ++)
                    {
                        batch.draw(map[r][c].getTexture(),(float) (c - (map[0].length - 7)) * Constants.TILE_WIDTH, (float) (r - (map[0].length - 7)) * Constants.TILE_HEIGHT);
                        if(r == y && c == x)
                            batch.draw(player.texture(),(float) (c - (map[0].length - 7)) * Constants.TILE_WIDTH, (float) (r - (map[0].length - 7)) * Constants.TILE_HEIGHT, Constants.TILE_WIDTH, Constants.TILE_HEIGHT);
                    }
                }
            }
            else if(x <= 3)
            {
                for(int r = y - 3 ; r <= y + 3 ; r ++)
                {
                    for(int c = 0; c < 7 ; c ++)
                    {
                        batch.draw(map[r][c].getTexture() ,(float) c * Constants.TILE_WIDTH, (float) (r - (y - 3)) * Constants.TILE_HEIGHT);
                        if(r == y && c == x)
                            batch.draw(player.texture(),(float) c * Constants.TILE_WIDTH, (float) (r - (y - 3)) * Constants.TILE_HEIGHT, Constants.TILE_WIDTH, Constants.TILE_HEIGHT); 
                    }
                }
            }
            else if(x >= map[0].length - 4)
            {
                for(int r = y - 3 ; r <= y + 3 ; r ++)
                {
                    for(int c = map[0].length - 7; c < map[0].length ; c ++)
                    {
                        batch.draw(map[r][c].getTexture() ,(float) (c - (map[0].length - 7)) * Constants.TILE_WIDTH, (float) (r - (y - 3)) * Constants.TILE_HEIGHT);
                        if(r == y && c == x)
                            batch.draw(player.texture(),(float) (c - (map[0].length - 7)) * Constants.TILE_WIDTH, (float) (r - (y - 3)) * Constants.TILE_HEIGHT, Constants.TILE_WIDTH, Constants.TILE_HEIGHT); 
                    }
                }
            }
            else if(y <= 3)
            {
                for(int r = 0 ; r < 7 ; r ++)
                {
                    for(int c = x - 3; c <= x + 3 ; c ++)
                    {
                        batch.draw(map[r][c].getTexture() ,(float) (c - (x - 3)) * Constants.TILE_WIDTH, (float) r * Constants.TILE_HEIGHT);
                        if(r == y && c == x)
                            batch.draw(player.texture(),(float) (c - (x - 3)) * Constants.TILE_WIDTH, (float) r * Constants.TILE_HEIGHT, Constants.TILE_WIDTH, Constants.TILE_HEIGHT);
                    }
                }
            }
            else if(y >= map[0].length - 4)
            {
                for(int r = map.length - 7; r < map.length ; r ++)
                {
                    for(int c = x - 3; c <= x + 3 ; c ++)
                    {
                        batch.draw(map[r][c].getTexture() ,(float) (c - (x - 3)) * Constants.TILE_WIDTH, (float) (r - (map.length - 7)) * Constants.TILE_HEIGHT);
                        if(r == y && c == x)
                            batch.draw(player.texture(),(float) (c - (x - 3)) * Constants.TILE_WIDTH, (float) (r - (map.length - 7)) * Constants.TILE_HEIGHT, Constants.TILE_WIDTH, Constants.TILE_HEIGHT); 
                    }
                }
            }
        }
        batch.end();
        gameTime ++;
    }

    @Override
    public void dispose()
    {
        renderer.dispose(); 
    }

    @Override
    public void resize(int width, int height)
    {
        viewport.update(width, height, true); 
    }

    private void genMap()
    {
        int[][] randOriginal = new int[(int) (Math.random() * (35 - 15 + 1) + 15)][3];
        int randX = 0;
        int randY = 0;
        int randT = 0; //random tile type 0 1 2 stone grass sand

        Tile[] tiles = {Constants.STONE , Constants.GRASS , Constants.SAND};

        for(int i = 0; i < randOriginal.length; i ++)
        {
            randX = (int) (Math.random() * (map[0].length - 2) + 1);
            randY = (int) (Math.random() * (map.length - 2) + 1);
            randT = (int) (Math.random() * 3);

            randOriginal[i] = new int[] {randX, randY, randT};
            map[randY][randX] = tiles[randT];
        }

        double temp = Integer.MAX_VALUE;
        int tile = randOriginal[0][2];
        int index = 0;

        for(int r = 0; r < map.length ; r ++)
        {
            for(int c = 0; c < map[r].length; c++)
            {
                if(r == 0 || c == 0 || r == map.length - 1 || c == map[r].length - 1)
                    map[r][c] = Constants.BARRIER;
                else
                {
                    for(int i = 0; i < randOriginal.length; i++)
                    {
                        if(temp > findDistance(c,r,randOriginal[i][0],randOriginal[i][1]))
                        {
                            temp = findDistance(c,r,randOriginal[i][0],randOriginal[i][1]);
                            index = i;
                        }
                    }
                    map[r][c] = tiles[randOriginal[index][2]];
                    temp = Integer.MAX_VALUE;
                }
            }
        }
    }

    private double findDistance(int x1, int y1, int x2, int y2)
    {
        int xd = Math.abs(x1 - x2); //x difference
        int yd = Math.abs(y1 - y2); //y difference

        return Math.sqrt(Math.pow(xd, 2) + Math.pow(yd, 2));
    }
}

