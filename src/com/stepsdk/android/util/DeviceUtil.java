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
package com.stepsdk.android.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.WindowManager;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class DeviceUtil {
    public static String getDeviceId(Context context) {
    	
        String serviceName = Context.TELEPHONY_SERVICE;
        TelephonyManager tele = (TelephonyManager)context.getSystemService(
                serviceName);
        String deviceId = tele.getDeviceId();
        
        if(deviceId == null || deviceId.isEmpty()){
        	deviceId = Secure.getString(context.getContentResolver(),
                    Secure.ANDROID_ID);
        }
        
        return deviceId;
    }

    public static String getDeviceString() {
        String deviceString = "";
        try {
            deviceString = URLEncoder.encode(DeviceUtil.getDeviceName(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return deviceString;
    }

    public static String getMobileNetworkCode(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager)context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getNetworkOperator();
    }

    public static String getMobileCountryCode(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager)context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getNetworkCountryIso();
    }

    public static String getCarrier(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager)context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getNetworkOperatorName();
    }
    
    public static String getDevice() {
        // TODO: not implemented yet
        return android.os.Build.CPU_ABI;
    }
    
    public static String getDeviceName() {
        StringBuilder deviceSB = new StringBuilder();
        deviceSB.append(Build.MANUFACTURER);
        deviceSB.append("-");
        deviceSB.append(Build.MODEL);
        deviceSB.append("@");
        deviceSB.append(Build.VERSION.RELEASE);
        return deviceSB.toString();
    }
    
    public static int getScreenWidth(Context context) {
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        return display.getWidth();
    } 
    
    public static boolean checkPermission(Context context, String permission){
        int res = context.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);            
    }
    
}
