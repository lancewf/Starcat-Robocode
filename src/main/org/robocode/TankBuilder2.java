package org.robocode;

import robocode.RobotDeathEvent;
import robocode.ScannedRobotEvent;

public class TankBuilder2 {
	public Tank buildTank(ScannedRobotEvent scannedRobotEvent,
			double xWhenSighted, double yWhenSighted, double headingWhenSighted) {

		String name = scannedRobotEvent.getName();
		double energy = scannedRobotEvent.getEnergy();
		double bearing = scannedRobotEvent.getBearing();
		double distance = scannedRobotEvent.getDistance();
		double heading = scannedRobotEvent.getHeading();
		double velocity = scannedRobotEvent.getVelocity();
		long time = scannedRobotEvent.getTime();

		return buildTank(name, energy, bearing, distance, heading, velocity,
				time, xWhenSighted, yWhenSighted, headingWhenSighted);
	}
	
	public Tank buildTank(String name, double energy, double bearing, 
			double distance, double heading, double velocity, long time, double xWhenSighted, double yWhenSighted, double headingWhenSighted){
		double absoluteBearing = (headingWhenSighted + bearing) % 360.0;

		if (absoluteBearing < 0) {
			absoluteBearing = 360.0 + absoluteBearing;
		}

		double distanceWhenSighted = distance;
		absoluteBearing = Math.toRadians(absoluteBearing); // convert to radians

		double x = xWhenSighted + Math.sin(absoluteBearing)
				* distanceWhenSighted;
		double y = yWhenSighted + Math.cos(absoluteBearing)
				* distanceWhenSighted;

		return new Tank(name, energy, bearing, distance, heading, velocity,
				time, x, y);
	}

	public Tank buildTank(RobotDeathEvent event) {

		String name = event.getName();
		long time = event.getTime();

		return buildTank(name, 0.0, 0.0, 0.0, 0.0, 0.0,
				time, 0.0, 0.0, 0.0);
	}
}
