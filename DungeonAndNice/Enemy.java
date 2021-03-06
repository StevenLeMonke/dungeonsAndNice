import com.badlogic.gdx.*; 
import com.badlogic.gdx.graphics.Texture;

public class Enemy extends Entity
{
    private double damage;
    private int xCoord;
    private int yCoord;
    private int direction;
    private double maxHealth;

    private boolean altTexture;  
    private Texture texture;
    private Texture[] textures;
    private int enemyTime;

    private boolean aggro;

    public Enemy(int x, int y, int tier)
    {
        super(75 * tier, 3);

        this.xCoord = x;
        this.yCoord = y;
        this.direction = 3;

        this.damage = 10 * tier;

        this.maxHealth = 75 * tier;
        this.enemyTime = 0;

        this.aggro = false;

        this.textures = new  Texture[] {new Texture("assets/Fishy.png"), new Texture("assets/FishyFlap.png"),
            new Texture("assets/FishyRight.png"), new Texture("assets/FishyFlapRight.png")};
        this.texture = textures[0];
        this.altTexture = false;
    }

    public void update()  
    {
        if(this.direction == 4)
        {
            if(altTexture)
            {this.texture = textures[1];}
            else
            {this.texture = textures[0];}
        }
        else if(this.direction == 2)
        {
            if(altTexture)
            {this.texture = textures[3];}
            else    
            {this.texture = textures[2];}
        }
        enemyTime++;
    }
    
    public void setDirection(int d) {this.direction = d;}
    
    public Texture texture() {return texture;}

    public void moveX(int x) {xCoord += x;}

    public void moveY(int y) {yCoord += y;}

    public int getX() {return xCoord;}

    public int getY() {return yCoord;}

    public double getMaxHealth() {return maxHealth;}

    public void setAggro(boolean temp) {aggro = temp;}

    public boolean getAggro() {return aggro;}

    public double getDamage() {return damage;}

    public void altTexture(boolean temp) {altTexture = temp;}

    public boolean getAltTexture() {return altTexture;}
    
    public int enemyTime() {return enemyTime;}
    
    public void setEnemyTime(int i) {this.enemyTime = i;}
}
