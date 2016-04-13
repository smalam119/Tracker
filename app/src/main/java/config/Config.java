package config;

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


    //Keys that will be used to send the request to php scripts

    public static final String KEY_USER_ID ="userId";
    public static final String KEY_USER_NAME = "userName";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_PHONE_NUMBER = "phoneNumber";
    public static final String KEY_NO_OF_WATCHERS = "noOfWatchers";
    public static final String KEY_NO_OF_WATCHINGS = "noOfWatching";

    //JSON Tags
    public static final String TAG_JSON_ARRAY="result";
    public static final String TAG_USER_ID ="userId";
    public static final String TAG_USER_NAME = "userName";
    public static final String TAG_PASSWORD = "password";
    public static final String TAG_PHONE_NUMBER = "phoneNumber";
    public static final String TAG_NO_OF_WATCHERS = "noOfWatchers";
    public static final String TAG_NO_OF_WATCHINGS = "noOfWatchers";

    public static final String SHARED_PREF_NAME = "trackMe";
    public static final String USER_SHARED_PREF = "email";
    public static final String LOGGEDIN_SHARED_PREF = "loggedIn";

    //id to pass with intent
    public static final String USER_ID = "observerId";
}
