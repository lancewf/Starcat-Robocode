package org.robocode.configuration;

import java.util.ArrayList;
import java.util.List;

import org.robocode.RobotAction;
import org.robocode.codelets.BackwardTurretOrientationObserverCodelet;
import org.robocode.codelets.EnergyLevelBehaviorCodlet;
import org.robocode.codelets.ForwardTurretOrientationObserverCodelet;
import org.robocode.codelets.FuzzyObstacleBehaviorCodelet;
import org.robocode.codelets.LeftTurretOrientationObserverCodelet;
import org.robocode.codelets.NorthBodyOrientationObserverCodelet;
import org.robocode.codelets.PerformerBehaviorCodelet;
import org.robocode.codelets.RightTurretOrientationObserverCodelet;
import org.robocode.codelets.SouthBodyOrientationObserverCodelet;
import org.robocode.codelets.TargetObserverBehaviorCodelet;
import org.robocode.codelets.EastBodyOrientationObserverCodelet;
import org.robocode.codelets.WestBodyOrientationObserverCodelet;
import org.robocode.genenticalgorithm.BotcatChromosome;
import org.robocode.genenticalgorithm.Chromosome;
import org.starcat.configuration.SlipnetBuilder;
import org.starcat.configuration.SystemConfiguration;
import org.starcat.slipnet.LateralLink;

public class RobocodeSlipnetBuilder extends SlipnetBuilder
{
   // --------------------------------------------------------------------------
   // #region Private Static Data
   // --------------------------------------------------------------------------
   
   private static final String ENERGY_LOW_NODE_NAME = "energyLow";
   private static final String ENERGY_HIGH_NODE_NAME = "energyHigh";
   private static final String OBSTACLE_LEFT = "obstacleLeft";
   private static final String OBSTACLE_RIGHT = "obstacleRight";
   private static final String OBSTACLE_FRONT = "obstacleFront";
   private static final String OBSERVER = "obstacleObserver";
   private static final String CLEAR_RIGHT = "clearRight";
   private static final String CLEAR_LEFT = "clearLeft";
   private static final String CLEAR_FOREWARD = "clearFront";
   private static final String OBSTACLE_BACKWARD = "obstacleBackward";
   private static final String CLEAR_BACKWARD = "clearBackward";
   private static final String TARGET_LEFT = "targetLeft";
   private static final String TARGET_RIGHT = "targetRight";
   private static final String TARGET_FORWARD = "targetForward";
   private static final String TARGET_BACKWARD = "targetBackward";
   
   private static final String MOVE_FORWARD= "moveForward";
   private static final String FIRE = "fire";
   private static final String DO_NOT_MOVE = "doNotMove";
   private static final String MOVE_BACKWARD = "moveBackward";
   private static final String TURN_RIGHT = "turnRight";
   private static final String TURN_LEFT = "turnLeft";
   
   private static final String ORIENTATION_NORTH = "orientationNorth";
   private static final String ORIENTATION_SOUTH = "orientationSouth";
   private static final String ORIENTATION_WEST = "orientationWest";
   private static final String ORIENTATION_EAST = "orientatinoEast";
   
   private static final String TURN_TURRET_RIGHT = "turnTurretRight";
   private static final String TURN_TURRET_LEFT = "turnTurretLeft";
   
   private static final String TURRET_FORWARD = "turretForward";
   private static final String TURRET_BACKWARD = "turretBackward";
   private static final String TURRET_RIGHT = "turretRight";
   private static final String TURRET_LEFT = "turretLeft";

   // --------------------------------------------------------------------------
   // #region Protected Overridden Methods
   // --------------------------------------------------------------------------
   
   /**
    * Create all the slipnet nodes for the system
    */
   protected void createSlipnetNodes(Chromosome chromosome, 
                                     List<String> slipnetNodeNames)
   {
      createSlipnetNode(OBSERVER, 100, 99, 1);
      
      for(String slipnetNode : slipnetNodeNames)
      {
         int conceptualDepth = chromosome.getMemoryLevel(slipnetNode);
         int activationThreashold = 
            chromosome.getActivationThreshold(slipnetNode);
         
         createSlipnetNode(slipnetNode, 
            conceptualDepth, 0, activationThreashold);
      }
   }

   /**
    * Create all the links for the system
    */
   protected void createLinks(Chromosome chromosome, 
                              List<String> slipnetNodeNames)
   {
      //link length must be from 0 - 100 integer
      for(String fromSlipnetNode : slipnetNodeNames)
      {
         for(String toSlipnetNode : slipnetNodeNames)
         {
            int value = chromosome.getLinkLength(
               fromSlipnetNode, toSlipnetNode);
            
            // A link with length of 100 is the same as no link at all
            // to save process time do not create a link
            if(value < 100)
            {
               createLink(new LateralLink(), fromSlipnetNode, 
                  toSlipnetNode, value);
            }
         }
      }
   }

   /**
    * Create all the codelets for the system
    */
   protected void createCodelets(Chromosome chromosome)
   {
      createCodeletObservers(chromosome);
      
      createCodeletPreformers(chromosome);
   }
   
   protected List<String> getSlipnetNodeList(Chromosome chromosome)
   {
      List<String> slipnetNodes = new ArrayList<String>();
      
      slipnetNodes.add(OBSTACLE_LEFT);
      slipnetNodes.add(OBSTACLE_RIGHT);
      slipnetNodes.add(OBSTACLE_FRONT);
      slipnetNodes.add(OBSTACLE_BACKWARD);

      slipnetNodes.add(CLEAR_LEFT);
      slipnetNodes.add(CLEAR_RIGHT);
      slipnetNodes.add(CLEAR_FOREWARD);
      slipnetNodes.add(CLEAR_BACKWARD);

      slipnetNodes.add(TARGET_RIGHT);
      slipnetNodes.add(TARGET_LEFT);
      slipnetNodes.add(TARGET_FORWARD);
      slipnetNodes.add(TARGET_BACKWARD);
      
      slipnetNodes.add(TURN_RIGHT);
      slipnetNodes.add(TURN_LEFT);
      slipnetNodes.add(MOVE_FORWARD);
      slipnetNodes.add(MOVE_BACKWARD);
      slipnetNodes.add(DO_NOT_MOVE);
      slipnetNodes.add(FIRE);
      slipnetNodes.add(ENERGY_HIGH_NODE_NAME);
      slipnetNodes.add(ENERGY_LOW_NODE_NAME);
      
      slipnetNodes.add(ORIENTATION_NORTH);
      slipnetNodes.add(ORIENTATION_SOUTH);
      slipnetNodes.add(ORIENTATION_WEST);
      slipnetNodes.add(ORIENTATION_EAST);
      
      slipnetNodes.add(TURRET_RIGHT);
      slipnetNodes.add(TURRET_LEFT);
      slipnetNodes.add(TURRET_BACKWARD);
      slipnetNodes.add(TURRET_FORWARD);
      
      slipnetNodes.add(TURN_TURRET_RIGHT);
      slipnetNodes.add(TURN_TURRET_LEFT);
      
      int numberOfExtraNodes = chromosome.getNumberOfExtraNodes();
      
      for(int count = 0; count < numberOfExtraNodes; count++)
      {
         slipnetNodes.add(BotcatChromosome.EXTRA_NODE_TAG + count);
      }
      
      return slipnetNodes;
   }

   /**
    * Initialize the system configurations
    */
   protected void initializeSystemConfigurations(
                                        SystemConfiguration systemConfiguration,
                                        Chromosome chromosome)
   {
      // System Configuration
      systemConfiguration.setWorkspaceBehaviorAdaptiveExecute("false");
      systemConfiguration.setWorkspaceControlAdaptiveExecute("false");
      systemConfiguration.setCoderackBehaviorAdaptiveExecute("false");
      systemConfiguration.setCoderackControlAdaptiveExecute("false");
      systemConfiguration.setSlipnetBehaviorAdaptiveExecute("false");
      systemConfiguration.setSlipnetControlAdaptiveExecute("false");

//      systemConfiguration.setWorkspaceBehaviorExecuteFactor(20);
      systemConfiguration.setWorkspaceBehaviorExecuteFactor(
         chromosome.getWorkspaceBehaviorExecuteFactor());
      
//      systemConfiguration.setWorkspaceControlExecuteFactor(1);
      systemConfiguration.setWorkspaceControlExecuteFactor(
         chromosome.getWorkspaceControlExecuteFactor());
      
//      systemConfiguration.setCoderackBehaviorExecuteFactor(10);
      systemConfiguration.setCoderackBehaviorExecuteFactor(
         chromosome.getCoderackBehaviorExecuteFactor());
      
//      systemConfiguration.setCoderackControlExecuteFactor(1);
      systemConfiguration.setCoderackControlExecuteFactor(
         chromosome.getCoderackControlExecuteFactor());
      
//      systemConfiguration.setSlipnetBehaviorExecuteFactor(10);
      systemConfiguration.setSlipnetBehaviorExecuteFactor(
         chromosome.getSlipnetBehaviorExecuteFactor());
      
//      systemConfiguration.setSlipnetControlExecuteFactor(1);
      systemConfiguration.setSlipnetControlExecuteFactor(
         chromosome.getSlipnetControlExecuteFactor());

//      systemConfiguration.setWorkspaceBehaviorReductionFactor(0.01);
      systemConfiguration.setWorkspaceBehaviorReductionFactor(
         chromosome.getWorkspaceBehaviorReductionFactor());
      
//      systemConfiguration.setWorkspaceControlReductionFactor(0.01);
      systemConfiguration.setWorkspaceControlReductionFactor(
         chromosome.getWorkspaceControlReductionFactor());
      
//      systemConfiguration.setCoderackBehaviorReductionFactor(0.01);
      systemConfiguration.setCoderackBehaviorReductionFactor(
         chromosome.getCoderackBehaviorReductionFactor());
      
//      systemConfiguration.setCoderackControlReductionFactor(0.01);
      systemConfiguration.setCoderackControlReductionFactor(
         chromosome.getCoderackControlReductionFactor());
      
//      systemConfiguration.setSlipnetBehaviorReductionFactor(0.01);
      systemConfiguration.setSlipnetBehaviorReductionFactor(
         chromosome.getSlipnetBehaviorReductionFactor());
      
//      systemConfiguration.setSlipnetControlReductionFactor(0.01);
      systemConfiguration.setSlipnetControlReductionFactor(
         chromosome.getSlipnetControlReductionFactor());
      
      systemConfiguration.setWorkspaceBehaviorSleeper("true");
      systemConfiguration.setWorkspaceControlSleeper("true");
      systemConfiguration.setCoderackBehaviorSleeper("true");
      systemConfiguration.setCoderackControlSleeper("true");
      systemConfiguration.setSlipnetBehaviorSleeper("true");
      systemConfiguration.setSlipnetControlSleeper("true");

//      systemConfiguration.setWorkspaceBehaviorSleepTime(10);
      systemConfiguration.setWorkspaceBehaviorSleepTime(
         chromosome.getWorkspaceBehaviorSleepTime());
      
//      systemConfiguration.setWorkspaceControlSleepTime(10);
      systemConfiguration.setWorkspaceControlSleepTime(
         chromosome.getWorkspaceControlSleepTime());
      
//      systemConfiguration.setCoderackBehaviorSleepTime(10);
      systemConfiguration.setCoderackBehaviorSleepTime(
         chromosome.getCoderackBehaviorSleepTime());
      
//      systemConfiguration.setCoderackControlSleepTime(10);
      systemConfiguration.setCoderackControlSleepTime(
         chromosome.getCoderackControlSleepTime());
      
//      systemConfiguration.setSlipnetBehaviorSleepTime(10);
      systemConfiguration.setSlipnetBehaviorSleepTime(
         chromosome.getSlipnetBehaviorSleepTime());
      
//      systemConfiguration.setSlipnetControlSleepTime(10);
      systemConfiguration.setSlipnetControlSleepTime(
         chromosome.getSlipnetControlSleepTime());
   }
   
   // --------------------------------------------------------------------------
   // Private Members
   // --------------------------------------------------------------------------
   
   private void createCodeletPreformers(Chromosome chromosome)
   {
      createCodelet(new PerformerBehaviorCodelet(RobotAction.FORWARD),
         MOVE_FORWARD,
         100,
         1);
      
      createCodelet(new PerformerBehaviorCodelet(RobotAction.FIRE),
         FIRE,
         100,
         1);

      createCodelet(new PerformerBehaviorCodelet(RobotAction.DONT_MOVE),
         DO_NOT_MOVE,
         100,
         1);

      createCodelet(new PerformerBehaviorCodelet(RobotAction.BACKWARD),
         MOVE_BACKWARD,
         100,
         1);

      createCodelet(new PerformerBehaviorCodelet(RobotAction.TURN_RIGHT),
         TURN_RIGHT,
         100,
         1);

      createCodelet(new PerformerBehaviorCodelet(RobotAction.TURN_LEFT),
         TURN_LEFT,
         100,
         1);
      
      createCodelet(new PerformerBehaviorCodelet(RobotAction.TURN_TURRET_RIGHT),
         TURN_TURRET_RIGHT,
         100,
         1);

      createCodelet(new PerformerBehaviorCodelet(RobotAction.TURN_TURRET_LEFT),
         TURN_TURRET_LEFT,
         100,
         1);
   }
   
   private void createCodeletObservers(Chromosome chromosome)
   {
      int bufferDistance = chromosome.getBufferDistance();
      
      // configuration/codelet
      createCodelet(new FuzzyObstacleBehaviorCodelet(
         FuzzyObstacleBehaviorCodelet.LEFT, bufferDistance),
         OBSERVER,
         1,
         100,
         100,
         1,
         OBSTACLE_LEFT,
         CLEAR_LEFT);
      createCodelet(new FuzzyObstacleBehaviorCodelet(
         FuzzyObstacleBehaviorCodelet.RIGHT, bufferDistance),
         OBSERVER,
         1,
         100,
         100,
         1,
         OBSTACLE_RIGHT,
         CLEAR_RIGHT);
      createCodelet(new FuzzyObstacleBehaviorCodelet(
         FuzzyObstacleBehaviorCodelet.FORWARD, bufferDistance),
         OBSERVER,
         1,
         100,
         100,
         1,
         OBSTACLE_FRONT,
         CLEAR_FOREWARD);
      createCodelet(new FuzzyObstacleBehaviorCodelet(
         FuzzyObstacleBehaviorCodelet.BACKWARD, bufferDistance),
         OBSERVER,
         1,
         100,
         100,
         1,
         OBSTACLE_BACKWARD,
         CLEAR_BACKWARD);
    	      
      int targetDistance = chromosome.getTargetDistance();
      
      // Target Observers      
      createCodelet(new TargetObserverBehaviorCodelet(
         TargetObserverBehaviorCodelet.LEFT, targetDistance),
         OBSERVER,
         1,
         100,
         1,
         TARGET_LEFT);
      createCodelet(new TargetObserverBehaviorCodelet(
         TargetObserverBehaviorCodelet.RIGHT, targetDistance),
         OBSERVER,
         1,
         100,
         1,
         TARGET_RIGHT);
      createCodelet(new TargetObserverBehaviorCodelet(
         TargetObserverBehaviorCodelet.FORWARD, targetDistance),
         OBSERVER,
         1,
         100,
         1,
         TARGET_FORWARD);
      createCodelet(new TargetObserverBehaviorCodelet(
         TargetObserverBehaviorCodelet.BACKWARD, targetDistance),
         OBSERVER,
         1,
         100,
         1,
         TARGET_BACKWARD);
      
      int value0 = chromosome.getEnergyLevelPeg(0);
      int value1 = chromosome.getEnergyLevelPeg(1);
      int value2 = chromosome.getEnergyLevelPeg(2);
      int value3 = chromosome.getEnergyLevelPeg(3);
      
      createCodelet(new EnergyLevelBehaviorCodlet(
         value0, value1, value2, value3),
         OBSERVER,
         1,
         100,
         100,
         1,
         ENERGY_LOW_NODE_NAME, 
         ENERGY_HIGH_NODE_NAME);
      
      //Orientation
      createCodelet(new EastBodyOrientationObserverCodelet(),
         OBSERVER,
         1,
         100,
         1,
         ORIENTATION_EAST);
      createCodelet(new WestBodyOrientationObserverCodelet(),
         OBSERVER,
         1,
         100,
         1,
         ORIENTATION_WEST);
      createCodelet(new NorthBodyOrientationObserverCodelet(),
         OBSERVER,
         1,
         100,
         1,
         ORIENTATION_NORTH);
      createCodelet(new SouthBodyOrientationObserverCodelet(),
         OBSERVER,
         1,
         100,
         1,
         ORIENTATION_SOUTH);
      
      createCodelet(new RightTurretOrientationObserverCodelet(),
         OBSERVER,
         1,
         100,
         1,
         TURRET_RIGHT);
      createCodelet(new LeftTurretOrientationObserverCodelet(),
         OBSERVER,
         1,
         100,
         1,
         TURRET_LEFT);
      createCodelet(new BackwardTurretOrientationObserverCodelet(),
         OBSERVER,
         1,
         100,
         1,
         TURRET_BACKWARD);
      createCodelet(new ForwardTurretOrientationObserverCodelet(),
         OBSERVER,
         1,
         100,
         1,
         TURRET_FORWARD);
   }
}
