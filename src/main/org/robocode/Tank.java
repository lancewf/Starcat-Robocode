package org.robocode;

public class Tank {

	private String name;
	private double energy;
	private double bearing;
	private double distance;
	private double heading;
	private double velocity;
	private double x;
	private double y;
	private long time;
	
	public Tank(String name, double energy, double bearing, 
			double distance, double heading, double velocity, long time, double x, double y){
		this.name = name;
		this.energy = energy;
		this.bearing = bearing;
		this.distance = distance;
		this.heading = heading;
		this.velocity = velocity;
		this.time = time;
		this.x = x;
		this.y = y;
	}
	
	public boolean isAlive(){
		return energy > 0;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getEnergy() {
		return energy;
	}
	public void setEnergy(double energy) {
		this.energy = energy;
	}
	public double getBearing() {
		return bearing;
	}
	public void setBearing(double bearing) {
		this.bearing = bearing;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public double getHeading() {
		return heading;
	}
	public void setHeading(double heading) {
		this.heading = heading;
	}
	public double getVelocity() {
		return velocity;
	}
	public void setVelocity(double velocity) {
		this.velocity = velocity;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	
	
}
