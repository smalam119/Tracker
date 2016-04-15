package databaseHelpers;

import java.util.Random;

/**
 * Created by SAYED on 4/15/2016.
 */
public class UserNameGenarators
{
    public static int oddOrEven(int id)
    {
        if(id % 2 == 0)
        {
            return 0;
        }

        else
        {
            return 1;
        }
    }

    public static char randomCharactor()
    {
        Random r = new Random();
        char c = (char) (r.nextInt(26) + 'a');

        return c;
    }
}
