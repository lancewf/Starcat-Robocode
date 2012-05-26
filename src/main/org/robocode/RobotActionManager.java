package org.robocode;

public class RobotActionManager {

	private RobotActionType currentMovement = RobotActionType.DONT_MOVE;
	private int amount = 0;
	
	synchronized public RobotAction popMovement(){
		return new RobotAction(currentMovement, amount);
	}
	
	synchronized public void setMovement(RobotActionType movement) {
		if(currentMovement == movement){
			amount++;
		}
		else{
			currentMovement = movement;
			amount = 0;
		}
	}
}
