package org.robocode;

public class RobotUtilities
{
   // --------------------------------------------------------------------------
   // #region Public Static Members
   // --------------------------------------------------------------------------

   public static double findDistanceToWall(BotCatable robot,
                                           double degreesFromCurrentClockwise)
   {
      double heading = 0;
      double x = 0;
      double y = 0;
      double battleFieldWidth = 0;
      double battleFieldHeight = 0;
      try
      {
         x = robot.getX();
         y = robot.getY();
         heading = robot.getHeading();
         battleFieldWidth = robot.getBattleFieldWidth();
         battleFieldHeight = robot.getBattleFieldHeight();
      }
      catch (Exception ex)
      {
         robot.toString();
      }

      double degreesAfterTurn = turnClockwise(heading,
         degreesFromCurrentClockwise);

      // convert
      // to
      // radians
      double absoluteBearing = Math.toRadians(degreesAfterTurn);

      double distance = Double.MAX_VALUE;

      if (degreesAfterTurn > 0 && degreesAfterTurn < 180)
      {
         // Test east wall
         // law of sin

         double nintyDegreesDistanceFromEastWall = battleFieldWidth - x;

         distance = (nintyDegreesDistanceFromEastWall)
               / (Math.sin(absoluteBearing));
      }
      if (degreesAfterTurn > 90 && degreesAfterTurn < 270)
      {
         // Test south wall

         double oneEightyDegreesDistanceFromEastWall = y;

         double tempDistance = (oneEightyDegreesDistanceFromEastWall)
               / (Math.sin(absoluteBearing - Math.PI / 2.0));

         if (tempDistance < distance)
         {
            distance = tempDistance;
         }
      }
      if (degreesAfterTurn > 180 && degreesAfterTurn < 359.999999)
      {
         // Test west wall

         double twoSeventyDegreesDistanceFromEastWall = x;

         double tempDistance = Double.MAX_VALUE;

         if (degreesAfterTurn < 270)
         {
            tempDistance = (twoSeventyDegreesDistanceFromEastWall)
                  / (Math.sin(absoluteBearing - Math.PI));
         }
         else
         {
            tempDistance = (twoSeventyDegreesDistanceFromEastWall)
                  / (Math.sin((-1.0) * absoluteBearing));
         }

         if (tempDistance < distance)
         {
            distance = tempDistance;
         }
      }
      if ((degreesAfterTurn > 270 && degreesAfterTurn < 359.999999)
            || (degreesAfterTurn >= 0 && degreesAfterTurn < 90))
      {
         // Test north wall

         double zeroDegreesDistanceFromNorthWall = battleFieldHeight - y;

         double tempDistance = (zeroDegreesDistanceFromNorthWall)
               / (Math.sin(Math.PI / 2.0 - absoluteBearing));

         if (tempDistance < distance)
         {
            distance = tempDistance;
         }
      }

      return distance;
   }

	public static double findDistanceOpponets(BotCatable robot,
			double degreesFromCurrentClockwise) {
		double distance = Double.MAX_VALUE;

		double heading = 0;
		double x = 0;
		double y = 0;
		try {
			x = robot.getX();
			y = robot.getY();
			heading = robot.getHeading();
		} catch (Exception ex) {
			robot.toString();
		}

		double degreesAfterTurn = turnClockwise(heading,
				degreesFromCurrentClockwise);

		for (Tank tank : robot.getTanks()) {
			double angleToTank = tank.bearingToTank(x, y);

			double tempDistance = tank.distanceToTank(x, y);

			double tankWidth = 50;
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

   public static Position getProposedLocation(BotCatable robot,
                                              double degreesFromCurrentClockwise,
                                              double distance)
   {
      double degreesAfterTurn = turnClockwise(robot.getHeading(),
         degreesFromCurrentClockwise);

      // convert
      // to
      // radians
      double absoluteBearing = Math.toRadians(degreesAfterTurn);

      double proposedX = robot.getX() + Math.sin(absoluteBearing) * distance;
      double proposedY = robot.getY() + Math.cos(absoluteBearing) * distance;

      Position position = new Position(proposedX, proposedY);

      return position;
   }

   public static boolean isOutsideBattleField(Position position, BotCatable robot)
   {
      if (position.getX() > robot.getBattleFieldWidth() || position.getX() < 0
            || position.getY() > robot.getBattleFieldHeight()
            || position.getY() < 0)
      {
         return true;
      }
      else
      {
         return false;
      }
   }

   // #endregion

   // --------------------------------------------------------------------------
   // #region Private Members
   // --------------------------------------------------------------------------

   private static double turnClockwise(double currentDirection,
                                       double degreesFromCurrentClockwise)
   {
      double degreesAfterTurn = currentDirection + degreesFromCurrentClockwise;

      if (degreesAfterTurn > 360)
      {
         degreesAfterTurn -= 360;
      }
      else if (degreesAfterTurn < 0)
      {
         degreesAfterTurn = 360 + degreesAfterTurn;
      }
      return degreesAfterTurn;
   }

   // #endregion
}
