package apputils;

import android.content.Context;
import android.content.SharedPreferences;
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

    public static void writeToSharedPreferencesBoolean(String sharedPreferenceName,String key, boolean i,Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(sharedPreferenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, i);
        editor.commit();
    }

    public static boolean readFromSharedPreferences(String sharedPreferenceName,String s,Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(sharedPreferenceName, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(s, false);

    }
}
