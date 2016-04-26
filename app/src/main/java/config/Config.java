package config;

import android.app.Activity;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

/**
 * Created by SAYED on 4/12/2016.
 */
public class Config
{
    //Address of our scripts of the CRUD
    public static final String URL_ADD="http://jenkisoutside-001-site1.1tempurl.com/test/register.php";
    public static final String URL_GET_USER= "http://jenkisoutside-001-site1.1tempurl.com/test/getUser.php?userName=";
    public static final String URL_UPDATE_USER = "http://jenkisoutside-001-site1.1tempurl.com/test/updateUser.php";
    public static final String GET_ALL_USER = "http://jenkisoutside-001-site1.1tempurl.com/test/getAll.php";
    public static final String URL_UPDATE_USER_NAME= "http://jenkisoutside-001-site1.1tempurl.com/test/updateUserName.php";
    public static final String URL_UPDATE_LOCATION= "http://jenkisoutside-001-site1.1tempurl.com/test/updateLocation.php";
    public static final String URL_WATCHED_LOCATION= "http://jenkisoutside-001-site1.1tempurl.com/test/getWatchedsLocation.php?watcherName=";
    public static final String URL_ADD_WATCHERS= "http://jenkisoutside-001-site1.1tempurl.com/test/addWatchers.php";
    public static final String URL_ACCEPT_WATCHERS= "http://jenkisoutside-001-site1.1tempurl.com/test/acceptWatchers.php";
    public static final String URL_ALL_WATCHERS= "http://jenkisoutside-001-site1.1tempurl.com/test/getAllWatchers.php?userName=";
    public static final String URL_DELETE_USER= "http://jenkisoutside-001-site1.1tempurl.com/test/deleteUser.php?userName=";


    //Keys that will be used to send the request to php scripts

    public static final String KEY_USER_ID ="userId";
    public static final String KEY_USER_NAME = "userName";
    public static final String KEY_RAW_USER_NAME = "rawUserName";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_PHONE_NUMBER = "phoneNumber";
    public static final String KEY_NO_OF_WATCHERS = "noOfWatchers";
    public static final String KEY_NO_OF_WATCHINGS = "noOfWatching";
    public static final String KEY_CURRENT_LAT_ = "currentLat";
    public static final String KEY_CURRENT_LNG_ = "currentLng";
    public static final String KEY_IS_IN_DANGER = "isInDanger";
    public static final String KEY_WATCHER = "watcherName";
    public static final String KEY_IS_ACCEPT = "isAccept";



    //JSON Tags
    public static final String TAG_JSON_ARRAY="result";
    public static final String TAG_USER_ID ="userId";
    public static final String TAG_USER_NAME = "userName";
    public static final String TAG_PASSWORD = "password";
    public static final String TAG_PHONE_NUMBER = "phoneNumber";
    public static final String TAG_NO_OF_WATCHERS = "noOfWatchers";
    public static final String TAG_NO_OF_WATCHINGS = "noOfWatchers";
    public static final String TAG_RAW_USER_NAME = "rawUserName";
    public static final String TAG_CURRENT_LAT_ = "currentLat";
    public static final String TAG_CURRENT_LNG_ = "currentLng";
    public static final String TAG_IS_IN_DANGER = "isInDanger";
    public static final String TAG_IS_ACCEPTED = "isAccepted";
    public static final String TAG_WATCHER_NAME = "watcherName";

    public static final String SHARED_PREF_NAME = "trackMe";
    public static final String USER_SHARED_PREF = "userName";
    public static final String USER_PASSWORD_PREF = "password";
    public static final String WATCHER_SHARED_PREF = "watcherName";
    public static final String LOGGED_IN_SHARED_PREF = "loggedIn";
    public static final String SENT_TOKEN_TO_SERVER = "SENT_TOKEN_TO_SERVER";
    public static final String REG_SUCCESS = "REG_SUCCESS";
    public static final String SENT_UNREG_REQUEST_TO_SERVER = "SENT_UNREG_REQUEST_TO_SERVER";
    public static final String GOT_TOKEN_FROM_GCM = "GOT_TOKEN_FROM_GCM";
    public static final String REGISTRATION_COMPLETE = "REGISTRATION_COMPLETE";
    public static final String REG_ID = "";

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    // put your server registration url here, must end with a /
    public static final String URL_NOTIFICATION_SERVER = "http://jenkisoutside-001-site1.1tempurl.com/gcmphp/";

    public static final String URL_NOTIFICATION_SERVER_SEND_REQUEST = "http://jenkisoutside-001-site1.1tempurl.com/gcmphp/gcm_main3.php";

    public static String notificationType[] = {"default", "type1"};

    /**
     * Tag used on log messages.
     */
    static final String TAG = "androidpushnotification";

    public static final String DISPLAY_MESSAGE_ACTION =
            "com.smalam.pseudozero.gcmtest";

    static final String EXTRA_MESSAGE = "message";

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    public static boolean checkPlayServices(Activity activity) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(activity);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(activity, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                activity.finish();
            }
            return false;
        }
        return true;
    }
}
