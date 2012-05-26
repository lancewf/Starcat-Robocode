package org.robocode;

public class RobotAction {

	private int amount;
	private RobotActionType robotActionType;
	
	public RobotAction(RobotActionType robotActionType, int amount){
		this.robotActionType = robotActionType;
		this.amount = amount;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public RobotActionType getRobotActionType() {
		return robotActionType;
	}
}
