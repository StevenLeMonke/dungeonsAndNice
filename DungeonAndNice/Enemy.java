import com.badlogic.gdx.*; 
import com.badlogic.gdx.graphics.Texture;

public class Enemy extends Entity
{
    private double damage;
    private int xCoord;
    private int yCoord;
    private int direction;
    
    private Texture texture;
    private Texture[] textures;
   
    
    public Enemy(int x, int y, int tier)
    {
      super(75 * tier, 3);
      
      this.xCoord = x;
      this.yCoord = y;
      this.direction = 3;
      
      this.damage = 10 * tier;
    }
    
    public void update()  {this.texture = textures[this.direction - 1];}

    public void moveX(int x) {xCoord += x;}

    public void moveY(int y) {yCoord += y;}

    public int getX() {return xCoord;}

    public int getY() {return yCoord;}
}
