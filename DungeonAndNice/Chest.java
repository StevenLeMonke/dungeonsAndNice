import com.badlogic.gdx.*; 
import com.badlogic.gdx.graphics.Texture;

public class Chest extends Entity
{
    private Texture texture;
    private Texture[] textures;

    public Chest()
    {
        super(200, 3);

        textures = new Texture[] {new Texture("assets/ChestClosed.png"), new Texture("assets/ChestFull.png"), new Texture("assets/ChestEmpty.png")};
        texture = textures[0];
    }

    public void open()
    {
        texture = textures[1];
        Item.getItem();
    }
}
