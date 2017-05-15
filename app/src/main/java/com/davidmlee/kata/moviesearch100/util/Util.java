package com.davidmlee.kata.moviesearch100.util;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.davidmlee.kata.moviesearch100.core.MyApp;

import org.json.JSONObject;

/**
 * (non-Javadoc)
 *
 * Created by davidmlee on 5/10/17.
 */
public class Util {
    /**
     * @param obj jsonobject
     * @param key key in json object to look for
     * @param defValue The default value to return
     */
    public static String getString(JSONObject obj, String key, String defValue) {
        String res = defValue;
        if (obj != null && obj.has(key)) {
            try {
                Object val = asObject(obj.get(key));
                if (val != null && !val.toString().isEmpty()
                        && !val.toString().equalsIgnoreCase("null")) {
                    res = val.toString();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return res;
    }

    public static boolean getBool(JSONObject obj, String key) {
        boolean res = false;
        if (obj != null && obj.has(key)) {
            try {
                Object val = asObject(obj.get(key));
                if (val != null && !val.toString().isEmpty()
                        && !val.toString().equalsIgnoreCase("null")) {
                    res = Boolean.valueOf(val.toString().toLowerCase());
                }
            } catch (Exception ex) {
                MyApp.handleException("JSONException", ex, false);
            }
        }
        return res;
    }

    /**
     * @param obj The Object
     */
    private static Object asObject(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj == JSONObject.NULL) {
            return null;
        }
        return obj;
    }

    /**
     * @param activity The activity on which the soft keyboard is displayed
     */
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager)activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * @param str
     * @return
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty() || str.equalsIgnoreCase("null");
    }

}
