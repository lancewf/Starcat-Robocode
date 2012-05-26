package org.robocode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import robocode.BulletHitEvent;
import robocode.BulletMissedEvent;

public class BulletManager {

	// -------------------------------------------------------------------------
	// Private Data
	// -------------------------------------------------------------------------
	
	private long maximumBulletAge = 80;
	
	private List<BulletHitEvent> bulletHitEvents = 
			Collections.synchronizedList(new ArrayList<BulletHitEvent>());
	
	private List<BulletMissedEvent> bulletMissedEvents = 
			Collections.synchronizedList(new ArrayList<BulletMissedEvent>());
	
	private BotCatable botCatable;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	public BulletManager(BotCatable botCatable){
		this.botCatable = botCatable;
	}
	
	// -------------------------------------------------------------------------
	// Public Members
	// -------------------------------------------------------------------------
	
	public void setMaximumBulletAge(long maximumBulletAge){
		this.maximumBulletAge = maximumBulletAge;
	}
	
	public List<BulletHitEvent> getRecentBulletHitEvents(){
		update();
		
		List<BulletHitEvent> copiedBulletHitEvents = null;
		synchronized (bulletHitEvents) {
			copiedBulletHitEvents = new ArrayList<BulletHitEvent>(bulletHitEvents);
		}
		
		return copiedBulletHitEvents;
	}
	
	public List<BulletMissedEvent> getRecentBulletMissedEvents(){
		update();
		List<BulletMissedEvent> copiedBulletMissedEvents = null;
		synchronized (bulletMissedEvents) {
			copiedBulletMissedEvents = new ArrayList<BulletMissedEvent>(bulletMissedEvents);
		}
		return copiedBulletMissedEvents;
	}
	
	public void add(BulletHitEvent event){
		bulletHitEvents.add(event);
		
		update();
	}
	
	public void add(BulletMissedEvent event){
		bulletMissedEvents.add(event);
		
		update();
	}
	
	// -------------------------------------------------------------------------
	// Private Members
	// -------------------------------------------------------------------------
	
	private void update(){
		long currentTime = botCatable.getTime();
		
		List<BulletHitEvent> removedBulletHitEvents = new ArrayList<BulletHitEvent>();
		synchronized (bulletHitEvents) {
			for (BulletHitEvent bulletHitEvent : bulletHitEvents) {
				long time = bulletHitEvent.getTime();

				long diff = currentTime - time;

				if (diff > maximumBulletAge) {
					removedBulletHitEvents.add(bulletHitEvent);
				}
			}
		}
		
		for(BulletHitEvent bulletHitEvent : removedBulletHitEvents){
			bulletHitEvents.remove(bulletHitEvent);
		}
		
		List<BulletMissedEvent> removedBulletMissedEvents = new ArrayList<BulletMissedEvent>();
		synchronized (bulletMissedEvents) {
			for (BulletMissedEvent bulletMissedEvent : bulletMissedEvents) {
				long time = bulletMissedEvent.getTime();

				long diff = currentTime - time;

				if (diff > maximumBulletAge) {
					removedBulletMissedEvents.add(bulletMissedEvent);
				}
			}
		}
		
		for(BulletMissedEvent bulletMissedEvent : removedBulletMissedEvents){
			bulletMissedEvents.remove(bulletMissedEvent);
		}
	}
}
