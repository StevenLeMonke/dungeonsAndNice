public class Player extends Entity
{
    private double playerSpeed;
    private double attackSpeed;
    private double playerDamage;
    private double shield;
    
    private int xCoord;  //coords of player
    private int yCoord;
    
    public Player()
    {
        super(100);
        playerSpeed = Constants.PLAYER_SPEED;
        attackSpeed = 1.5;
        shield = 50;
        xCoord = 0;
        yCoord = 0;
    }
    
    public void attack(Entity e)
    {
        e.updateHealth(playerDamage);
    }
    
    public void movePlayer(int x, int y)
    {
        xCoord += x;
        yCoord += y;  //y decreases as go up???
    }
    
    
}
