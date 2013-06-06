package com.stepsdk.android.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.util.Log;

public class ZipUtil {
	
    public static class Decompress { 
  	  private String _zipFile; 
  	  private String _location; 

  	  public Decompress(String zipFile, String location) { 
  	    _zipFile = zipFile; 
  	    _location = location; 

  	    _dirChecker(""); 
  	  } 

  	  public void unzip() { 
  	    try  { 
  	      FileInputStream fin = new FileInputStream(_zipFile); 
  	      BufferedInputStream fbin = new BufferedInputStream(fin);

  	      ZipInputStream zin = new ZipInputStream(fbin); 
  	      BufferedInputStream bin = new BufferedInputStream(zin);

  	      ZipEntry ze = null; 
  	      while ((ze = zin.getNextEntry()) != null) { 
  	        Log.e("Decompress", "Unzipping " + ze.getName()); 

  	        if(ze.isDirectory()) { 
  	          _dirChecker(ze.getName()); 
  	        } else { 
  	        	String absolutePath = _location + ze.getName();
  	        	String dirPath = absolutePath.
  	    	    	     substring(0,absolutePath.lastIndexOf(File.separator));
  	        	File test = new File(dirPath);
  	        	if(!test.exists()) {
  	        	    test.mkdirs();
  	        	}
  	        	test = new File(absolutePath);
  	        	if(test.exists())
  	        		continue;
  	          FileOutputStream fout = new FileOutputStream(_location + ze.getName()); 
  	          BufferedOutputStream bout = new BufferedOutputStream(fout);
//  	          for (int c = zin.read(); c != -1; c = zin.read()) { 
//  	            fout.write(c); 
//  	          } 
  	          byte b[] = new byte[2048];
  	          int n;
  	          while ((n = bin.read(b,0,2048)) >= 0) {
  	            bout.write(b,0,n);
  	          }

  	          bout.flush();
  	          bout.close();
  	          zin.closeEntry();  
  	          bout = null;
  	          fout = null;
  	          System.gc();
  	        } 

  	      } 
  	      zin.close(); 
  	    } catch(Exception e) { 
  	      Log.e("Decompress", "unzip", e); 
  	    } 

  	  } 

  	  private void _dirChecker(String dir) { 
  	    File f = new File(_location + dir); 

  	    if(!f.isDirectory()) { 
  	      f.mkdirs(); 
  	    } 
  	  } 
  	} 
}
