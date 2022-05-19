
public class Item
{
    public static double damageMulti = 1;
    public static double moveSpeed = 1;
    public static double attackSpeed = 1;
   
    
    public static void getItem()
    {
        int randI = (int) (Math.random() * 3);
        if(randI == 0)
            damageMulti += 0.3;
        if(randI == 1)
            moveSpeed += 0.3;
        if(randI == 2)
            attackSpeed += 0.3;
    }
}
