import com.badlogic.gdx.*; 
import com.badlogic.gdx.graphics.Texture;

public class Enemy extends Entity
{
    private double damage;
    private int xCoord;
    private int yCoord;
    private int direction;
    private double maxHealth;

    private Texture texture;
    private Texture[] textures;

    public Enemy(int x, int y, int tier)
    {
        super(75 * tier, 3);

        this.xCoord = x;
        this.yCoord = y;
        this.direction = 3;

        this.damage = 10 * tier;
        
        this.maxHealth = 75 * tier;

        this.textures = new  Texture[] {new Texture("assets/Fishy.png"), new Texture("assets/FishyFlap.png")};
        this.texture = textures[0];
    }

    public void update()  {if(direction == 4) {this.texture = textures[0];}
        else if(direction == 2){}}

    public Texture texture() {return texture;}

    public void moveX(int x) {xCoord += x;}

    public void moveY(int y) {yCoord += y;}

    public int getX() {return xCoord;}

    public int getY() {return yCoord;}
    
    public double getMaxHealth() {return maxHealth;}
}
