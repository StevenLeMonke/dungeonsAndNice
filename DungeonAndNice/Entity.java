public class Entity
{
    private double health;
    
    public Entity()
    {
        health = 50.0;
    }

    public Entity(double health)
    {
        this.health = health;
    }
    
    public void updateHealth(double damage)
    {
        health -= damage;
    }
    
    public boolean checkDestroy()
    {
        if(health <=0)
            return true;
        return false;
    }
}