
public class Tile
{
    private boolean traversable;
    private int time; //milisecond
    
    public Tile()
    {
       traversable = true;
       time = 100;
    }
    
    public Tile(boolean traversable)
    {
        this.traversable = traversable;
        this.time = 100;
    }
    
    public Tile(boolean traversable, int time)
    {
        this.traversable = traversable;
        this.time = time;
    }
}
