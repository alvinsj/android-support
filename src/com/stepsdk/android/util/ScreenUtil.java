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

import android.app.Activity;
import android.content.res.Configuration;
import android.view.Display;

public class ScreenUtil {
    public static Orientation getScreenOrientation(Activity _activity) {
        Display getOrient = _activity.getWindowManager().getDefaultDisplay();

        Orientation orientation = Orientation.PORTRAIT;
        int orient = getOrient.getOrientation();
        if (orient == 1) {
            orientation = Orientation.PORTRAIT;
        } else if (orient == 2) {
            orientation = Orientation.LANDSCAPE;
        }

        // Sometimes you may get undefined orientation Value is 0
        // simple logic solves the problem compare the screen
        // X,Y Co-ordinates and determine the Orientation in such cases
        if (orient == Configuration.ORIENTATION_UNDEFINED) {

            Configuration config = _activity.getResources().getConfiguration();
            orient = config.orientation;

            if (orient == Configuration.ORIENTATION_UNDEFINED) {
                // if height and widht of screen are equal then
                // it is square orientation
                if (getOrient.getWidth() == getOrient.getHeight()) {
                    orientation = Orientation.PORTRAIT;
                } else { // if widht is less than height than it is portrait
                    if (getOrient.getWidth() < getOrient.getHeight()) {
                        orientation = Orientation.PORTRAIT;
                    } else { // if it is not any of the above it will defineitly
                             // be landscape
                        orientation = Orientation.LANDSCAPE;
                    }
                }
            }
        }
        return orientation; // return value 1 is portrait and 2 is Landscape
                            // Mode
    }

    public enum Orientation {
        UNKNOWN, PORTRAIT, LANDSCAPE, SQUARE
    }
}
