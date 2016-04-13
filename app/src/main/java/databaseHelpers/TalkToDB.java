package databaseHelpers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    public static void loginUser(final Activity activity, final String userName,final String givenPassword)
    {
        class GetUser extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(activity,"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                matchPassword(activity, s, givenPassword);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Config.URL_GET_USER, userName);
                return s;
            }
        }
        GetUser ge = new GetUser();
        ge.execute();
    }

    private static void matchPassword(Context context,String json,final String givenPassword)
    {
        try
        {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            String password = c.getString(Config.TAG_PASSWORD);
            if(password.equals("null"))
            {
                Toast.makeText(context, "No user found", Toast.LENGTH_LONG).show();
            }
            else if(givenPassword.equals(password))
            {
                Toast.makeText(context, "logged in", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(context, "wrong password", Toast.LENGTH_LONG).show();
            }


        }

        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
}
