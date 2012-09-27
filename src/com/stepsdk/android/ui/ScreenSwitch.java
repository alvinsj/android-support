package com.stepsdk.android.ui;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ScreenSwitch {
	
	String currentPage = null;
	
	public static final String IDLE = "idle";
	public static final String LOADING = "loading";
	public static final String LOADED = "loaded";
	public static final String ERROR = "error";

	public ScreenSwitch(){
		screenIdle();
	}
	
	public ScreenSwitch to(String page){
		
		if(page.equals(IDLE)){
			screenIdle();
    		currentPage = page;

		}else if(page.equals(LOADING)){
			screenLoading();
    		currentPage = page;

		}else if(page.equals(LOADED)){
			screenLoaded();
    		currentPage = page;

		}else if(page.equals(ERROR)){
			screenError();
    		currentPage = page;

		}else{
			Method main;
			try {
				main = getClass().getDeclaredMethod(page);
    			main.invoke(this);
        		currentPage = page;

			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} 
		}
		
		return this;
	}
	
	public void screenIdle(){}
	
	public void screenLoading(){}
	
	public void screenLoaded(){}
	    	
	public void screenError(){}
	
}