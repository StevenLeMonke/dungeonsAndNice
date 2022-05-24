public class Entity
{
    private double health;
    private int direction; //1234 uprightdownleft
    public Entity()
    {
        health = 50.0;
        direction = 3;
    }

    public Entity(double health, int direction)
    {
        this.health = health;
        this.direction = direction;
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
    
    public void setDirection(int d) {this.direction = d;}
    public int getDirection() {return direction;}
    public double getHealth() {return health;}
}