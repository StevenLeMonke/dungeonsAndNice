import com.badlogic.gdx.*; 
import com.badlogic.gdx.graphics.Texture;
public class Player extends Entity
{
    private Texture[] textures; //index 0123 up right down left
    private Texture texture;

    private int direction;
    private int xCoord;  //coords of player
    private int yCoord;

    public Player()
    {
        super(100 , 3);
        this.direction = 3;
        xCoord = 6;
        yCoord = 7;
        texture = new Texture("assets/PlayerSprite.png");
        textures = new Texture[] {new Texture("assets/PlayerUp.png"), new Texture("assets/PlayerRight.png"), new Texture("assets/PlayerDown.png"), new Texture("assets/PlayerLeft.png")};
    }

    public void attack(Entity e)
    {
        e.updateHealth(10 * Item.damageMulti);
        
    }
    
    public void attack()
    {
        
    }

    public void update()  {this.texture = textures[this.direction - 1];}

    public void moveX(int x) {xCoord += x;}

    public void moveY(int y) {yCoord += y;}

    public int getX() {return xCoord;}

    public int getY() {return yCoord;}

    public Texture texture() {return texture;}

    public void setDirection(int d) {this.direction = d;}

    public int getDirection() {return direction;}
}
