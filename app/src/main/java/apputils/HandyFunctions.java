package apputils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by SAYED on 4/11/2016.
 */
public class HandyFunctions
{

    public static void getLongToast(String info, Context context)
    {
        Toast.makeText(context,info,Toast.LENGTH_LONG).show();
    }

    public static void getShortToast(String info,Context context)
    {
        Toast.makeText(context,info,Toast.LENGTH_SHORT).show();
    }
}
