public class Constants
{
    public static final int WORLD_WIDTH = 32 * 7;  //7 is for 7 x 7
    public static final int WORLD_HEIGHT = 32 * 7;
    
    public static final int TILE_WIDTH = WORLD_WIDTH / 7;
    public static final int TILE_HEIGHT = WORLD_HEIGHT / 7;
    
    public static final double PLAYER_SPEED = 0;   
    public static final double ENEMY_SPEED = 0;
    
    public static final Tile STONE = new Tile(true, 5, new String[] {"CobbleV1.png" , "CobbleV2.png" , "CobbleV3.png" , "CobbleV4.png"});
    public static final Tile GRASS = new Tile(true, 7, new String[] {"GrassV1.png" , "GrassV2.png" , "GrassV3.png" , "GrassV4.png"});
    public static final Tile BARRIER = new Tile(false); //auto barrier.png
    public static final Tile SAND = new Tile(true, 10, new String[] {"SandV1.png" , "SandV2.png" , "SandV3.png" , "SandV4.png"});
}