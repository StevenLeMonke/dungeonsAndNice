public class Constants
{
    public static final int WORLD_WIDTH = 32 * 7;  //7 is for 7 x 7
    public static final int WORLD_HEIGHT = 32 * 7;
    
    public static final int TILE_WIDTH = WORLD_WIDTH / 7;
    public static final int TILE_HEIGHT = WORLD_HEIGHT / 7;
    
    public static final double PLAYER_SPEED = 0;   
    public static final double ENEMY_SPEED = 0;
    
    public static final Tile STONE1 = new Tile(true, 5, "CobbleV1.png");
    public static final Tile STONE2 = new Tile(true, 5, "CobbleV2.png");
    public static final Tile STONE3 = new Tile(true, 5, "CobbleV3.png");
    public static final Tile STONE4 = new Tile(true, 5, "CobbleV4.png");
    public static final Tile GRASS1 = new Tile(true, 7, "GrassV1.png");
    public static final Tile GRASS2 = new Tile(true, 7, "GrassV2.png");
    public static final Tile GRASS3 = new Tile(true, 7, "GrassV3.png");
    public static final Tile GRASS4 = new Tile(true, 7, "GrassV4.png");
    public static final Tile SAND1 = new Tile(true, 10, "SandV1.png");
    public static final Tile SAND2 = new Tile(true, 10, "SandV2.png");
    public static final Tile SAND3 = new Tile(true, 10, "SandV3.png");
    public static final Tile SAND4 = new Tile(true, 10, "SandV4.png");
    public static final Tile BARRIER = new Tile(false); //auto barrier.png
}