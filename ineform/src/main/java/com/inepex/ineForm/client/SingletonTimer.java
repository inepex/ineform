package com.inepex.ineForm.client;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.google.gwt.user.client.Timer;
import com.google.inject.Singleton;

@Singleton
public class SingletonTimer {

	private final TreeMap<Long, Runnable> tasks = new TreeMap<Long, Runnable>(); 
	private Timer timer;
	
	public void scheduleDelayed(long delayInMs, Runnable task) {
		long when = System.currentTimeMillis()+delayInMs;
		while(tasks.containsKey(when))
			when++;
			
		tasks.put(when, task);
		awakeTimer();
	}

	private void awakeTimer() {
		if(timer==null) {
			timer=new Timer() {
				
				@Override
				public void run() {
					doTimerJob();
				}
			};
			
			timer.scheduleRepeating(100);
		}
	}
	
	private void stopTimer() {
		timer.cancel();
		timer=null;
	}
	
	private void doTimerJob() {
		long now = System.currentTimeMillis();
		Iterator<Entry<Long, Runnable>> it = tasks.entrySet().iterator();
		while(it.hasNext()) {
			Entry<Long, Runnable> entry = it.next();
			if(now>=entry.getKey()) {
				try {
					entry.getValue().run();
				} catch(Exception e) {
					e.printStackTrace();
				}
				it.remove();
			} else {
				//treeset is ordered by timestamps
				break;
			}
		}
		
		if(tasks.isEmpty())
			stopTimer();
	}
}
