package com.stepsdk.android.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Observable;

public abstract class EventObservable {
	private HashMap<String, CustomObservable> mObservables = new HashMap<String, CustomObservable>();
	public abstract ArrayList<String> events();
	
	public class CustomObservable extends Observable{
		private ArrayList<EventObserver> mObservers = new ArrayList<EventObserver>();
		public void notifyObservers(Event event) {
			if(!hasChanged())
				return;
			
			Iterator<EventObserver> i = mObservers.iterator();
			
			while(i.hasNext()){
				EventObserver o = i.next();
				if(o != null)
					o.update(EventObservable.this, event);
			}
			clearChanged();
		}
		@Override
		public boolean hasChanged(){
			return mChanged == true; 
		}
		
		public void addObserver(EventObserver observer){
			mObservers.add(observer);
		}
	}

	public void notifyObservers(Event event) {
		String eventName = event.getName();

		if(events().contains(eventName) && mObservables.containsKey(eventName)){
			CustomObservable o = mObservables.get(eventName);
			o.notifyObservers(event);
		}
	}
	
	public void addObserver(String eventName, EventObserver eventObserver){
		if( events() != null && events().contains(eventName)){
			if(!mObservables.containsKey(eventName))
				mObservables.put(eventName, new CustomObservable());
			CustomObservable o = mObservables.get(eventName);
			o.addObserver(eventObserver);
		}
	}
	
	private boolean mChanged;
	public void setChanged(){
		mChanged = true;
	}

	
}
