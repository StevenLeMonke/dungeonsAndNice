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
    private double playerMaxHealth;

    private int gameTime;
    private int attackCooldownTime;

    private Tile[][] map;
    private Enemy[][] enemyMap;
    private BitmapFont font;

    private ArrayList<Enemy> enemies;
    private ArrayList<Chest> chests;

    private float xOffset;
    private float yOffset;

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
        playerMaxHealth = player.getHealth();
        gameTime = 0;
        attackCooldownTime = 0;
        map = new Tile[52][52];

        font = new BitmapFont();

        enemies = new ArrayList<Enemy>();
        chests = new ArrayList<Chest>();

        enemies.add(new Enemy(player.getX(), player.getY() + 2, 1));

        xOffset = 0;
        yOffset = 0;

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
            // if(player.getDirection() == 1)
            // {
            // if(gameTime == 15 && map[y+1][x].isTraversable())
            // {
            // player.moveY(1);
            // yOffset = 0;    
            // }
            // else if(gameTime < 15)
            // yOffset = -1 * (float) gameTime / 15f * (float)Constants.TILE_HEIGHT;
            // }
            if(gameTime * Item.moveSpeed > map[x][y].getTime())
            {
                if(Gdx.input.isKeyJustPressed(Keys.W) && gameTime > 15)
                {
                    if(map[y+1][x].isTraversable())
                    {player.moveY(1); gameTime = 0;}
                    player.setDirection(1);
                }
                if(Gdx.input.isKeyJustPressed(Keys.A) && gameTime > 15)
                {
                    if(map[y][x-1].isTraversable())
                    {player.moveX(-1); gameTime = 0;}
                    player.setDirection(4);
                }
                if(Gdx.input.isKeyJustPressed(Keys.S) && gameTime > 15)
                {
                    if(map[y-1][x].isTraversable())
                    {player.moveY(-1); gameTime = 0;}
                    player.setDirection(3);
                }      
                if(Gdx.input.isKeyJustPressed(Keys.D) && gameTime > 15)
                {
                    if(map[y][x+1].isTraversable())
                    {player.moveX(1); gameTime = 0;}
                    player.setDirection(2);
                }
            }

            if(Gdx.input.isKeyJustPressed(Keys.SPACE) && attackCooldownTime * Item.attackSpeed > 30)
            {
                for(int i = 0; i < enemies.size(); i++)
                {
                    Enemy temp = enemies.get(i);
                    if(temp.getX() == x && temp.getY() == y + 1) //col same : row + 1
                    {player.setDirection(1); player.attack(temp); attackCooldownTime = 0;}
                    if(temp.getX() == x && temp.getY() == y - 1)
                    {player.setDirection(3); player.attack(temp); attackCooldownTime = 0;}
                    if(temp.getX() == x + 1 && temp.getY() == y)
                    {player.setDirection(2); player.attack(temp); attackCooldownTime = 0;}
                    if(temp.getX() == x - 1 && temp.getY() == y)
                    {player.setDirection(4); player.attack(temp); attackCooldownTime = 0;}
                }
            }

            if(player.getHealth() <= 0) {}

            for(int i = 0; i < enemies.size(); i++)
            {
                Enemy temp = enemies.get(i);
                if(temp.getHealth() <= 0)
                    enemies.remove(i);
                if(temp.enemyTime() > 45)
                    if(!temp.getAggro()) 
                    {randomMove(temp);
                        temp.setEnemyTime(0);}
                    else 
                    {moveEnemy(temp);
                        temp.setEnemyTime(0);}
                temp.update();
            }
        }
        player.update();

        if(gamestate == GameState.MENU)
        {

        }

        batch.begin();
        renderer.begin(ShapeType.Filled);
        renderer.setColor(Color.RED);

        if(gamestate == GameState.MENU)
        {

        }

        if(gamestate == GameState.GAME)
        {
            if(x > 2 && y > 2 && x < map[0].length - 3 && y < map.length - 3)
            {
                for(int r = y - 4 ; r <= y + 4 ; r ++)
                {
                    for(int c = x - 4; c <= x + 4 ; c ++)
                    {
                        batch.draw(map[r][c].getTexture() ,(float) (c - (x - 3)) * Constants.TILE_WIDTH, (float) (r - (y - 3)) * Constants.TILE_HEIGHT + yOffset);
                        // if(r == y && c == x)
                        // batch.draw(player.texture(),(float) 3f * Constants.TILE_WIDTH, 3f * Constants.TILE_HEIGHT, Constants.TILE_WIDTH, Constants.TILE_HEIGHT); 
                        for(int i = 0; i < chests.size() ; i++)
                        {
                            if(chests.get(i).getX() == c && chests.get(i).getY() == r)
                            {
                                batch.draw(chests.get(i).texture(),(float) (c - (x - 3)) * Constants.TILE_WIDTH, (float) (r - (y - 3)) * Constants.TILE_HEIGHT  + yOffset, Constants.TILE_WIDTH, Constants.TILE_HEIGHT); 
                            }
                        }
                        for(int i = 0; i < enemies.size() ; i++)
                        {
                            if(enemies.get(i).getX() == c && enemies.get(i).getY() == r)
                            {
                                batch.draw(enemies.get(i).texture(),(float) (c - (x - 3)) * Constants.TILE_WIDTH, 
                                    (float) (r - (y - 3)) * Constants.TILE_HEIGHT  + yOffset, Constants.TILE_WIDTH, Constants.TILE_HEIGHT); 
                                renderer.rect((float) (c - (x - 3)) * Constants.TILE_WIDTH + (Constants.TILE_WIDTH / 6), 
                                    (float) (r - (y - 3)) * Constants.TILE_HEIGHT + (Constants.TILE_HEIGHT / 7)  + yOffset, 
                                    Constants.TILE_WIDTH / 1.5f - ((float) (enemies.get(i).getMaxHealth() - enemies.get(i).getHealth())
                                        / (float) enemies.get(i).getMaxHealth() * Constants.TILE_WIDTH / 1.5f), Constants.TILE_HEIGHT / 14);
                            }
                        }
                    }
                }
                batch.draw(player.texture(),(float) 3f * Constants.TILE_WIDTH, 3f * Constants.TILE_HEIGHT, Constants.TILE_WIDTH, Constants.TILE_HEIGHT);
            }

            renderer.setColor(Color.BLACK);
            renderer.rect(Constants.WORLD_WIDTH - Constants.WORLD_WIDTH/2.5f, 0, Constants.WORLD_WIDTH/2.5f, Constants.WORLD_HEIGHT/12);
            renderer.rect(Constants.WORLD_WIDTH - Constants.WORLD_WIDTH/7, Constants.WORLD_HEIGHT/12, Constants.WORLD_WIDTH/7,Constants.WORLD_HEIGHT/7);

            renderer.setColor(Color.RED);
            renderer.rect(Constants.WORLD_WIDTH - Constants.WORLD_WIDTH/2.75f, Constants.WORLD_HEIGHT / 60, Constants.WORLD_WIDTH/3f - (float) (playerMaxHealth - player.getHealth()), Constants.WORLD_HEIGHT/20);

            //font.draw(batch, _GlyphLayout_, _float_, _float_)
        }

        batch.end();
        renderer.end();
        gameTime ++;
        attackCooldownTime ++;
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

        Tile[] tiles = {Constants.STONE1 , Constants.STONE2 , Constants.STONE3 , Constants.STONE4 ,
                Constants.GRASS1 , Constants.GRASS2 , Constants.GRASS3 , Constants.GRASS4 , 
                Constants.SAND1 , Constants.SAND2 , Constants.SAND3 , Constants.SAND4};

        for(int i = 0; i < randOriginal.length; i ++)
        {
            randX = (int) (Math.random() * (map[0].length - 5) + 4);
            randY = (int) (Math.random() * (map.length - 5) + 4);
            randT = (int) (Math.random() * 3);

            randOriginal[i] = new int[] {randX, randY, randT};
            chests.add(new Chest(randX, randY));
            enemies.add(new Enemy(randX + 1, randY + 1, 1));
            enemies.add(new Enemy(randX - 1, randY + 1, 1));
            
            map[randY][randX] = Constants.BARRIER;
        }

        double temp = Integer.MAX_VALUE;
        int tile = randOriginal[0][2];
        int index = 0;

        for(int r = 0; r < map.length ; r ++)
        {
            for(int c = 0; c < map[r].length; c++)
            {
                if(r <= 3 || c <= 3 || r >= map.length - 4 || c >= map[r].length - 4)
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
                    map[r][c] = tiles[(randOriginal[index][2] * 4) + (int)(Math.random()*4)];
                    temp = Integer.MAX_VALUE;
                }
            }
        }

        for(int i = 0; i < randOriginal.length ; i ++)
        {
            map[randOriginal[i][1]][randOriginal[i][0]] = Constants.BARRIER;
        }
    }

    private double findDistance(int x1, int y1, int x2, int y2)
    {
        int xd = Math.abs(x1 - x2); //x difference
        int yd = Math.abs(y1 - y2); //y difference

        return Math.sqrt(Math.pow(xd, 2) + Math.pow(yd, 2));
    }

    private void checkAggro(Enemy temp)
    {
        if(!temp.getAggro() && findDistance(player.getX(), player.getY(), temp.getX(), temp.getY()) <= 5)
            temp.setAggro(true);
    }

    private void moveEnemy(Enemy temp)
    {
        if(temp.getX() > player.getX())
        {temp.moveX(-1); temp.setDirection(4);}
        else if(temp.getX() < player.getX())
        {temp.moveX(1); temp.setDirection(2);}
        else if(temp.getY() > player.getY())
            temp.moveY(-1);
        else if(temp.getY() < player.getY())
            temp.moveY(1);
    }

    private boolean attack(Enemy temp)
    {
        if((Math.abs(temp.getX() - player.getX()) == 1 && temp.getY() == player.getY()) || (Math.abs(temp.getY() - player.getY()) == 1 && temp.getX() == player.getX()))
        {
            player.updateHealth(temp.getDamage());
            return true;
        }
        return false;
    }

    private void randomMove(Enemy temp)
    {
        int randI = (int) (Math.random() * 6 + 1);
        if(randI == 1 && map[temp.getY() + 1][temp.getX()].isTraversable())
        {temp.moveY(1);}
        else if(randI == 2 && map[temp.getY()][temp.getX() + 1].isTraversable())
        {temp.moveX(1); temp.setDirection(2);}
        else if(randI == 3 && map[temp.getY() - 1][temp.getX()].isTraversable())
        {temp.moveY(-1);}
        else if(randI == 4 && map[temp.getY()][temp.getX() - 1].isTraversable())
        {temp.moveX(-1); temp.setDirection(4);}
        if(temp.getAltTexture())
            temp.altTexture(false);
        else
            temp.altTexture(true);
    }
}