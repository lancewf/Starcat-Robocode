package org.robocode;

import java.awt.Graphics2D;

import org.robocode.codelets.FuzzyObstacleBehaviorCodelet;
import org.robocode.codelets.TargetObserverBehaviorCodelet;

import robocode.Bullet;
import robocode.BulletHitBulletEvent;
import robocode.BulletMissedEvent;

public class BotCatPainter2 {
	private BotCatable robot;

	public BotCatPainter2(BotCatable robot) {
		this.robot = robot;
	}

	public void onPaint(Graphics2D g) {
		g.setColor(java.awt.Color.RED);
		
//		double power = 0.0;
//		for(BulletHitBulletEvent bulletHitBulletEvent :robot.getBulletHitBulletEvents()){
//			Bullet bullet = bulletHitBulletEvent.getBullet();
//			
//			power += bullet.getPower();
//		}
//		
//		for(BulletMissedEvent bulletMissedEvent :robot.getBulletMissedEvents()){
//			Bullet bullet = bulletMissedEvent.getBullet();
//			
//			power -= bullet.getPower();
//		}
//		
//		g.drawString(power + "", 0, 0);
		
		
//		g.drawString(getTargetDirectionText(), 0, 0);
//		g.setColor(java.awt.Color.WHITE);
//		g.drawString(getObstacleDirectionText(), 0, 12);
//		g.setColor(java.awt.Color.RED);
//		g.drawString("Heading: " + robot.getHeading() + "", 0, 24);
//		g.setColor(java.awt.Color.WHITE);
//		g.drawString("Gun Heading: " + robot.getGunHeading(), 0, 36);
//		g.setColor(java.awt.Color.RED);
//		g.drawString(
//				"Gun Heading Relative: "
//						+ RobotUtilities.getTurretHeadingFromFront(robot), 0,
//				48);
	}

	private String getObstacleDirectionText() {
		double forward = RobotUtilities.findDistanceToWall(robot,
				FuzzyObstacleBehaviorCodelet.FORWARD);
		double left = RobotUtilities.findDistanceToWall(robot,
				FuzzyObstacleBehaviorCodelet.LEFT);
		double right = RobotUtilities.findDistanceToWall(robot,
				FuzzyObstacleBehaviorCodelet.RIGHT);
		double backward = RobotUtilities.findDistanceToWall(robot,
				FuzzyObstacleBehaviorCodelet.BACKWARD);

		String text = "Wall - ";

		if (forward < 2000) {
			text += "Forward: " + forward;
		}
		if (left < 2000) {
			text += " Left: " + left;
		}
		if (right < 2000) {
			text += " Right: " + right;
		}
		if (backward < 2000) {
			text += " Backward: " + backward;
		}

		return text;
	}

	private String getTargetDirectionText() {
		double forward = RobotUtilities.findDistanceOpponets(robot,
				TargetObserverBehaviorCodelet.FORWARD);
		double left = RobotUtilities.findDistanceOpponets(robot,
				TargetObserverBehaviorCodelet.LEFT);
		double right = RobotUtilities.findDistanceOpponets(robot,
				TargetObserverBehaviorCodelet.RIGHT);
		double backward = RobotUtilities.findDistanceOpponets(robot,
				TargetObserverBehaviorCodelet.BACKWARD);
		double forwardRight = RobotUtilities.findDistanceOpponets(robot,
				TargetObserverBehaviorCodelet.FORWARD_RIGHT);
		double backwardRight = RobotUtilities.findDistanceOpponets(robot,
				TargetObserverBehaviorCodelet.BACKWARD_RIGHT);
		double backwardLeft = RobotUtilities.findDistanceOpponets(robot,
				TargetObserverBehaviorCodelet.BACKWARD_LEFT);
		double forwardLeft = RobotUtilities.findDistanceOpponets(robot,
				TargetObserverBehaviorCodelet.FORWARD_LEFT);

		String text = "Target - ";

		if (forward < 2000) {
			text += "Forward: " + forward;
		}
		if (left < 2000) {
			text += " Left: " + left;
		}
		if (right < 2000) {
			text += " Right: " + right;
		}
		if (backward < 2000) {
			text += " Backward: " + backward;
		}

		if (forwardRight < 2000) {
			text += "forwardRight: " + forwardRight;
		}
		if (backwardRight < 2000) {
			text += " backwardRight: " + backwardRight;
		}
		if (backwardLeft < 2000) {
			text += " backwardLeft: " + backwardLeft;
		}
		if (forwardLeft < 2000) {
			text += " forwardLeft: " + forwardLeft;
		}

		return text;
	}
}
