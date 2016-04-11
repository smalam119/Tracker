package apputils;

/**
 * Created by SAYED on 4/11/2016.
 */
import android.app.Application;
import android.content.Context;

public class MyApplication extends Application
{
    private static Context context;

    public void onCreate()
    {
        super.onCreate();
        MyApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }
}
