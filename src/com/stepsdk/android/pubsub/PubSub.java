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
package com.stepsdk.android.pubsub;

import java.util.HashMap;
import java.util.Observable;

public class PubSub extends Observable{
	public Observable observarable;
	
	public HashMap<String, Observable> mTopicObservables = new HashMap<String, Observable>();
	
	public void subscribe( String topic, Callback callback ){
		Observable o = null;
		
		if( mTopicObservables.containsKey(topic) )
			o = mTopicObservables.get(topic);
		else
			o = makeObservable();
		
		o.addObserver( callback );
		mTopicObservables.put(topic, o);
		
	}
	
	public void unsubscribe( String topic, Callback callback){
		if( mTopicObservables.containsKey(topic) ){
			mTopicObservables.get(topic).deleteObserver(callback);
		}
			
	}
	
	protected void publish(String topic){
		if(mTopicObservables.containsKey(topic)){
			Observable o = mTopicObservables.get(topic);
			o.notifyObservers();
		}
	}
	
	public class Publisher extends Observable{
		@Override
		public void notifyObservers() {
			setChanged();
			super.notifyObservers();
			clearChanged();
		}
	}
	
	private Observable makeObservable(){
		return new Publisher();
	}
	


	
}
