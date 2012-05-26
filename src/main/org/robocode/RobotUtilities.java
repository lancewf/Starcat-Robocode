package org.robocode;

import java.util.List;
import java.util.Random;

public class RobotUtilities {
	// -------------------------------------------------------------------------
	// Public Static Members
	// -------------------------------------------------------------------------

	public static double bearingToTank(Tank tank, double x1, double y1) {
		double radians = bearingToTankRadian(tank, x1, y1);
		return Math.toDegrees(radians);
	}

	public static double distanceToTank(Tank tank, double x1, double y1) {
		double deltaX = tank.getX() - x1;
		double deltaY = tank.getY() - y1;
		return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
	}
	
	public static double getTurretHeadingFromFront(BotCatable robot) {
		double gunHeading = robot.getGunHeading();
		double bodyHeading = robot.getHeading();
		
		double bearing = gunHeading - bodyHeading;

        if(bearing < 0)
        {
           bearing = 360 + bearing;
        }
	    return bearing;
	}

	public static double findDistanceToWall(BotCatable robot,
			double degreesFromCurrentClockwise) {
		double heading = robot.getHeading();
		double x = robot.getX();
		double y = robot.getY();
		double battleFieldWidth = robot.getBattleFieldWidth();
		double battleFieldHeight = robot.getBattleFieldHeight();

		double degreesAfterTurn = turnClockwise(heading,
				degreesFromCurrentClockwise);

		// convert to radians
		double absoluteBearing = Math.toRadians(degreesAfterTurn);

		double distance = Double.MAX_VALUE;

		if (degreesAfterTurn > 0 && degreesAfterTurn < 180) {
			// Test east wall
			// law of sin

			double nintyDegreesDistanceFromEastWall = battleFieldWidth - x;

			distance = (nintyDegreesDistanceFromEastWall)
					/ (Math.sin(absoluteBearing));
		}
		if (degreesAfterTurn > 90 && degreesAfterTurn < 270) {
			// Test south wall

			double oneEightyDegreesDistanceFromEastWall = y;

			double tempDistance = (oneEightyDegreesDistanceFromEastWall)
					/ (Math.sin(absoluteBearing - Math.PI / 2.0));

			if (tempDistance < distance) {
				distance = tempDistance;
			}
		}
		if (degreesAfterTurn > 180 && degreesAfterTurn < 359.999999) {
			// Test west wall

			double twoSeventyDegreesDistanceFromEastWall = x;

			double tempDistance = Double.MAX_VALUE;

			if (degreesAfterTurn < 270) {
				tempDistance = (twoSeventyDegreesDistanceFromEastWall)
						/ (Math.sin(absoluteBearing - Math.PI));
			} else {
				tempDistance = (twoSeventyDegreesDistanceFromEastWall)
						/ (Math.sin((-1.0) * absoluteBearing));
			}

			if (tempDistance < distance) {
				distance = tempDistance;
			}
		}
		if ((degreesAfterTurn > 270 && degreesAfterTurn < 359.999999)
				|| (degreesAfterTurn >= 0 && degreesAfterTurn < 90)) {
			// Test north wall

			double zeroDegreesDistanceFromNorthWall = battleFieldHeight - y;

			double tempDistance = (zeroDegreesDistanceFromNorthWall)
					/ (Math.sin(Math.PI / 2.0 - absoluteBearing));

			if (tempDistance < distance) {
				distance = tempDistance;
			}
		}

		return distance;
	}
	
	public static double findDistanceToOtherTanks(double directionLooking,
			BotCatable robot, Random random) {
		double modifiedHeadingToLook = directionLooking;

		int amountToAdd = random.nextInt(30);

		if (random.nextBoolean()) {
			modifiedHeadingToLook += amountToAdd;
		} else {
			modifiedHeadingToLook -= amountToAdd;
		}

		double distance = RobotUtilities.findDistanceOpponets(robot,
				modifiedHeadingToLook);

		return distance;
	}

	public static double findDistanceOpponets(BotCatable robot,
			double degreesFromCurrentClockwise) {
		double distance = Double.MAX_VALUE;

		double heading = robot.getHeading();
		double x = robot.getX();
		double y = robot.getY();

		double degreesAfterTurn = turnClockwise(heading,
				degreesFromCurrentClockwise);

		List<Tank> tanks = robot.getTanks();
		for (Tank tank : tanks) {
			double angleToTank = bearingToTank(tank, x, y);

			double tempDistance = distanceToTank(tank, x, y);

			double tankWidth = 100;
			double tankHalfWidth = tankWidth / 2.0;
			tempDistance -= tankHalfWidth;

			double widthRadians = Math.atan2(tankHalfWidth, tempDistance);
			double widthDegrees = Math.toDegrees(widthRadians);

			if (Math.abs(degreesAfterTurn - angleToTank) < widthDegrees) {
				if (tempDistance < distance) {
					distance = tempDistance;
				}
			}
		}

		return distance;
	}

	// -------------------------------------------------------------------------
	// Private Members
	// -------------------------------------------------------------------------

	private static double bearingToTankRadian(Tank tank, double x1, double y1) {
		double deltaX = tank.getX() - x1;
		double deltaY = tank.getY() - y1;
		double distanceAway = distanceToTank(tank, x1, y1);
		if (deltaX == 0)
			return 0;

		if (deltaX > 0) {
			if (deltaY > 0)
				return Math.asin(deltaX / distanceAway);
			else
				return (Math.PI - Math.asin(deltaX / distanceAway));
		} else { // deltaX < 0
			if (deltaY > 0)
				return ((2 * Math.PI) - Math.asin(-deltaX / distanceAway));
			else
				return Math.PI + Math.asin(-deltaX / distanceAway);
		}
	}
	private static double turnClockwise(double currentDirection,
			double degreesFromCurrentClockwise) {
		double degreesAfterTurn = currentDirection
				+ degreesFromCurrentClockwise;

		if (degreesAfterTurn > 360) {
			degreesAfterTurn -= 360;
		} else if (degreesAfterTurn < 0) {
			degreesAfterTurn = 360 + degreesAfterTurn;
		}
		return degreesAfterTurn;
	}
}
