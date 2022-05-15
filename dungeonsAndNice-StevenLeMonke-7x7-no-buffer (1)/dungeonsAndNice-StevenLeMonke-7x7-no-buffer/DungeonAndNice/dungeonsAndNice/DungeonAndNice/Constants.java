public class Constants
{
    public static final int WORLD_WIDTH = 160;
    public static final int WORLD_HEIGHT = 160;
    
    public static final int TILE_WIDTH = WORLD_WIDTH / 7;
    public static final int TILE_HEIGHT = WORLD_HEIGHT / 7;
    
    public static final double PLAYER_SPEED = 0;   
    public static final double ENEMY_SPEED = 0;
    
    public static final Tile STONE = new Tile(true, 1, "Stone.png");
    public static final Tile GRASS = new Tile(true, 1, "Grass.png");
    public static final Tile BARRIER = new Tile(false); //auto barrier.png
    public static final Tile SAND = new Tile(true, 2, "Sand.png");
}