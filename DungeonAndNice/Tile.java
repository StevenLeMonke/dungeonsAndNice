import com.badlogic.gdx.*; 
import com.badlogic.gdx.graphics.Texture;
public class Tile
{
    private boolean traversable;
    private int time; // 1/60 of a second
    private Texture texture;
    
    public Tile()
    {
       traversable = true;
       time = 100;
       texture = new Texture("assets/Stone.png");
    }
    
    public Tile(boolean traversable)
    {
        this.traversable = traversable;
        this.time = 20;
        this.texture = new Texture("assets/Barrier.png");
    }
    
    public Tile(boolean traversable, int time, String texture)
    {
        this.traversable = traversable;
        this.time = time;
        this.texture = new Texture("assets/" + texture);
    }
    
    public int getTime() {return time;}
    public boolean isTraversable() {return traversable;}
    public Texture getTexture() {return texture;}
}
