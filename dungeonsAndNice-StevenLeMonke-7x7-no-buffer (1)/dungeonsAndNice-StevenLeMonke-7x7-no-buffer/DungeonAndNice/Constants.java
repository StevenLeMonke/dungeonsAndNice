public class Constants
{
    public static final int WORLD_WIDTH = 32 * 7;  //7 is for 7 x 7
    public static final int WORLD_HEIGHT = 32 * 7;
    
    public static final int TILE_WIDTH = WORLD_WIDTH / 7;
    public static final int TILE_HEIGHT = WORLD_HEIGHT / 7;
    
    public static final double PLAYER_SPEED = 0;   
    public static final double ENEMY_SPEED = 0;
    
    public static final Tile STONE = new Tile(true, 5, "CobbleV2.png");
    public static final Tile GRASS = new Tile(true, 7, "GrassV1.png");
    public static final Tile BARRIER = new Tile(false); //auto barrier.png
    public static final Tile SAND = new Tile(true, 10, "SandV1.png");
}