package org.robocode.genenticalgorithm;

import java.io.File;
import java.io.OutputStream;
import java.util.List;
import java.util.Random;

/**
 * The storage of all the information used to create the Botcat.
 * 
 * @author lancewf
 */
public interface Chromosome extends Cloneable {
	// --------------------------------------------------------------------------
	// Private Static data
	// --------------------------------------------------------------------------

	public static final String BODY_RED = "bodyRed";
	public static final String BODY_GREEN = "bodyGreen";
	public static final String BODY_BLUE = "bodyBlue";

	public static final String TURRET_RED = "turretRed";
	public static final String TURRET_GREEN = "turretGreen";
	public static final String TURRET_BLUE = "turretBlue";

	public static final String RADAR_RED = "radarRed";
	public static final String RADAR_GREEN = "radarGreen";
	public static final String RADAR_BLUE = "radarBlue";

	public static final String BULLET_RED = "bulletRed";
	public static final String BULLET_GREEN = "bulletGreen";
	public static final String BULLET_BLUE = "bulletBlue";

	// --------------------------------------------------------------------------
	// Public Members
	// --------------------------------------------------------------------------

	List<String> getSlipnetNodeList();

	int getColorValue(String colorType);

	double getMovementAmount();

	double getTurnAmount();

	double getFireAmount();

	int getLinkLength(String slipnetNodeSourceName,
			String slipnetNodeReceiverName);

	int getMemoryLevel(String slipnetNodeName);

	int getActivationThreshold(String slipnetNodeName);

	int getInitalActivation(String slipnetNodeName);

	int getNumberOfExtraNodes();

	Chromosome mutate(Random random);

	Chromosome[] crossover(Chromosome chromosome, Random random);

	void save(OutputStream outputStream);

	void load(File file);

	Chromosome mutateAll(Random random);

	int getBufferDistance();

	int getTargetDistance();

	int getEnergyLevelPeg(int peg);

	int getWorkspaceBehaviorExecuteFactor();

	int getWorkspaceControlExecuteFactor();

	int getCoderackBehaviorExecuteFactor();

	int getCoderackControlExecuteFactor();

	int getSlipnetBehaviorExecuteFactor();

	int getSlipnetControlExecuteFactor();

	long getSlipnetControlSleepTime();

	long getSlipnetBehaviorSleepTime();

	long getCoderackControlSleepTime();

	long getCoderackBehaviorSleepTime();

	long getWorkspaceControlSleepTime();

	long getWorkspaceBehaviorSleepTime();

	double getSlipnetControlReductionFactor();

	double getSlipnetBehaviorReductionFactor();

	double getCoderackControlReductionFactor();

	double getCoderackBehaviorReductionFactor();

	double getWorkspaceControlReductionFactor();

	double getWorkspaceBehaviorReductionFactor();

	int getMaximumBulletAge();

	int getBulletAccuracySuccessOne();

	int getBulletAccuracyFailureOne();
}
