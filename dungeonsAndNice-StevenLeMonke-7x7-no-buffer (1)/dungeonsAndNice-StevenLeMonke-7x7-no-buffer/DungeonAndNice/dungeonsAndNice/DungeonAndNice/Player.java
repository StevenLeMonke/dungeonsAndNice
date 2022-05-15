import com.badlogic.gdx.*; 
import com.badlogic.gdx.graphics.Texture;
public class Player extends Entity
{
    private double playerSpeed;
    private double attackSpeed;
    private double playerDamage;
    private double shield;
    private Texture texture;
    private int direction; //up right down left ; 1 2 3 4
    
    private int xCoord;  //coords of player
    private int yCoord;
    
    public Player()
    {
        super(100);
        playerSpeed = Constants.PLAYER_SPEED;
        attackSpeed = 1.5;
        shield = 50;
        xCoord = 25;
        yCoord = 40;
        texture = new Texture("assets/Player.png");
    }
    
    public void attack(Entity e) {e.updateHealth(playerDamage);}
    public void setDirection(int d) {direction = d;}
    public void moveX(int x) {xCoord += x;}
    public void moveY(int y) {yCoord += y;}
    public int getX() {return xCoord;}
    public int getY() {return yCoord;}
    public Texture texture() {return texture;}
}
