package in.mcug.linuxication2k18.Prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;

/**
 * Created by aditya on 1/31/18.
 */

public class PrefUtils {
    private static final String VOLUNTEER_NAME = "VOLUNTEER_NAME";
    private static final String SECRET = "SECRET";
    private static final String IS_LOGGED_IN = "IS_LOGGED_IN";

    public static boolean getLoginStatus(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(IS_LOGGED_IN,false);
    }

    public static String getVolunteerName(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getString(VOLUNTEER_NAME,"");
    }
    public static String getSecret(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getString(SECRET,"");
    }

    public static void logOut(Context context){
        SharedPreferences.Editor edit= PreferenceManager.getDefaultSharedPreferences(context).edit();
        edit.putString(VOLUNTEER_NAME,"");
        edit.putString(SECRET,"");
        edit.putBoolean(IS_LOGGED_IN,false);
        edit.apply();
    }

    public static void login(Context context,String name,String secret){
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(context).edit();
        edit.putString(VOLUNTEER_NAME,name);
        edit.putString(SECRET,secret);
        edit.putBoolean(IS_LOGGED_IN,true);
        edit.apply();
    }

}
