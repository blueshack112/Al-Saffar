package com.fyp.al_saffar;

import android.content.Context;
import android.content.SharedPreferences;

public class Utils {
    public static String getUserId(Context context) {
        SharedPreferences sp =
                context.getSharedPreferences(Values.SP_FILE_KEY, Context.MODE_PRIVATE);
        String userID = sp.getString(Values.SPF_USER_ID_KEY, "-1");
        return userID;
    }
}
