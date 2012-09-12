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
package com.stepsdk.android.event;

public class Event {
    protected String mName;
    public Object data;
    public Object[] datum;
    private int mId;
    
    public static int eventCount = 1;
    
    public Event(String name){
        mName = name;
        mId = name.hashCode() + eventCount++;
    }
    
    public Event(Object obj, String name){
        mName = name;
        data = obj;
        mId = name.hashCode() + eventCount++;
    }
    
    public String getName(){
        return mName;
    }
    public static boolean equals(Object data, String event){
        if(data instanceof Event && ((Event)data).getName().equals(event))
            return true;
        return false;
    }
    
    public static boolean equals(Object data, Object caller, String event){
        if(data instanceof Event 
                && ((Event)data).getName().equals(event) 
                && caller==((Event)data).data)
            return true;
        return false;
    }
    
    public Event withObjects(Object... obj){
        datum = new Object[obj.length];
        for(int i = 0; i< obj.length; i++)
            datum[i]=obj[i];
        return this;
    }
    
    public Event withObject(Object obj){
        data = obj;
        return this;
    }
    
    public int getId(){
    	return mId;
    }
}
