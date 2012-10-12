/**
 * Copyright (c) 2012 Alvin S.J. Ng
 * 
 * Permission is hereby granted, free of charge, to any person obtaining 
 * a copy of this software and associated documentation files (the 
 * "Software"), to deal in the Software without restriction, including 
 * without limitation the rights to use, copy, modify, merge, publish, 
 * distribute, sublicense, and/or sell copies of the Software, and to 
 * permit persons to whom the Software is furnished to do so, subject 
 * to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be 
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT 
 * WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF 
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR 
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT 
 * SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE 
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, 
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR 
 * IN CONNECTION WITH THE SOFTWARE OR 
 * THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 * @author 		Alvin S.J. Ng <alvinsj.ng@gmail.com>
 * @copyright	2012	Alvin S.J. Ng
 * 
 */

package com.stepsdk.android.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.File;

public abstract class AppConfig {
    protected static final String DEFAULT_STRING_VALUE = "";
    protected static final long DEFAULT_LONG_VALUE = 0;
    protected static final int DEFAULT_INTEGER_VALUE = 0;
    protected static final float DEFAULT_FLOAT_VALUE = 0.0f;
    protected static final boolean DEFAULT_BOOLEAN_VALUE = false;
    
    protected static AppConfig instance = null;
    protected SharedPreferences theSharedPreferences;
    
	public static boolean betaMode = false;
    
    public abstract boolean isDevVersion();

    public static File cacheFolder(Context context) {
        return getInstance().getCacheFolder(context);
    }
    
    public abstract String getPreferencesName();
    
    protected AppConfig(Context context) {
        this.theSharedPreferences = context.getSharedPreferences(getPreferencesName(), 0);
    }

    public static void initConfig(AppConfig i) {
        instance = i;
    }

    public static AppConfig getInstance() {
        return instance;
    }
    
    protected Boolean setPreferenceString(String key, String value)
    {
        final SharedPreferences.Editor editor = this.theSharedPreferences.edit();
        editor.putString(key, value);
        return editor.commit();
    }
    
    protected void setPreferenceLong(String key, long value)
    {
        final SharedPreferences.Editor editor = this.theSharedPreferences.edit();
        editor.putLong(key, value);
        editor.commit();
    }
    
    protected void setPreferenceInt(String key, int value)
    {
        final SharedPreferences.Editor editor = this.theSharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }
    
    protected void setPreferenceFloat(String key, float value)
    {
        final SharedPreferences.Editor editor = this.theSharedPreferences.edit();
        editor.putFloat(key, value);
        editor.commit();
    }
    
    protected void setPreferenceBoolean(String key, boolean value)
    {
        final SharedPreferences.Editor editor = this.theSharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }
    
    protected String getPreferenceString(String key)
    {
        if (!this.theSharedPreferences.contains(key))
            return null;
        else
            return this.theSharedPreferences.getString(key, AppConfig.DEFAULT_STRING_VALUE);
    }
    
    protected Long getPreferenceLong(String key)
    {
        if (!this.theSharedPreferences.contains(key))
            return null;
        else
            return this.theSharedPreferences.getLong(key, AppConfig.DEFAULT_LONG_VALUE);
    }
    
    protected Integer getPreferenceInt(String key)
    {
        if (!this.theSharedPreferences.contains(key))
            return null;
        else
            return this.theSharedPreferences.getInt(key, AppConfig.DEFAULT_INTEGER_VALUE);
    }
    
    protected Float getPreferenceFloat(String key)
    {
        if (!this.theSharedPreferences.contains(key))
            return null;
        else
            return this.theSharedPreferences.getFloat(key, AppConfig.DEFAULT_FLOAT_VALUE);
    }
    
    protected Boolean getPreferenceBoolean(String key)
    {
        if (!this.theSharedPreferences.contains(key))
            return null;
        else
            return this.theSharedPreferences.getBoolean(key, AppConfig.DEFAULT_BOOLEAN_VALUE);
    }
    
    protected void removePreference(String key)
    {
        final SharedPreferences.Editor editor = this.theSharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }

    protected abstract File getCacheFolder(Context context);
    
    public static void log(String where, String message){
    	if(betaMode)
    		Log.e(where, message);
    }
    
}
