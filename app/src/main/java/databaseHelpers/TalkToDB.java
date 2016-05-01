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

    public static void deleteUser(final String userName, final Activity activity){
        class DeleteUSer extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(activity, "Updating...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(activity, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Config.URL_DELETE_USER,userName);
                return s;
            }
        }

        DeleteUSer de = new DeleteUSer();
        de.execute();
    }

    public static void declineRequest(final String requester, final String userName, final Activity activity){
    class DeleteUSer extends AsyncTask<Void,Void,String> {
        ProgressDialog loading;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(activity, "Updating...", "Wait...", false, false);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            loading.dismiss();
            Toast.makeText(activity, s, Toast.LENGTH_LONG).show();
        }

        @Override
        protected String doInBackground(Void... v)
        {
            HashMap<String,String> params = new HashMap<>();
            params.put(Config.TAG_USER_NAME,userName);
            params.put(Config.TAG_REQUESTER_NAME,requester);
            RequestHandler rh = new RequestHandler();
            String s = rh.sendPostRequest(Config.URL_REJECT_WATCHERS,params);
            return s;
        }
    }

    DeleteUSer de = new DeleteUSer();
    de.execute();
}

    public static void addWatchers(final String user, final String watcher, final Activity activity)
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
                params.put(Config.KEY_USER_NAME,user);
                params.put(Config.KEY_WATCHER,watcher);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_ADD_WATCHERS, params);
                return res;
            }
        }

        AddUser ae = new AddUser();
        ae.execute();
    }

    public static void acceptWatchers(final String user, final String watcher, final Activity activity, final String isAccepted)
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
                params.put(Config.KEY_USER_NAME,user);
                params.put(Config.KEY_WATCHER,watcher);
                params.put(Config.KEY_IS_ACCEPT,isAccepted);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_ACCEPT_WATCHERS, params);
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

    public static void updateLocation(final String userName,final String currentLat, final String currentLng,final String isInDanger,final Activity activity)
    {


        class UpdateUserLocation extends AsyncTask<Void,Void,String>
        {

            ProgressDialog loading;

            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
                loading = ProgressDialog.show(activity,"Updating...","Wait...",false,false);
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
                params.put(Config.KEY_IS_IN_DANGER,isInDanger);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_UPDATE_LOCATION, params);
                return res;
            }
        }

        UpdateUserLocation ae = new UpdateUserLocation();
        ae.execute();
    }


    public static void sendTrackRequest(final String userName, final String watcherName, final Activity activity)
    {


        class StartTracking extends AsyncTask<Void,Void,String> {

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
                params.put(Config.KEY_WATCHER,watcherName);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_NOTIFICATION_SERVER_SEND_TRACK_REQUEST, params);
                return res;
            }
        }

        StartTracking ae = new StartTracking();
        ae.execute();
    }




}
