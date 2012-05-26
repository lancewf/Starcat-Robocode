package org.robocode.codelets;

import org.robocode.BotCatable;
import org.robocode.RobotUtilities;
import org.robocode.workspace.RobocodeWorkspace;
import org.starcat.codelets.FuzzyBehaviorCodelet;
import org.starcat.workspace.Workspace;

/**
 * A Codelet that observes obstacles (walls and robots) in a certain direction
 * 
 * M 1|
 * e  |----__   __-----
 * M  |      -_-
 * B  |     _- -_
 * E 0|___-_______-____
 * R  |min    one    max  : value
 * 
 * @author User Lance Finfrock
 */
public class FuzzyObstacleBehaviorCodelet extends FuzzyBehaviorCodelet {
	// -------------------------------------------------------------------------
	// Public static Data
	// -------------------------------------------------------------------------

	   public static final int FORWARD = 0;
	   public static final int FORWARD_RIGHT = 45;
	   public static final int RIGHT = 90;
	   public static final int BACKWARD_RIGHT = 135;
	   public static final int BACKWARD = 180;
	   public static final int BACKWARD_LEFT = -135;
	   public static final int LEFT = -90;
	   public static final int FORWARD_LEFT = -45;

	// -------------------------------------------------------------------------
	// Private Data
	// -------------------------------------------------------------------------

	private double headingToLook = 0.0;

	private int bufferDistance = 70;

	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------

	/**
	 * Creates a obstacle observer in a specific direction and buffer distance.
	 * 
	 * @param headingToLook
	 *            - the direction that the codelet is observing
	 * @param bufferDistance
	 *            - the buffer distance of the obstacle from the robot
	 */
	public FuzzyObstacleBehaviorCodelet(double headingToLook, int bufferDistance) {
		this.bufferDistance = bufferDistance;

		setSuccessMinimumZeroValueX(0);
		setSuccessOneValueX(getHalfBufferDistance());
		setSuccessMaximumZeroValueX(getOneHalfBufferDistance());

		setFailureMinimumZeroValueX(getHalfBufferDistance());
		setFailureOneValueX(getOneHalfBufferDistance());
		setFailureMaximumZeroValueX(Double.MAX_VALUE);

		this.headingToLook = headingToLook;
	}

	// -------------------------------------------------------------------------
	// Overridden Codelet Members
	// -------------------------------------------------------------------------

	@Override
	public void execute(Workspace workspace) {
		if (workspace instanceof RobocodeWorkspace) {
			RobocodeWorkspace robocodeWorkspace = (RobocodeWorkspace) workspace;
			BotCatable robot = robocodeWorkspace.getRobot();

			if (robot != null) {
				double distance = findDistanceToObstacle(robot);

				setCrispValue(distance);
			}
		}
	}

	// -------------------------------------------------------------------------
	// Private Members
	// -------------------------------------------------------------------------

	private int getOneHalfBufferDistance() {
		return bufferDistance + getHalfBufferDistance();
	}

	private int getHalfBufferDistance() {
		return bufferDistance / 2;
	}

	private double findDistanceToObstacle(BotCatable robot) {
		double distance = RobotUtilities.findDistanceToWall(robot,
				headingToLook);

		return distance;
	}
}
