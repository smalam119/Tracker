package databaseHelpers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;
import java.util.HashMap;
import config.Config;

/**
 * Created by SAYED on 4/12/2016.
 */
public class TalkToDB
{


    public static void addUser(final String userName, final String password, final String phoneNumber, final Activity activity)
    {


        class AddUser extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(activity,"Registering...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(activity, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(Config.KEY_USER_NAME,userName);
                params.put(Config.KEY_PASSWORD,password);
                params.put(Config.KEY_PHONE_NUMBER,phoneNumber);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_ADD, params);
                return res;
            }
        }

        AddUser ae = new AddUser();
        ae.execute();
    }


    public static void editUserName(final String rawUserName, final String userName, final Activity activity)
    {


        class EditUserName extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(activity,"Registering...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(activity, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(Config.KEY_RAW_USER_NAME,rawUserName);
                params.put(Config.KEY_USER_NAME,userName);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_UPDATE_USER_NAME, params);
                return res;
            }
        }

        EditUserName ae = new EditUserName();
        ae.execute();
    }

    public static void updateLocation(final String userName,final String currentLat, final String currentLng,final Activity activity)
    {


        class UpdateUserLocation extends AsyncTask<Void,Void,String>
        {

            ProgressDialog loading;

            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
                loading = ProgressDialog.show(activity,"Registering...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s)
            {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(activity, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v)
            {
                HashMap<String,String> params = new HashMap<>();
                params.put(Config.KEY_USER_NAME,userName);
                params.put(Config.KEY_CURRENT_LAT_,currentLat);
                params.put(Config.KEY_CURRENT_LNG_,currentLng);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_UPDATE_LOCATION, params);
                return res;
            }
        }

        UpdateUserLocation ae = new UpdateUserLocation();
        ae.execute();
    }


}
