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

}
