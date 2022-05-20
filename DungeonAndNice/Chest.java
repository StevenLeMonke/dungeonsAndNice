import com.badlogic.gdx.*; 
import com.badlogic.gdx.graphics.Texture;

public class Chest extends Entity
{
    private Texture texture;
    private Texture[] textures;
    
    private int xCoord;
    private int yCoord;

    public Chest(int x, int y)
    {
        super(200, 3);
        
        this.xCoord = x;
        this.yCoord = y;
        textures = new Texture[] {new Texture("assets/ChestClosed.png"), new Texture("assets/ChestFull.png"), new Texture("assets/ChestEmpty.png")};
        texture = textures[0];
        
    }

    public void open()
    {
        texture = textures[1];
        Item.getItem();
    }
    
    public int getX() {return xCoord;}

    public int getY() {return yCoord;}
    
    public Texture texture() {return texture;}
}
