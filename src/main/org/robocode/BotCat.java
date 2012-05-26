package org.robocode;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import java.util.List;

import org.robocode.genenticalgorithm.BotcatChromosome;
import org.robocode.genenticalgorithm.Chromosome;

import robocode.AdvancedRobot;
import robocode.BulletHitEvent;
import robocode.BulletMissedEvent;
import robocode.DeathEvent;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.RobotDeathEvent;
import robocode.ScannedRobotEvent;
import robocode.WinEvent;

public class BotCat extends AdvancedRobot implements BotCatable {
	private boolean isAlive = false;
	private static String CHROMOSOME_FILE_NAME = "BotCat.properties";
	private Chromosome chromosome = null;
	private double turnAmount = 0;
	private double fireAmount = 0;
	private double forwardMovementAmount = 0;
	private BotCatPainter2 botCatPainter = new BotCatPainter2(this);
	private RobotActionManager robotMovement = new RobotActionManager();
	private StartCatControler starCatRunner = null;
	private TankManager tankManager = new TankManager(this);
	private BulletManager bulletManager = new BulletManager(this);
	
	// ---------------------------------------------------------------------------
	// BotCatable Methods
	// ---------------------------------------------------------------------------

	public void setMovement(RobotActionType movement) {
		System.out.println(movement);
		robotMovement.setMovement(movement);
	}

	public List<Tank> getTanks() {
		return tankManager.getTanks();
	}

	public Chromosome getChromosome() {
		return chromosome;
	}

	// ---------------------------------------------------------------------------
	// AdvancedRobot Methods
	// ---------------------------------------------------------------------------

	public void run() {
		chromosome = createChromosome();
		turnAmount = chromosome.getTurnAmount();
		fireAmount = chromosome.getFireAmount();
		forwardMovementAmount = chromosome.getMovementAmount();
		bulletManager.setMaximumBulletAge(chromosome.getMaximumBulletAge());
		applyGeniticFeatures();
	    starCatRunner = new StartCatControler(this);
	    starCatRunner.start();

		isAlive = true;
		long count = 0;
		while (isAlive) {
			if (count == 100000) {
				move();
				count = 0;
			}
			count += 1;
		}
	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		tankManager.onScannedRobot(e, getX(), getY(), getHeading());
	}
	
	public void onBulletHit(BulletHitEvent event) {
		bulletManager.add(event);
	}
	
	public void onBulletMissed(BulletMissedEvent event) {
		bulletManager.add(event);
	}

	/**
	 * This method is called when another robot dies.
	 */
	public void onRobotDeath(RobotDeathEvent event) {
		tankManager.onRobotDeath(event);
	}

	public void onPaint(Graphics2D g) {
		botCatPainter.onPaint(g);
	}
	
	public List<BulletHitEvent> getRecentBulletHitEvents(){
		return bulletManager.getRecentBulletHitEvents();
	}
	
	public List<BulletMissedEvent> getRecentBulletMissedEvents(){
		return bulletManager.getRecentBulletMissedEvents();
	}

	/**
	 * This method is called when your robot collides with a wall.
	 * 
	 * @see robocode.Robot#onHitWall(robocode.HitWallEvent)
	 */
	public void onHitWall(HitWallEvent event) {}

	public void onHitRobot(HitRobotEvent event) {}

	/**
	 * This method is called if your robot wins a battle.
	 * 
	 * @see robocode.Robot#onWin(robocode.WinEvent)
	 */
	public void onWin(WinEvent event) {
		isAlive = false;
		starCatRunner.stop();
	}

	public void onDeath(DeathEvent event) {
		isAlive = false;
		starCatRunner.stop();
	}

	// ---------------------------------------------------------------------------
	// Private Methods
	// ---------------------------------------------------------------------------

	private Chromosome createChromosome() {
		File file = getDataFile(CHROMOSOME_FILE_NAME);

		return new BotcatChromosome(file);
	}

	private void applyGeniticFeatures() {
		Color bodyColor = new Color(
				chromosome.getColorValue(Chromosome.BODY_RED),
				chromosome.getColorValue(Chromosome.BODY_GREEN),
				chromosome.getColorValue(Chromosome.BODY_BLUE));

		Color turretColor = new Color(
				chromosome.getColorValue(Chromosome.TURRET_RED),
				chromosome.getColorValue(Chromosome.TURRET_GREEN),
				chromosome.getColorValue(Chromosome.TURRET_BLUE));

		Color radarColor = new Color(
				chromosome.getColorValue(Chromosome.RADAR_RED),
				chromosome.getColorValue(Chromosome.RADAR_GREEN),
				chromosome.getColorValue(Chromosome.RADAR_BLUE));
		
		Color bulletColor = new Color(
				chromosome.getColorValue(Chromosome.BULLET_RED),
				chromosome.getColorValue(Chromosome.BULLET_GREEN),
				chromosome.getColorValue(Chromosome.BULLET_BLUE));

		setColors(bodyColor, turretColor, radarColor);
		
		setBulletColor(bulletColor);
	}

	/**
	 * Perform a move on the robotCode battle field
	 */
	private void move() {
		RobotAction robotAction = robotMovement.popMovement();
		if (robotAction.getRobotActionType() == RobotActionType.FORWARD) {
			setAhead(forwardMovementAmount * robotAction.getAmount());
		} else if (robotAction.getRobotActionType() == RobotActionType.BACKWARD) {
			setBack(forwardMovementAmount * robotAction.getAmount());
		} else if (robotAction.getRobotActionType() == RobotActionType.TURN_LEFT) {
			setTurnLeft(turnAmount * robotAction.getAmount());
		} else if (robotAction.getRobotActionType() == RobotActionType.TURN_RIGHT) {
			setTurnRight(turnAmount * robotAction.getAmount());
		} else if (robotAction.getRobotActionType() == RobotActionType.TURN_TURRET_LEFT) {
			setTurnGunLeft(turnAmount * robotAction.getAmount());
		} else if (robotAction.getRobotActionType() == RobotActionType.TURN_TURRET_RIGHT) {
			setTurnGunRight(turnAmount * robotAction.getAmount());
		} else if (robotAction.getRobotActionType() == RobotActionType.FIRE) {
			setFire(fireAmount * robotAction.getAmount());
		} else if (robotAction.getRobotActionType() == RobotActionType.DONT_MOVE) {
			// do nothing
		}
		setTurnRadarRight(RobotConstance.ONE_REV);
		execute();
	}
}
