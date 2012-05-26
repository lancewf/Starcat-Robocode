package org.robocode.codelets;

import org.robocode.BotCatable;
import org.robocode.workspace.RobocodeWorkspace;
import org.starcat.codelets.FuzzyBehaviorCodelet;
import org.starcat.workspace.Workspace;

import robocode.Bullet;
import robocode.BulletHitEvent;
import robocode.BulletMissedEvent;

/**
 *            M
 *            e 1|      
 *            m  |--------_           _--------
 *            b  |         -_       _- 
 *            e  | failure   -_   _-  success   
 *            r 0|_____________-_-_____________
 * Bullet Power  |-5    -2      0      2     5
 * 
 */
public class BulletHitMissBehaviorCodlet extends FuzzyBehaviorCodelet {

   // -----------------------------------------------------------------------------
   // Constructor
   // -----------------------------------------------------------------------------
   /**
    * successOne = 2; Max 10; Min 0
    * failureOne = -2; Max 0; Min -10
    * setSuccessMinimumZeroValueX(0);
    * setSuccessOneValueX(2);
    * setSuccessMaximumZeroValueX(Double.MAX_VALUE);
    * 
    * setFailureMinimumZeroValueX(Double.MIN_VALUE);
    * setFailureOneValueX(-2);
    * setFailureMaximumZeroValueX(0);
    */
   public BulletHitMissBehaviorCodlet(int successOne, int failureOne) 
   {  
      setSuccessMinimumZeroValueX(0);
      setSuccessOneValueX(successOne);
      setSuccessMaximumZeroValueX(Double.MAX_VALUE);

      setFailureMinimumZeroValueX(Double.MAX_VALUE*-1);
      setFailureOneValueX(failureOne);
      setFailureMaximumZeroValueX(0);
   }

   // -----------------------------------------------------------------------------
   // Overridden Codelet Members
   // -----------------------------------------------------------------------------

	@Override
	public void execute(Workspace workspace) {
		if (workspace instanceof RobocodeWorkspace) {
			RobocodeWorkspace robocodeWorkspace = (RobocodeWorkspace) workspace;
			BotCatable robot = robocodeWorkspace.getRobot();

			double power = 0.0;
			for (BulletHitEvent bulletHitEvent : robot
					.getRecentBulletHitEvents()) {
				Bullet bullet = bulletHitEvent.getBullet();

				power += bullet.getPower();
			}

			for (BulletMissedEvent bulletMissedEvent : robot
					.getRecentBulletMissedEvents()) {
				Bullet bullet = bulletMissedEvent.getBullet();

				power -= bullet.getPower();
			}

			setCrispValue(power);
		}
	}
}