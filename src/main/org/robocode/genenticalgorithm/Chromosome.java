package org.robocode.genenticalgorithm;

import java.io.OutputStream;
import java.util.Random;

/**
 * The storage of all the information used to create the Botcat. 
 * 
 * @author lancewf
 */
public interface Chromosome extends Cloneable
{
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
   
   public static final String EXTRA_NODE_TAG = "extraNode";
   public static final String MOVEMENT_AMOUNT_TAG = "movementAmount";
   public static final String TURN_AMOUNT_TAG = "turnAmount";
   public static final String FIRE_AMOUNT_TAG = "fireAmount";
   
   public static final String EXTRA_NODES_1 = "extraNodes1";
   public static final String EXTRA_NODES_2 = "extraNodes2";
   public static final String EXTRA_NODES_3 = "extraNodes3";
   public static final String EXTRA_NODES_4 = "extraNodes4";
   
   public static final String ENERGY_LEVEL_PEG = "energyLevelPeg";
   
   public static final String TARGET_DISTANCE = "targetDistance";
   
   public static final String BUFFER_DISTANCE = "bufferDistance";
   
   // --------------------------------------------------------------------------
   // Public Members
   // --------------------------------------------------------------------------

   int getColorValue(String colorType);

   double getMovementAmount();

   double getTurnAmount();

   double getFireAmount();

   int getLinkLength(String slipnetNodeSourceName, 
                     String slipnetNodeReceiverName);

   int getMemoryLevel(String slipnetNodeName);

   int getActivationThreshold(String slipnetNodeName);
   
   int getNumberOfExtraNodes();

   Chromosome mutate(Random random);

   Chromosome[] crossover(Chromosome chromosome, Random random);

   void save(OutputStream outputStream);

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
}
