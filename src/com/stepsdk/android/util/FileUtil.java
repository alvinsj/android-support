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

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URLEncoder;

public class FileUtil {
    public static String getMimeTypeFromFilePath( String filepath ) {
        MimeTypeMap map = MimeTypeMap.getSingleton();
        String path;
        try {
            path = URLEncoder.encode("file://"+filepath, "UTF-8");
        } catch(Exception e) {
            path = "file://"+filepath;
        }
        // replace slash back to "/"
        path = path.replace("%2F","/");
        path = path.replace("%3A",":");
        path = path.replace("+","%20");
        
        Log.i("Utils",path);        
        String extension = MimeTypeMap.getFileExtensionFromUrl(path);
        
        if( extension == "" ) // try again with custom method
            extension = FileUtil.getFileExtensionFromPath(path);
        
        String mime = map.getMimeTypeFromExtension(extension);
        /*if(mime == null){
            if(path.contains("images"))
                mime = "image/jpeg";
            else if(path.contains("videos"))
                mime = "video/3gp";
            else
                mime = "text/plain";
        }*/
        return mime;
    }
    public static String getFileExtensionFromPath(String filepath) {
        int dot= filepath.lastIndexOf(".");
        int slash= filepath.lastIndexOf("/");

        if(dot>slash)
            return filepath.substring(dot+1,filepath.length()).toLowerCase(); 
        else
            return "";
        
    }
    public static Bitmap decodeFile(File f, final int REQUIRED_SIZE){
        try {
            //Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f),null,o);


            //Find the correct scale value. It should be the power of 2.
            int width_tmp=o.outWidth, height_tmp=o.outHeight;
            int scale=1;
            while(true){
                if(width_tmp/2<REQUIRED_SIZE || height_tmp/2<REQUIRED_SIZE)
                    break;
                width_tmp/=2;
                height_tmp/=2;
                scale*=2;
            }

            //Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize=scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {}
        return null;
    }
}
