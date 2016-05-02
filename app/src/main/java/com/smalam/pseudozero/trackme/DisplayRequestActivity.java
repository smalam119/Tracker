package com.smalam.pseudozero.trackme;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import apputils.HandyFunctions;
import config.Config;
import databaseHelpers.RequestHandler;
import databaseHelpers.TalkToDB;

public class DisplayRequestActivity extends AppCompatActivity implements ListView.OnItemClickListener
{
    ListView watcherListView;
    private String JSON_STRING;
    String userName;
    public static final String IS_ACCEPTED = "default";
    public static final String ITEM_OPTION_TYPE = "default2";
    public static final String IS_MINE_REQUEST = "default3";
    public String type,itemOptionType,isMineRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_watchers);

        type = String.valueOf(getIntent().getExtras().get(IS_ACCEPTED));
        itemOptionType = String.valueOf(getIntent().getExtras().get(ITEM_OPTION_TYPE));
        isMineRequest = String.valueOf(getIntent().getExtras().get(IS_MINE_REQUEST));

        userName = HandyFunctions.readFromSharedPreferencesString(Config.SHARED_PREF_NAME,Config.USER_SHARED_PREF,this);
        watcherListView = (ListView) findViewById(R.id.watchers_list_view_profile);
        watcherListView.setOnItemClickListener(this);
        getAllWatchers();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        String watcher = parent.getItemAtPosition(position).toString();

        Toast.makeText(this,watcher,Toast.LENGTH_LONG).show();

        if(itemOptionType.equals("yes"))
        {
            showDialogOnlyDelete(watcher);
        }

        if(itemOptionType.equals("no"))
        {
            showDialog(watcher);
        }
    }

    protected void showDialogOnlyDelete(final String watcherName)
    {

        AlertDialog alertbox = new AlertDialog.Builder(this)
                .setMessage("Do you want to delete " + watcherName +" as a watcher ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1)
                    {
                        TalkToDB.declineRequest(watcherName,userName,DisplayRequestActivity.this);
                        getAllWatchers();
                    }
                })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1)
                    {

                    }
                })
                .show();

    }

    protected void showDialog(final String requester)
    {

        AlertDialog alertbox = new AlertDialog.Builder(this)
                .setMessage(requester +" wants you to be a watcher ?")
                .setPositiveButton("Accept", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1)
                    {
                        TalkToDB.acceptWatchers(requester,userName,DisplayRequestActivity.this,"1");
                    }
                })

                .setNegativeButton("Decline", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1)
                    {
                        TalkToDB.declineRequest(requester,userName,DisplayRequestActivity.this);
                        getAllWatchers();
                    }
                })
                .show();

    }

    private void getAllWatchers(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DisplayRequestActivity.this,"Fetching Data","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showWatchers();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = null;
                if(isMineRequest.equals("1"))
                {
                    s = rh.sendGetRequest(Config.URL_MY_REQUESTS,userName);
                }
                if(isMineRequest.equals("0"))
                {
                    s = rh.sendGetRequest(Config.URL_OTHERS_REQUEST,userName);
                }

                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private void showWatchers()
    {
        JSONObject jsonObject = null;
        ArrayList<String> list = new ArrayList<String>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = null;
                try {
                    jo = result.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String isAccepted = jo.getString(Config.TAG_IS_ACCEPTED);
                String watcherName = jo.getString(Config.TAG_WATCHER_NAME);

                if(isAccepted.equals(type) && isMineRequest.equals("1"))
                {
                    list.add(watcherName);
                }
                if(isMineRequest.equals("0"))
                {
                    list.add(watcherName);
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> simpleAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                list);

        watcherListView.setAdapter(simpleAdapter);

    }
}
