package org.robocode;

import java.util.List;

import org.robocode.genenticalgorithm.Chromosome;

import robocode.BulletHitEvent;
import robocode.BulletMissedEvent;

public interface BotCatable {
	void setMovement(RobotActionType action);
	List<Tank> getTanks();
	
	Chromosome getChromosome();
	double getGunHeading();
	double getHeading();
	double getEnergy();
	double getX();
	double getY();
	double getBattleFieldWidth();
	double getBattleFieldHeight();
	long getTime();
	List<BulletHitEvent> getRecentBulletHitEvents();
	List<BulletMissedEvent> getRecentBulletMissedEvents();
}
