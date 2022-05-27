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
import com.badlogic.gdx.math.Circle; 
import com.badlogic.gdx.Input.Keys; 
import com.badlogic.gdx.math.Vector2; 
import com.badlogic.gdx.math.MathUtils; 
import com.badlogic.gdx.math.Intersector; 
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.*; 
import com.badlogic.gdx.graphics.*; 
import java.util.*;
import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SpaceShooter extends ApplicationAdapter
{
    private OrthographicCamera camera; //the camera to our world
    private Viewport viewport; //maintains the ratios of your world
    private ShapeRenderer renderer; //used to draw textures and fonts 
    private BitmapFont font; //used to draw fonts (text)
    private SpriteBatch batch; //also needed to draw fonts (text);
    private GlyphLayout layout; 

    private int ctr = 0;
    private int updateTimer;
    private List<Enemy> enemyList;
    private boolean moveLeft;
    private List<Rectangle> enemyBulletList;
    private GameState gamestate;
    private Player player;
    private Rectangle bullet;

    private Texture start;
    private Texture startHighlight;
    private Rectangle startButton;

    private Texture help;
    private Texture helpHighlight;
    private Rectangle helpButton;

    private Texture easy;
    private Texture easyHighlight;
    private Rectangle easyButton;

    private Texture medium;
    private Texture mediumHighlight;
    private Rectangle mediumButton;

    private Texture hard;
    private Texture hardHighlight;
    private Rectangle hardButton;

    private Texture menuBackground;
    private Texture helpBackground;
    private Texture gameBackground;

    private Texture enemy;
    private Texture spaceShip;

    private Music backgroundSound;
    private Music gameBackgroundSound;
    private Sound bulletSoundEffect;
    private Sound enemyPlayerDeath;
    private Sound enemyHurt;
    private Sound playerHurt;

    @Override//called once when we start the game
    public void create(){
        camera = new OrthographicCamera(); 
        viewport = new FitViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, camera); 
        renderer = new ShapeRenderer(); 
        font = new BitmapFont(Gdx.files.internal("helpFont.fnt")); 
        batch = new SpriteBatch();//if you want to use images instead of using ShapeRenderer
        layout = new GlyphLayout(); 

        moveLeft = false;
        enemyList = new ArrayList<Enemy>();
        enemyBulletList = new ArrayList<Rectangle>();
        updateTimer = 0;
        gamestate = GameState.MENU;
        player = new Player((float)Constants.WORLD_WIDTH / 2, (float)0,
            (float)Constants.PLAYER_WIDTH, (float)Constants.PLAYER_HEIGHT, 100);

        backgroundSound = Gdx.audio.newMusic(Gdx.files.internal("balls.mp3"));
        gameBackgroundSound = Gdx.audio.newMusic(Gdx.files.internal("cowboyBop.mp3"));

        // bulletSoundEffect = Gdx.audio.newSound(Gdx.files.internal("PewPew.mp3"));
        // enemyPlayerDeath = Gdx.audio.newSound(Gdx.files.internal("Explosion.mp3"));
        // enemyHurt = Gdx.audio.newSound(Gdx.files.internal("MinecraftHit.mp3"));
        // playerHurt = Gdx.audio.newSound(Gdx.files.internal("MinecraftHitDos.mp3"));

        enemy = new Texture(Gdx.files.internal("arien.png"));
        spaceShip = new Texture(Gdx.files.internal("spaceShip.png"));

        menuBackground  = new Texture(Gdx.files.internal("SpaceBackground.jpg"));
        helpBackground = new Texture(Gdx.files.internal("SpaceBackground1.jpg"));
        // gameBackground = new Texture(Gdx.files.internal("SpaceBackground2.jpg"));

        start = new Texture(Gdx.files.internal("Start.png")); 
        startHighlight = new Texture(Gdx.files.internal("StartHighlight.png")); 
        startButton = new Rectangle(Constants.WORLD_WIDTH / 2 - 128, 
            Constants.WORLD_HEIGHT / 2 + 96, 256, 128);

        help = new Texture(Gdx.files.internal("Help.png")); 
        helpHighlight = new Texture(Gdx.files.internal("HelpHighlight.png")); 
        helpButton = new Rectangle(Constants.WORLD_WIDTH / 2 - 128, 
            Constants.WORLD_HEIGHT / 2 - 160, 256, 128);

        easy = new Texture(Gdx.files.internal("Easy.png")); 
        easyHighlight = new Texture(Gdx.files.internal("EasyHighlight.png")); 
        easyButton = new Rectangle(Constants.WORLD_WIDTH / 2 - 128, 
            Constants.WORLD_HEIGHT / 2 + 192, 256, 128);

        medium = new Texture(Gdx.files.internal("Medium.png")); 
        mediumHighlight = new Texture(Gdx.files.internal("MediumHighlight.png")); 
        mediumButton = new Rectangle(Constants.WORLD_WIDTH / 2 - 128, 
            Constants.WORLD_HEIGHT / 2, 256, 128);

        hard = new Texture(Gdx.files.internal("Hard.png")); 
        hardHighlight = new Texture(Gdx.files.internal("HardHighlight.png")); 
        hardButton = new Rectangle(Constants.WORLD_WIDTH / 2 - 128, 
            Constants.WORLD_HEIGHT / 2 - 192, 256, 128);

        // enemy.add(new Texture("spaceInv.jpg"));
    }

    @Override
    public void render(){
        viewport.apply();
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //update attribute
        if(gamestate == GameState.MENU)
        {
            backgroundSound.setVolume(1f);
            backgroundSound.setLooping(true);
            backgroundSound.play();

            Vector2 clickLoc = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY())); 
            batch.setProjectionMatrix(viewport.getCamera().combined);
            batch.begin();
            batch.draw(menuBackground, 0, 0, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
            if(!startButton.contains(clickLoc)){
                batch.draw(start, 
                    startButton.x, 
                    startButton.y, 
                    startButton.width, 
                    startButton.height);
            }
            else
            {
                batch.draw(startHighlight, 
                    startButton.x, 
                    startButton.y, 
                    startButton.width, 
                    startButton.height);
                if(Gdx.input.justTouched())
                {
                    backgroundSound.setLooping(false);
                    backgroundSound.stop();
                    gamestate = GameState.START;
                }
            }
            if(!helpButton.contains(clickLoc)){
                batch.draw(help, 
                    helpButton.x, 
                    helpButton.y, 
                    helpButton.width, 
                    helpButton.height);
            }
            else
            {
                batch.draw(helpHighlight, 
                    helpButton.x, 
                    helpButton.y, 
                    helpButton.width, 
                    helpButton.height);
                if(Gdx.input.justTouched())
                {
                    batch.draw(menuBackground, 0, 0, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
                    gamestate = gamestate.INSTRUCTIONS;   
                }
            }
            batch.end(); 
        }

        else if(gamestate == GameState.INSTRUCTIONS)
        {
            layout.setText(font, "Welcome to SpaceShooter!" + 
                "\nPress space to fire a shot at the enemies and use the left" +
                "\nand right arrows keys or \"a\" and \"d\" to avoid the enemy" +
                "\nattacks! Press any key to return to the menu. You may also press" +
                "\nexcape during the game to end the game and return to menu.");
            batch.setProjectionMatrix(viewport.getCamera().combined);
            batch.begin();
            batch.draw(helpBackground, 0, 0, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
            font.draw(batch, layout,
                Constants.WORLD_WIDTH / 2 - layout.width / 2,
                Constants.WORLD_HEIGHT / 2 + layout.height / 2);

            batch.end(); 
            if(Gdx.input.isKeyJustPressed(Keys.ANY_KEY))
            {
                gamestate = GameState.MENU; 
            }
        }
        else if(gamestate == GameState.START)
        {
            Vector2 clickLoc = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY())); 
            batch.setProjectionMatrix(viewport.getCamera().combined);
            batch.begin();
            batch.draw(menuBackground, 0, 0, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
            if(Gdx.input.isKeyJustPressed(Keys.ESCAPE))
            {
                gamestate = GameState.MENU;
                reset();
            }
            if(!easyButton.contains(clickLoc))
            {
                batch.draw(easy, 
                    easyButton.x, 
                    easyButton.y, 
                    easyButton.width, 
                    easyButton.height);
            }
            else
            {
                batch.draw(easyHighlight, 
                    easyButton.x, 
                    easyButton.y, 
                    easyButton.width, 
                    easyButton.height);
                if(Gdx.input.justTouched())
                {
                    gamestate = GameState.GAME_EASY; 
                }
            }
            if(!mediumButton.contains(clickLoc))
            {
                batch.draw(medium, 
                    mediumButton.x, 
                    mediumButton.y, 
                    mediumButton.width, 
                    mediumButton.height);
            }
            else
            {
                batch.draw(mediumHighlight, 
                    mediumButton.x, 
                    mediumButton.y, 
                    mediumButton.width, 
                    mediumButton.height);
                if(Gdx.input.justTouched())
                {
                    gamestate = GameState.GAME_MEDIUM; 
                }
            }
            if(!hardButton.contains(clickLoc))
            {
                batch.draw(hard, 
                    hardButton.x, 
                    hardButton.y, 
                    hardButton.width, 
                    hardButton.height);
            }
            else
            {
                batch.draw(hardHighlight, 
                    hardButton.x, 
                    hardButton.y, 
                    hardButton.width, 
                    hardButton.height);
                if(Gdx.input.justTouched())
                {
                    gamestate = GameState.GAME_HARD;
                }
            }
            batch.end();
        }

        if(gamestate == GameState.GAME_EASY)
        {
            gameBackgroundSound.setVolume(0.5f);
            gameBackgroundSound.setLooping(true);
            gameBackgroundSound.play();
            if(Gdx.input.isKeyJustPressed(Keys.ESCAPE))
            {
                gameBackgroundSound.setLooping(false);
                gameBackgroundSound.stop();
                gamestate = GameState.MENU;
                reset();
            }
            healthBar();
            makeEnemies(1);
            drawEnemies();
            makePlayer();
            updateTimer++;
            if(Gdx.input.isKeyPressed(Keys.SPACE) && bullet == null)
            {
                playerShoot();
            }
            if(bullet != null)
            {
                moveBullet();
                renderer.setColor(Color.WHITE); 
                renderer.begin(ShapeType.Filled);
                renderer.rect(bullet.getX(), bullet.getY(), bullet.getWidth(), bullet.getHeight());
                renderer.end();
                if(bullet.getY() + Constants.BULLET_SPEED > Constants.WORLD_HEIGHT)
                {
                    bullet = null;
                }
            }
            if(Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.LEFT))
            {
                if(player.getX() - 6 > 0)
                {
                    player.setX(player.getX() - 6);
                }
            }
            else if(Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.RIGHT))
            {
                if(player.getX() + 6 < Constants.WORLD_WIDTH)
                {
                    player.setX(player.getX() + 6);
                }
            }
            if(updateTimer % 30 == 0)//every half second
            {
                moveEnemies();
            }
            if(player.getHealth() == 0)
            {
                gamestate = GameState.MENU;
                player.setHealth(Constants.MAX_HEALTH); 
            }
        }

        if(gamestate == GameState.GAME_MEDIUM)
        {
            gameBackgroundSound.setVolume(0.5f);
            gameBackgroundSound.setLooping(true);
            gameBackgroundSound.play();
            if(Gdx.input.isKeyJustPressed(Keys.ESCAPE))
            {
                gameBackgroundSound.setLooping(false);
                gameBackgroundSound.stop();
                gamestate = GameState.MENU;
                reset();
            }
            healthBar();
            makeEnemies(2);
            drawEnemies();
            makePlayer();
            updateTimer++;
            if(Gdx.input.isKeyPressed(Keys.SPACE) && bullet == null)
            {
                playerShoot();
            }
            if(bullet != null)
            {
                moveBullet();
                renderer.setColor(Color.WHITE); 
                renderer.begin(ShapeType.Filled);
                renderer.rect(bullet.getX(), bullet.getY(), bullet.getWidth(), bullet.getHeight());
                renderer.end();
                if(bullet.getY() + Constants.BULLET_SPEED > Constants.WORLD_HEIGHT)
                {
                    bullet = null;
                }
            }
            if(Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.LEFT))
            {
                if(player.getX() - 6 > 0)
                {
                    player.setX(player.getX() - 6);
                }
            }
            else if(Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.RIGHT))
            {
                if(player.getX() + 6 < Constants.WORLD_WIDTH)
                {
                    player.setX(player.getX() + 6);
                }
            }
            if(updateTimer % 30 == 0)//every half second
            {
                moveEnemies();
            }
            if(player.getHealth() == 0)
            {
                gamestate = GameState.MENU;
                player.setHealth(Constants.MAX_HEALTH); 
            }
        }

        if(gamestate == GameState.GAME_HARD)
        {
            gameBackgroundSound.setVolume(0.5f);
            gameBackgroundSound.setLooping(true);
            gameBackgroundSound.play();
            if(Gdx.input.isKeyJustPressed(Keys.ESCAPE))
            {
                gameBackgroundSound.setLooping(false);
                gameBackgroundSound.stop();
                gamestate = GameState.MENU;
                reset();
            }
            healthBar();
            makeEnemies(3);
            drawEnemies();
            makePlayer();
            updateTimer++;
            if(Gdx.input.isKeyPressed(Keys.SPACE) && bullet == null)
            {
                playerShoot();
            }
            if(bullet != null)
            {
                moveBullet();
                renderer.setColor(Color.WHITE); 
                renderer.begin(ShapeType.Filled);
                renderer.rect(bullet.getX(), bullet.getY(), bullet.getWidth(), bullet.getHeight());
                renderer.end();
                if(bullet.getY() + Constants.BULLET_SPEED > Constants.WORLD_HEIGHT)
                {
                    bullet = null;
                }
            }
            if(Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.LEFT))
            {
                if(player.getX() - 6 > 0)
                {
                    player.setX(player.getX() - 6);
                }
            }
            else if(Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.RIGHT))
            {
                if(player.getX() + 6 < Constants.WORLD_WIDTH)
                {
                    player.setX(player.getX() + 6);
                }
            }
            if(updateTimer % 30 == 0)//every half second
            {
                moveEnemies();
            }
            if(player.getHealth() == 0)
            {
                gamestate = GameState.MENU;
                player.setHealth(Constants.MAX_HEALTH); 
            }
        }
    }

    public void healthBar()
    {
        renderer.setProjectionMatrix(viewport.getCamera().combined);
        renderer.setColor(Color.WHITE); 
        renderer.begin(ShapeType.Filled);
        renderer.setColor(Color.RED); 
        renderer.rect(Constants.WORLD_WIDTH - 150, Constants.WORLD_HEIGHT - 35, Constants.MAX_HEALTH, 10);

        renderer.setColor(Color.GREEN); 
        float percentage = player.getHealth() / Constants.MAX_HEALTH;
        renderer.rect(Constants.WORLD_WIDTH - 150, Constants.WORLD_HEIGHT - 35, percentage * Constants.MAX_HEALTH , 10);

        renderer.end();
    }

    public void playerShoot()
    {
        renderer.setColor(Color.WHITE); 
        renderer.begin(ShapeType.Filled);
        bullet = new Rectangle(player.getX() + (player.getWidth() / 2) - 25,
            player.getY() + player.getHeight(), 10, 40);
        renderer.rect(bullet.getX(), bullet.getY(), bullet.getWidth(), bullet.getHeight());
        renderer.end();
    }

    private void moveBullet()
    {
        bullet.setPosition(bullet.getX(),
            bullet.getY() + Constants.BULLET_SPEED);
    }

    public void makePlayer()
    {
        batch.begin();
        batch.draw(spaceShip, player.getX() - Constants.GAP, player.getY() + Constants.GAP, player.getWidth(), player.getHeight());
        batch.end();
    }

    public void makeEnemies(int enemyHealth)
    {
        for(int i = 1; i <= 3; i++)
        {
            for(int x = 1; x <= 10; x++)
            {
                enemyList.add(new Enemy(((x - 1) * Constants.ENEMY_WIDTH) + (Constants.GAP * x), 
                        Constants.WORLD_HEIGHT - ((i) * (Constants.GAP + Constants.ENEMY_HEIGHT)),
                        Constants.ENEMY_WIDTH, Constants.ENEMY_HEIGHT, enemyHealth));
            }
        }
    }

    public void drawEnemies()
    {
        batch.begin();
        for(int i = 0; i < enemyList.size(); i++)
        {
            batch.draw(enemy, enemyList.get(i).getX() + (Constants.GAP + Constants.ENEMY_WIDTH * 2),
                enemyList.get(i).getY() - (Constants.GAP * 2),
                Constants.ENEMY_WIDTH + 30,
                Constants.ENEMY_HEIGHT + 30);
        }
        batch.end();
    }

    public void moveEnemies()
    {
        for(int i = 0; i < enemyList.size(); i++)
        {
            float xCor = enemyList.get(i).getX();
            float yCor = enemyList.get(i).getY();
            if(moveLeft == false)
            {
                enemyList.get(i).x = xCor + 60;
            }
            else if(moveLeft == true)
            {
                enemyList.get(i).x = xCor - 60;
            }
            //we don't need to move the y-coords
        }
        if(moveLeft == true)
        {
            moveLeft = false;
        }
        else
        {
            moveLeft = true;
        }
    }

    public void enemyShoot()
    {
        // int row
        // for(int i = 0; i < enemyList.size(); i++)
        // {
            
        // }
    }

    public void reset()
    {
        ctr = 0;
        updateTimer= 0;
        enemyList.clear();
    }

    @Override
    public void resize(int width, int height){
        viewport.update(width, height, true); 
    }

    @Override
    public void dispose(){
        renderer.dispose(); 
        batch.dispose(); 
    }
}
