package org.robocode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import robocode.RobotDeathEvent;
import robocode.ScannedRobotEvent;

public class TankManager {
	private List<Tank> tanks = Collections.synchronizedList(new ArrayList<Tank>());
	private TankBuilder2 tankBuilder = new TankBuilder2();
	private BotCatable botCatable;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	public TankManager(BotCatable botCatable){
		this.botCatable = botCatable;
	}
	// -------------------------------------------------------------------------
	// Public Members
	// -------------------------------------------------------------------------
	
	public List<Tank> getTanks() {
		update();
		List<Tank> copiedTanks = null;
		synchronized (tanks) {
			copiedTanks = new ArrayList<Tank>(tanks);
		}
		
		return copiedTanks;
	}
	
	public void onScannedRobot(ScannedRobotEvent e, double robotX,
			double robotY, double robotHeading) {
		Tank newTank = tankBuilder.buildTank(e, robotX, robotY, robotHeading);
		Tank sameTank = findTank(newTank.getName());

		if (sameTank != null) {
			updateTank(newTank, sameTank);
		} else {
			tanks.add(newTank);
		}
		
		update();
	}
	
	public void onRobotDeath(RobotDeathEvent event) {
		Tank sameTank = findTank(event.getName());
		if (sameTank != null) {
			tanks.remove(sameTank);
		}
		update();
	}
	
	// -------------------------------------------------------------------------
	// Private Members
	// -------------------------------------------------------------------------
	
	private void update(){
		List<Tank> removedTanks = new ArrayList<Tank>();
		synchronized (tanks) {
			for (Tank tank : tanks) {
				long diff = botCatable.getTime() - tank.getTime();
				if (diff > 500) {
					removedTanks.add(tank);
				}
			}
		}
		
		for(Tank tank: removedTanks){
			tanks.remove(tank);
		}
	}
	
	private void updateTank(Tank newestTank, Tank originalTank){
		originalTank.setEnergy(newestTank.getEnergy());
		originalTank.setBearing(newestTank.getBearing());
		originalTank.setDistance(newestTank.getDistance());
		originalTank.setHeading(newestTank.getHeading());
		originalTank.setVelocity(newestTank.getVelocity());
		originalTank.setX(newestTank.getX());
		originalTank.setY(newestTank.getY());
		originalTank.setTime(newestTank.getTime());
	}
	
	private Tank findTank(String tankName){
		synchronized (tanks) {
			for (Tank tank : tanks) {
				if (tank.getName().equals(tankName)) {
					return tank;
				}
			}
		}
		return null;
	}
}
